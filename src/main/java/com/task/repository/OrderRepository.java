package com.task.repository;

import com.task.entity.Order;
import com.task.projection.Customers_last_orders_projection;
import com.task.projection.Order_without_invoice_projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

     /// если надо через параметр то это
//    @Query(value = "select * from orders o right join detail d on d.orders_id != o.id where o.date < :date", nativeQuery = true)
//    List<Order> orders_without_details(@Param("date") Calendar date);

    @Query(value = "select * from order where not exists (select from detail where order.date > '2016-09-06' or detail.ord_id = order.id  )", nativeQuery = true)
    List<Order> orders_without_details();


    @Query(value = "select c.id as customer_id, c.name as customer_name, o.date as order_date from customer c left join order o on o.cust_id = c.id where concat(cust_id, date) IN (select concat(cust_id, max(date)) from order group by cust_id)", nativeQuery = true)
    List<Customers_last_orders_projection> customers_last_orders();


    @Query(value = "select o.id as orderId, o.date as orderDate, d.quantity as quantity, p.price * quantity as total_price from order o inner join detail d on o.id = d.ord_id inner join product p on d.pr_id = p.id and p.description is not null where not exists (select from invoice i where i.ord_id = o.id)", nativeQuery = true)
    List<Order_without_invoice_projection> orders_without_invoices();

}
