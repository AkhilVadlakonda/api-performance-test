package com.javatechie.controller;


import com.javatechie.dto.ProductDetailDTO;
import com.javatechie.dto.ReviewDTO;
import com.javatechie.entity.Category;
import com.javatechie.entity.Product;
import com.javatechie.entity.Review;
import com.javatechie.facade.ProductASyncFacade;
import com.javatechie.facade.ProductSyncFacade;
import com.javatechie.repository.CategoryRepository;
import com.javatechie.repository.ProductRepository;
import com.javatechie.service.ProductService;
import com.javatechie.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductSyncFacade productSyncFacade;

    @Autowired
    private ProductASyncFacade productASyncFacade;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductServiceImpl productService;


   /* @Autowired
    private Product product;*/



    @GetMapping("/{id}/sync")
    public ResponseEntity<ProductDetailDTO> getProductSync(@PathVariable Long id) {
        log.info("Rest request to get product by id sync: {}", id);
        return ResponseEntity.ok(productSyncFacade.getProductDetails(id));
    }

    @GetMapping("/{id}/async")
    public ResponseEntity<ProductDetailDTO> getProductAsync(@PathVariable Long id) {
        log.info("Rest request to get product by id async: {}", id);
        return ResponseEntity.ok(productASyncFacade.getProductDetails(id));
    }

    /*@PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product ) {
        Product saveProduct = productRepository.save(product);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }*/

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDetailDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setStatus(productDTO.getStatus());

        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String status) {

        List<Product> products = productRepository.searchProducts(name, category, status);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long id) {
        List<Review> reviews = productService.getReviewsForProduct(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<Review> addReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewRequest) {
        Review review = productService.addReview(id, reviewRequest.getReviewerName(), reviewRequest.getComment(), reviewRequest.getRating());
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }



}
