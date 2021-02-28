package com.task.controller;

import com.task.entity.Category;
import com.task.entity.Product;
import com.task.payload.ApiRes;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.service.CategoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/category")
    public HttpEntity<?> makeCategory(@RequestBody Category category){
        ApiResponse response = categoryService.makeCategory(category);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/list")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }

    @GetMapping("/category")
    public HttpEntity getProductDetails(@RequestParam Integer productId){
        ApiResponse response = categoryService.getProductDetails(productId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

}
