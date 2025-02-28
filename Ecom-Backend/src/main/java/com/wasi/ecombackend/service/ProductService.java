package com.wasi.ecombackend.service;

import com.wasi.ecombackend.model.Product;
import com.wasi.ecombackend.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    private ProductRepo repo;


    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }
}
