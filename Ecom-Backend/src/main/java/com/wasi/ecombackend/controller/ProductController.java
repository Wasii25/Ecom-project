package com.wasi.ecombackend.controller;


import com.wasi.ecombackend.model.Product;
import com.wasi.ecombackend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile image) {
        try {
            Product product1 = service.addProduct(product, image);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{productId}/image")
    public ResponseEntity<byte[]> getInageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id,
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile
    ) {
        Product product2 = null;
        try {
            product2 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(product2 != null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable int id
    ) {
        Product product3 = service.getProductById(id);
        if(product3 != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    //to search products by name
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

}
