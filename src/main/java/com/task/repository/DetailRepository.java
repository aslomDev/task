package com.task.repository;

import com.task.entity.Detail;
import com.task.projection.DetailForm;
import com.task.projection.Bulk_products_projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {

    @Query(value = "select pr_id as productId, count(pr_id) as TotalOrdered from detail group by pr_id having count (*) > 10", nativeQuery = true)
    List<DetailForm> high_demand_products();

    @Query(value = "select pr_id as productId, p.price from detail d right join product p on p.id = pr_id  group by pr_id, p.price having count (*) > 8 ", nativeQuery = true)
    List<Bulk_products_projection> bulk_products();



    //// select products_id as productId from detail d right join product p on p.id = products_id  group by products_id having count (*) > 8


}
