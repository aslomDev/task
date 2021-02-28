package com.task.service;

import com.task.entity.Customer;
import com.task.entity.Invoice;
import com.task.entity.Order;
import com.task.payload.ApiResponse;
import com.task.projection.*;
import com.task.repository.CustomerRepository;
import com.task.repository.DetailRepository;
import com.task.repository.InvoiceRepository;
import com.task.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AllService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DetailRepository detailRepository;

    public AllService(InvoiceRepository invoiceRepository, OrderRepository orderRepository, CustomerRepository customerRepository, DetailRepository detailRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.detailRepository = detailRepository;
    }

    ////   1
    public ApiResponse expired_invoice(){
        List<Invoice> invoice = invoiceRepository.makeInvoice();
        return new ApiResponse("SUCCESS", invoice, true);
    }

    ///   2
//    public ApiResponse wrong_date_invoices(){
//        List<MakeIssued_projection> invoices = invoiceRepository.makeIssued();
//        return new ApiResponse("SUCCESS", invoices.parallelStream().map(event -> new ResponseModel(event.getId(), event.getIssued(), event.getOrder().getId(), event.getOrder().getDate())).collect(Collectors.toList()), true);
//    }

    public ApiResponse wrong_date_invoices(){
        List<MakeIssued_projection> invoices = invoiceRepository.makeIssued();
        return new ApiResponse("SUCCESS", invoices, true);
    }

    /// 3
    public ApiResponse orders_without_details(){
        List<Order> order_without_detail = orderRepository.orders_without_details();
        return new ApiResponse("SUCCESS", order_without_detail, true);
    }

    /// 4
    public ApiResponse customers_without_orders(){
        List<Customer> customers = customerRepository.customers_without_orders();
        return new ApiResponse("SUCCES", customers, true);
    }

    ////5
    public ApiResponse customers_last_orders(){
        List<Customers_last_orders_projection> customers = orderRepository.customers_last_orders();

        return new ApiResponse("SUCCESS", customers, true);
    }

    //// 6
    public ApiResponse overpaid_invoices(){
        List<?> overpaid_invoices = invoiceRepository.overpaid_invoices();

        return new ApiResponse("SUCCESS", overpaid_invoices, true);
    }

    /// 7
    public ApiResponse high_demand_products(){
        List<DetailForm> details = detailRepository.high_demand_products();

        return new ApiResponse("SUCCESS", details, true);
    }

    // 8
    public ApiResponse bulk_products(){
        List<Bulk_products_projection> products_projections = detailRepository.bulk_products();

        return new ApiResponse("SUCCESS", products_projections, true);

    }

    //// 9
    public ApiResponse number_of_products_in_year(){
        List<Number_of_products_in_year_projection> customersInCountry = customerRepository.number_of_products_in_year();

        return new ApiResponse("SUCCESS", customersInCountry, true);
    }


    /// 10
    public ApiResponse orders_without_invoices(){
        List<Order_without_invoice_projection> order_without_invoice_projections = orderRepository.orders_without_invoices();

        return new ApiResponse("SUCCESS", order_without_invoice_projections, true);
    }

}
