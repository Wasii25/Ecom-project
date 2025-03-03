package com.wasi.ecombackend.repo;

import com.wasi.ecombackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepo extends JpaRepository<Product, Integer> {

    //writing a jpa query, language used - JPQL
    @Query("SELECT p from Product p WHERE " +
            "lower(p.name) like lower(concat('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}
