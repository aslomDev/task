package com.task.repository;

import com.task.entity.Customer;
import com.task.projection.Number_of_products_in_year_projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


   /// если надо через параметр то это
//    @Query(value = "select * from customers c right join orders o on o.customers_id != c.id where o.date >= :startDate and o.date <= :endDate", nativeQuery = true)
//    List<Customer> customers_without_orders(@Param("startDate") Calendar startDate, @Param("endDate") Calendar endDate);

    @Query(value = "select * from customer c  right join order o on o.cust_id = c.id where not exists (select from order where order.date > '2016-01-01 00:00:00' and order.date < '2016-12-31 23:59:59' and order.cust_id = c.id and order.cust_id = c.id)", nativeQuery = true)
    List<Customer> customers_without_orders();


    @Query(value = "select c.country as country, count(o.cust_id) as totalOrders from customer c inner join order o on o.cust_id = c.id where o.date > '2016-01-01' and o.date < '2016-12-31'group by country", nativeQuery = true)
    List<Number_of_products_in_year_projection> number_of_products_in_year();

    Optional<Customer> findByPhone(String phone);

    Customer getByPhone(String phone);


}
