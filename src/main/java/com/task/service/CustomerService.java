package com.task.service;

import com.task.entity.Customer;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public ApiResponse makeCustomer(Customer customer){
        Customer newCustom = new Customer();
        newCustom.setName(customer.getName());
        newCustom.setAddress(customer.getAddress());
        newCustom.setCountry(customer.getCountry());
        newCustom.setPhone(customer.getPhone());
        customerRepository.save(customer);
        return new ApiResponse("SUCCES", newCustom, true);
    }

    public List<Customer> list(){
        return customerRepository.findAll();
    }

    public Customer getOne(Integer customerId){
        return customerRepository.getOne(customerId);
    }




}
