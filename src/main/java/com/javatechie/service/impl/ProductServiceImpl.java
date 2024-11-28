package com.javatechie.service.impl;

import com.javatechie.entity.Product;
import com.javatechie.entity.Review;
import com.javatechie.repository.ProductRepository;
import com.javatechie.repository.ReviewRepository;
import com.javatechie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) {
        log.info("Service request to fetch product by id: {}", id);
        addDelay();
        return productRepository.findById(id).orElse(null);
    }

    private void addDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviewsForProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return reviewRepository.findByProduct(product);
    }

    public Review addReview(Long productId, String reviewerName, String comment, int rating) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Review review = new Review();
        review.setProduct(product);
        review.setReviewerName(reviewerName);
        review.setComment(comment);
        review.setRating(rating);
        review.setReviewDate(new Timestamp(System.currentTimeMillis()));
        return reviewRepository.save(review);
    }



}
