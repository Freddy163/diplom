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

    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImage(image1);
        }
        if (file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImage(image2);
        }
        if (file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImage(image3);
        }
        log.info("Saving new product: Product {}", product.getName());
        Product savedProduct = productRepository.save(product);
        savedProduct.setPreviewImageId((savedProduct.getImages().get(0).getId()));
        productRepository.save(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFilename(file.getOriginalFilename());
        image.setSize(file.getSize());
        image.setContentType(file.getContentType());
        image.setBytes(file.getBytes());
        return image;
    }
}
