package com.task.controller;

import com.task.payload.ApiRes;
import com.task.payload.ApiResponse;
import com.task.service.AllService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AllController {

    private final AllService allService;

    public AllController(AllService allService) {
        this.allService = allService;
    }

    @GetMapping("/expired_invoice")
    public HttpEntity<?> expired_invoice(){
        ApiResponse response = allService.expired_invoice();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> wrong_date_invoices(){
        ApiResponse response = allService.wrong_date_invoices();
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/orders_without_details")
    public HttpEntity<?> orders_without_details(){
        ApiResponse response = allService.orders_without_details();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/customers_without_orders")
    public HttpEntity<?> customers_without_orders(){
        ApiResponse response = allService.customers_without_orders();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers_last_orders")
    public HttpEntity<?> customers_last_orders(){
        ApiResponse response = allService.customers_last_orders();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> overpaid_invoices(){
        ApiResponse response = allService.overpaid_invoices();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/high_demand_products")
    public HttpEntity<?> high_demand_products(){
        ApiResponse response = allService.high_demand_products();

        return ResponseEntity.ok(response);
    }



    @GetMapping("/bulk_products")
    public HttpEntity<?> bulk_products(){
        ApiResponse response = allService.bulk_products();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> number_of_products_in_year(){
        ApiResponse response = allService.number_of_products_in_year();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> orders_without_invoices(){
        ApiResponse response = allService.orders_without_invoices();

        return ResponseEntity.ok(response);
    }






}
