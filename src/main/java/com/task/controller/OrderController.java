package com.task.controller;

import com.task.entity.Customer;
import com.task.entity.Detail;
import com.task.entity.Order;
import com.task.payload.ApiRes;
import com.task.payload.ResponseResult;
import com.task.payload.ReqOrder;
import com.task.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/list")
    public List<Order> orderList(){
        return orderService.orderList();
    }

    @GetMapping("/order/details")
    public Detail getOne(@RequestParam Integer orderId){
        return orderService.getOne(orderId);
    }

    @PostMapping("/order")
    public HttpEntity<?> makeOrder(@RequestBody ReqOrder reqOrder){
        ResponseResult result = orderService.makeOrder(reqOrder);
        ApiRes response = new ApiRes();

        if (result.isResult()) {
            response.setStatus(result.getMessage());
            response.setInvoice_number(result.getInvoice_number());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(result.getMessage());
    }
}
