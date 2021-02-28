package com.task.controller;

import com.task.entity.Detail;
import com.task.entity.Product;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public HttpEntity<?> makeProduct(@RequestParam Integer id, @RequestBody Product product){
        ApiResponse response = productService.makeProduct(product, id);
        if (response.isResult()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/product")
    public HttpEntity<?> getProductDetails(@RequestParam Integer productId){
        ApiResponse response = productService.getProductDetails(productId);

        if (response.isResult()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/product/list")
    public List<Product> getList(){
        return productService.productList();
    }

}
