package com.task.service;

import com.task.entity.Invoice;
import com.task.entity.Order;
import com.task.repository.InvoiceRepository;
import com.task.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private OrderService orderService;

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    public InvoiceService(InvoiceRepository invoiceRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
    }
    public List<Invoice> invoiceList(){
        return invoiceRepository.findAll();
    }
}
