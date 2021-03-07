//package com.task.controller;
//
//import com.task.payload.*;
//import com.task.security.AuthService;
//import com.task.security.JwtTokenProvider;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//    private final AuthService authService;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthenticationManager authenticate;
//
//    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticate) {
//        this.authService = authService;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.authenticate = authenticate;
//    }
//
//    @PostMapping("/register")
//    public HttpEntity<?> register(@RequestBody ReqRegister request){
//        ApiResponse response = authService.register(request);
//        if(response.isResult()){
//            return ResponseEntity.status(HttpStatus.CREATED).body("Вам отправили код");
//        }
//        return ResponseEntity.ok(response.getMessage());
//    }
//
//    @PostMapping("/activate")
//    public HttpEntity<?> activate(@RequestBody ReqActivate request){
//        ApiResponse response = authService.activate(request);
//        if (response.isResult()){
//            return ResponseEntity.ok(getApiToken(request.getPhone(), request.getPassword()));
//        }
//        return ResponseEntity.ok(response.getMessage());
//    }
//
//    @PostMapping("/login")
//    public HttpEntity<?> login(@RequestBody ReqLogin request){
//        ApiResponse response = authService.login(request);
//        if (response.isResult()){
//            return ResponseEntity.accepted()
//                    .body(getApiToken(request.getPhone(), request.getPassword()));
//        }
//        return ResponseEntity.ok(response.getMessage());
//    }
//
//    private HttpEntity<?> getApiToken(String login, String password) {
//        Authentication authentication = authenticate.authenticate(
//                new UsernamePasswordAuthenticationToken(login, password)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtTokenProvider.generateToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));
//    }
//
//
//}
