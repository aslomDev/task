package com.task.controller;

import com.task.entity.Detail;
import com.task.entity.Payment;
import com.task.payload.ApiRes;
import com.task.payload.ApiResponse;
import com.task.payload.ReqPayment;
import com.task.repository.DetailRepository;
import com.task.service.PaymentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public HttpEntity<?> makePayment(@RequestBody ReqPayment reqPayment){
        ApiResponse response = paymentService.makePayment(reqPayment);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/payment/details")
    public HttpEntity<?> getPaymentDetails(@RequestParam Integer id){
        ApiResponse response = paymentService.getPaymentDetails(id);
        if (response.isResult()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

}
