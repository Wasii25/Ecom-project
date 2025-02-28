package com.wasi.ecombackend.controller;


import com.wasi.ecombackend.model.Product;
import com.wasi.ecombackend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    public ProductController(ProductService service) {
        this.service = service;
    }

    private ProductService service;

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {

        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {

        if(service.getProductById(id) != null) {
            return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
