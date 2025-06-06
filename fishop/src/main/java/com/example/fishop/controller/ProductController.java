package com.example.fishop.controller;//package com.example.fishop.controller;

import com.example.fishop.model.Product;
import com.example.fishop.model.ShoppingCart;
import com.example.fishop.repository.ProductRepository;
import com.example.fishop.service.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String product(@PathVariable(value = "id") long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        model.addAttribute("product", product); // передаем один продукт
        return "product";
    }
    @PostMapping("/products/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id"));

        ShoppingCart cart = getOrCreateCart(session);
        cart.addItem(product, quantity);
        session.setAttribute("cart", cart); // Явное сохранение изменений

        return "redirect:/products";
    }
    private ShoppingCart getOrCreateCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create"; // имя вашего Thymeleaf-шаблона с формой
    }

    @PostMapping("/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product) throws IOException {
//        if (file1.isEmpty() & file2.isEmpty() & file3.isEmpty()) {
//            model.addAttribute("error", "Добавьте хотя бы 1 фото");
//            return "create";
//        }
        productRepository.saveProduct(product, file1, file2, file3);
        return "redirect:/products";


    }
}


/*
import com.example.fishop.model.Product;
import com.example.fishop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

////    @PostMapping("/upload")
////    public String uploadImage(@RequestParam("file") MultipartFile file,
////                              @RequestParam("productId") Long productId) {
////        try {
////            Optional<Product> product = productRepository.findById(productId);
////            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
////            Path path = Paths.get("uploads/" + filename);
////            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
////            product.setImageUrl("/images/" + filename);
////            productRepository.save(product);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return "redirect:/products/" + productId;
////    }
//
//}
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @PostMapping
    public String createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam("image") MultipartFile file) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            product.setImageUrl("/images/" + fileName);
        }

        productRepository.save(product);
        return "redirect:/products";
    }
}
 */