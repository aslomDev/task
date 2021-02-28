package com.task.service;

import com.task.entity.Detail;
import com.task.entity.Order;
import com.task.entity.Product;
import com.task.repository.DetailRepository;
import com.task.repository.OrderRepository;
import com.task.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DetailService {

    private final DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }


    public Detail getOne(Integer detailId){
        return detailRepository.getOne(detailId);
    }

    public List<Detail> detailList(){
        return detailRepository.findAll();
    }
}
