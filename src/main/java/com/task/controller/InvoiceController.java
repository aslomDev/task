package com.task.controller;

import com.task.entity.Invoice;
import com.task.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private final InvoiceService  invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoice/list")
    public List<Invoice> invoiceList(){
        return invoiceService.invoiceList();
    }
}
