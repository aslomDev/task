package com.task.security;

import com.task.entity.Code;
import com.task.entity.Customer;
import com.task.payload.ApiResponse;
import com.task.payload.ReqActivate;
import com.task.payload.ReqLogin;
import com.task.payload.ReqRegister;
import com.task.repository.CodeRepository;
import com.task.repository.CustomerRepository;
import com.task.repository.RoleRepository;
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
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeRepository codeRepository;
    private final SmsProvoider smsProvoider;

    public AuthService(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CodeRepository codeRepository, SmsProvoider smsProvoider) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.codeRepository = codeRepository;
        this.smsProvoider = smsProvoider;
    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return customerRepository.findByPhone(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }


    UserDetails loadUserById(Integer custId) {
        return customerRepository.findById(custId).orElseThrow(() -> new UsernameNotFoundException("Customer id not found: " + custId));
    }

    @Transactional
    public ApiResponse register(ReqRegister request){
        Optional<Customer> customer = customerRepository.findByPhone(request.getPhone());
        if (customer.isPresent()){
            return new ApiResponse("FAILED: этот номер уже зарегистрирован!", false);
        }
        Customer cust = new Customer();
        ReqActivate activate = new ReqActivate();
        cust.setPhone(request.getPhone());
        cust.setCountry(request.getCountry());
        cust.setAddress(request.getAddress());
        cust.setName(request.getName());
        cust.setPassword(passwordEncoder.encode(request.getPassword()));
        cust.setCode(code(secureCode()));
        cust.setRoles(roleRepository.findAll());
        activate.setPhone(cust.getPhone());
        activate.setCode(cust.getCode().getCode());
       if (smsProvoider.sendToPravoider(activate).isResult()) {
           customerRepository.save(cust);
           return new ApiResponse("SUCCESS", true);
       }
       return new ApiResponse("ERROR: неправильный номер телефона!", false);
    }

    @Transactional
    public ApiResponse activate(ReqActivate activate){
        Optional<Customer> customer = customerRepository.findByPhone(activate.getPhone());
            if (customer.isPresent()){
                if (passwordEncoder.matches(activate.getPassword(), customer.get().getPassword())) {
                    if (customer.get().isActive()) return new ApiResponse("FAILED: этот акоунт уже активирован!", false);
                    if (customer.get().getCode().getCode().equals(activate.getCode())) {
                        Integer id = customer.get().getCode().getId();
                        customer.get().setActive(true);
                        customer.get().setCode(null);
                        deleteCode(id);
                        customerRepository.save(customer.get());
                        return new ApiResponse("SUCCESS",true);
                    }
                }
                return new ApiResponse("FAILED:  неверный код", false);
            }
            return new ApiResponse("FAILED: не правильно указан номер или парол", false);
    }

    @Transactional
    public ApiResponse login(ReqLogin request){
        Optional<Customer> customer = customerRepository.findByPhone(request.getPhone());
        if (customer.isPresent() && passwordEncoder.matches(request.getPassword(), customer.get().getPassword())){
            return new ApiResponse("SUCCESS", customer, true);
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

