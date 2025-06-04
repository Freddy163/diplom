package com.example.fishop.repository;

import com.example.fishop.model.Cart;
import com.example.fishop.model.Product;
import com.example.fishop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    void deleteByUserAndProduct(User user, Product product);
}
