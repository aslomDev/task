package com.task.controller;

import com.task.payload.ApiResponse;
import com.task.payload.ReqTest;
import com.task.service.TestService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final TestService testService;


    public TestController(TestService testService) {
        this.testService = testService;
    }


    @PostMapping("/test")
    public HttpEntity<?> sms(@RequestBody ReqTest reqTest){
        ApiResponse response = testService.sms(reqTest);

        if (response.isResult()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }
}
