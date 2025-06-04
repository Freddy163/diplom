package com.example.fishop.repository;

import com.example.fishop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByName(String name);

    default void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) {

    }
}
