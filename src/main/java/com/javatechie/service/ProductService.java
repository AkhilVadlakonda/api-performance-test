package com.javatechie.service;

import com.javatechie.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface ProductService {
    Product findById(Long id);
}
