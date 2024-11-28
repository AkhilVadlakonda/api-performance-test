package com.javatechie.repository;

import com.javatechie.entity.Review;
import com.javatechie.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
