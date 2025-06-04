package com.example.fishop.controller;

import com.example.fishop.model.Order;
import com.example.fishop.model.User;
import com.example.fishop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public String userOrders(@AuthenticationPrincipal User user, Model model) {
        List<Order> orders = orderRepository.findByUserOrderByCreatedDateDesc(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
        model.addAttribute("order", order);
        return "order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }


}
