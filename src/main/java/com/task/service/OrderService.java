package com.task.service;

import com.task.entity.*;
import com.task.payload.ApiResult;
import com.task.payload.ResponseResult;
import com.task.payload.ReqOrder;
import com.task.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DetailRepository detailRepository;
    private final InvoiceRepository invoiceRepository;


    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, DetailRepository detailRepository, InvoiceRepository invoiceRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.detailRepository = detailRepository;
        this.invoiceRepository = invoiceRepository;
    }


    public ApiResult order(Integer customerId){
        ApiResult result = new ApiResult();
        Date date = new Date();
        Order order = new Order();
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            order.setDate(date);
            order.setCustomer(customer.get());
            orderRepository.save(order);
            result.setId(order.getId());
        }else {
            result.setMessage("FAILED");
        }


        return result;
    }

    public ApiResult detail(Integer orderId, Integer productId, Short quantity){
        ApiResult result = new ApiResult();
        Detail detail = new Detail();
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Product> product = productRepository.findById(productId);
        if (order.isPresent()){
            detail.setOrder(order.get());
            if (product.isPresent()){
                detail.setProduct(product.get());
                detail.setQuantity(quantity);
                detailRepository.save(detail);
                result.setId(order.get().getId());
                result.setPrice(detail.getProduct().getPrice());
            }else {
                result.setMessage("FAILED");
            }
        }else {
            result.setMessage("FAILED");
        }

        return result;
    }


    @Transactional
    public ResponseResult makeOrder(ReqOrder reqOrder){
        ApiResult result = order(reqOrder.getCustomerId());
        if (result.getId() == null) return new ResponseResult("FAILED", false);
        result = detail(result.getId(), reqOrder.getProductId(), reqOrder.getQuantity());
        if (result.getMessage() != null && result.getMessage().equals("FAILED")) return new ResponseResult("FAILED", false);
            Order order = orderRepository.getOne(result.getId());
            if (order == null) return new ResponseResult("FAILED", false);
                Invoice invoice = new Invoice();
                Date date = new Date();
                invoice.setOrder(order);
                invoice.setIssued(date);
                invoice.setDue(setDays());
                invoice.setAmount(amount(result.getPrice(), reqOrder.getQuantity()));
                invoiceRepository.save(invoice);
        return new ResponseResult("SUCCESS", invoice.getId(), true);

    }


    public BigDecimal amount(BigDecimal price, Short quantity){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Date setDays() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return date = calendar.getTime();
    }

















    public List<Order> orderList(){
        return orderRepository.findAll();
    }

    public Detail getOne(Integer orderId){
        return detailRepository.getOne(orderId);
    }

    public List<Customer> getCustomerId(){
        List<Order> orders = orderRepository.findAll();
        List<Customer> customers = customerRepository.findAll();



        List<Customer> newCustomer = new LinkedList<>();
        for (Order order : orders){

//            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getDate());
            calendar.add(Calendar.DATE, 365);
            calendar.getTime();
            System.out.println(calendar.getTime());


            for (Customer customer : customers){
                if (order.getId() != customer.getId()){
                    newCustomer.add(customer);
                }
            }
        }

        return newCustomer;
    }


}
