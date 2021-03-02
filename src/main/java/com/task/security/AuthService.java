package com.task.security;

import com.task.entity.Code;
import com.task.entity.Customer;
import com.task.entity.Users;
import com.task.payload.ApiResponse;
import com.task.payload.ReqActivate;
import com.task.payload.ReqLogin;
import com.task.payload.ReqRegister;
import com.task.repository.CodeRepository;
import com.task.repository.RoleRepository;
import com.task.repository.UserRepository;
import com.task.service.SmsProvoider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeRepository codeRepository;
    private final SmsProvoider smsProvoider;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CodeRepository codeRepository, SmsProvoider smsProvoider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.codeRepository = codeRepository;
        this.smsProvoider = smsProvoider;
    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhone(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }


    UserDetails loadUserById(Integer custId) {
        return userRepository.findById(custId).orElseThrow(() -> new UsernameNotFoundException("Customer id not found: " + custId));
    }

    @Transactional
    public ApiResponse register(ReqRegister request){
        Optional<Users> users = userRepository.findByPhone(request.getPhone());
        if (users.isPresent()){
            return new ApiResponse("FAILED: этот номер уже зарегистрирован!", false);
        }
        Users user = new Users();
        ReqActivate activate = new ReqActivate();
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCode(code(secureCode()));
        user.setRoles(roleRepository.findAll());
        activate.setPhone(user.getPhone());
        activate.setCode(user.getCode().getCode());
       if (smsProvoider.sendToPravoider(activate).isResult()) {
           userRepository.save(user);
           return new ApiResponse("SUCCESS", true);
       }
       return new ApiResponse("ERROR: неправильный номер телефона!", false);
    }

    @Transactional
    public ApiResponse activate(ReqActivate activate){
        Optional<Users> user = userRepository.findByPhone(activate.getPhone());
            if (user.isPresent()){
                if (passwordEncoder.matches(activate.getPassword(), user.get().getPassword())) {
                    if (user.get().isActive()) return new ApiResponse("FAILED: этот акоунт уже активирован!", false);
                    if (user.get().getCode().getCode().equals(activate.getCode())) {
                        Integer id = user.get().getCode().getId();
                        user.get().setActive(true);
                        user.get().setCode(null);
                        deleteCode(id);
                        userRepository.save(user.get());
                        return new ApiResponse("SUCCESS",true);
                    }
                }
                return new ApiResponse("FAILED:  неверный код", false);
            }
            return new ApiResponse("FAILED: не правильно указан номер или парол", false);
    }

    @Transactional
    public ApiResponse login(ReqLogin request){
        Optional<Users> user = userRepository.findByPhone(request.getPhone());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            if (user.get().isActive()) {
                return new ApiResponse("SUCCESS", user, true);
            }
            return new ApiResponse("FAILED: узер не активирован!", false);
        }
        return new ApiResponse("FAILED: не правильно указан номер или парол!", false);
    }

    @Transactional
    public Code code(String code){
        Code secureCode = new Code();
        secureCode.setCode(code);
        codeRepository.save(secureCode);
        return secureCode;
    }

    public void deleteCode(Integer id){
        codeRepository.deleteById(id);
    }



    public String secureCode(){
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%05d", num);
    }


}

