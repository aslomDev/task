package com.task.service;

import com.task.entity.Category;
import com.task.entity.Product;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.repository.CategoryRepository;
import com.task.repository.ProductRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }



    @Transactional
    public ApiResponse makeCategory(Category category){
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        categoryRepository.save(newCategory);
        return new ApiResponse("SUCCESS", newCategory, true);
    }

    public List<Category> listCategory(){

        return categoryRepository.findAll();
    }

    @Transactional
    public ApiResponse getProductDetails(Integer productId){
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return new ApiResponse("SUCCESS", product, true);
        }
        return new ApiResponse("FAILED: not found product", false);
    }

}
