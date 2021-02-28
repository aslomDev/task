package com.task.service;

import com.task.entity.Detail;
import com.task.entity.Invoice;
import com.task.entity.Payment;
import com.task.payload.ApiResponse;
import com.task.payload.ReqPayment;
import com.task.repository.DetailRepository;
import com.task.repository.InvoiceRepository;
import com.task.repository.PaymentRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }


    public ApiResponse makePayment(ReqPayment reqPayment){
        Payment newPayment = new Payment();
        Optional<Invoice> invoice = invoiceRepository.findById(reqPayment.getIvoice_id());
        if (!invoice.isPresent()){
            return new ApiResponse("ERROR", false);
        }
        BigDecimal inAmount = invoice.get().getAmount();
        BigDecimal payAmount = reqPayment.getSumma();
        if (inAmount.doubleValue() > payAmount.doubleValue()){
            Double value = inAmount.doubleValue() - payAmount.doubleValue();
            return new ApiResponse("ERROR: недостаточно средств покупка: "+value+"", false); /// response error
        }
        newPayment.setInvoice(invoice.get());
        newPayment.setAmount(reqPayment.getSumma());
        paymentRepository.save(newPayment);
        return new ApiResponse("SUCCESS", newPayment, true);
    }

    public ApiResponse getPaymentDetails(Integer paymentId){
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {
            return new ApiResponse("SUCCESS", payment, true);
        }
        return new ApiResponse("FAILED: not found payment details", false);
    }


}
