package com.example.fishop.service;

import com.example.fishop.model.Image;
import com.example.fishop.model.Product;
import com.example.fishop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> listProducts (String name){
        if (name != null) return productRepository.findByName(name);
        return productRepository.findAll();
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

}
