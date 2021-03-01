package com.task.controller;

import com.task.entity.Customer;
import com.task.payload.ApiRes;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.service.CustomerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public HttpEntity<?> makeCustomer(@RequestBody Customer customer){
        ApiResponse response = customerService.makeCustomer(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/list")
    public List<Customer> customerList(){
        return customerService.list();
    }

    @GetMapping("/customer")
    public Customer getOne(@RequestParam Integer customerId){
        return customerService.getOne(customerId);
    }



}
