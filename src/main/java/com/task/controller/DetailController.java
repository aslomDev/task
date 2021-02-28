package com.task.controller;

import com.task.entity.Detail;
import com.task.entity.Order;
import com.task.service.DetailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DetailController {
    private final DetailService detailService;
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/detail/list")
    public List<Detail> detailList(){
        return detailService.detailList();
    }
}
