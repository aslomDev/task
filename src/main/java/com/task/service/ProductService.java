package com.task.service;

import com.task.entity.Category;
import com.task.entity.Detail;
import com.task.entity.Product;
import com.task.payload.ApiRes;
import com.task.payload.ApiResponse;
import com.task.payload.ResponseResult;
import com.task.repository.CategoryRepository;
import com.task.repository.DetailRepository;
import com.task.repository.ProductRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DetailRepository detailRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DetailRepository detailRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.detailRepository = detailRepository;
    }


    @Transactional
    public ApiResponse makeProduct(Product product, Integer id){
        Product newProduct = new Product();
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            return new ApiResponse("FAILED", false);
        }
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPhoto(product.getPhoto());
        newProduct.setPrice(product.getPrice());
        newProduct.setCategory(category.get());

        productRepository.save(newProduct);

        return new ApiResponse("SUCCESS", newProduct, true);

    }

    @Transactional
    public List<Product> productList(){
        return productRepository.findAll();
    }


    public ApiResponse getProductDetails(Integer productId){
        Optional<Detail> detail = detailRepository.findById(productId);
        if (detail.isPresent()){
            return new ApiResponse("SUCCESS", detail, true);
        }
        return new ApiResponse("FAILED: not found detail", false);

    }

}
