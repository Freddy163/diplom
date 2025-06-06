package com.example.fishop.controller;

import com.example.fishop.model.*;
import com.example.fishop.repository.CartRepository;
import com.example.fishop.repository.OrderRepository;
import com.example.fishop.repository.ProductRepository;
import com.example.fishop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id"));

        ShoppingCart cart = getOrCreateCart(session);
        cart.addItem(product, quantity);
        session.setAttribute("cart", cart); // Явное сохранение изменений

        return "redirect:/products/" + productId;
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("id") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        cart.updateQuantity(productId, quantity);
        session.setAttribute("cart", cart); // Явное сохранение изменений

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("id") Long productId,
                                 HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        cart.removeItem(productId);
        session.setAttribute("cart", cart); // Явное сохранение изменений

        return "redirect:/cart";
    }

    private ShoppingCart getOrCreateCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }


    @PostMapping("/placeOrder")
    public String placeOrder(Principal principal, HttpSession session) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ShoppingCart cart = getOrCreateCart(session);
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        Order order = new Order();
        order.setUser(user);

        // Преобразуем List<CartItem> в Map<Long, Integer>
        Map<Long, Integer> orderItems = cart.getItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getProduct().getId(),
                        CartItem::getQuantity
                ));

        order.setItems(orderItems);
        order.setTotalPrice(calculateTotal(cart));

        orderRepository.save(order);
        session.removeAttribute("cart");

        return "redirect:/orders/" + order.getId();
    }

    private double calculateTotal(ShoppingCart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> {
                    Product product = productRepository.findById(item.getProduct().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    return product.getPrice() * item.getQuantity();
                })
                .sum();
    }
}