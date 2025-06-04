package com.example.fishop.repository;

import com.example.fishop.model.Order;
import com.example.fishop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedDateDesc(User user);
}
