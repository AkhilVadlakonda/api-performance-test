package com.javatechie.repository;

import com.javatechie.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN p.category c " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:category IS NULL OR c.name = :category) " +
            "AND (:status IS NULL OR p.status = :status)")
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("status") String status);

}