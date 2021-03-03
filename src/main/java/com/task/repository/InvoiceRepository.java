package com.task.repository;

import com.task.entity.Invoice;
import com.task.projection.MakeIssued_projection;
import com.task.projection.Overpaid_invoices_projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "select * from invoice where due < current_timestamp ", nativeQuery = true)
    List<Invoice> makeInvoice();

    @Query(value = "select i.id as invoice_id,  i.issued as invoice_date, i.ord_id as order_id, o.date as order_date from invoice i inner join orders o on i.ord_id = o.id where i.issued < o.date", nativeQuery = true)
    List<MakeIssued_projection> makeIssued();

    @Query(value = "select i.id as invoiceId,  p.amount - i.amount as should_reimbursed, p.id as payment_id from invoice i inner join payment p on i.id = p.inv_id and i.amount < p.amount", nativeQuery = true)
    List<Overpaid_invoices_projection> overpaid_invoices();

}
