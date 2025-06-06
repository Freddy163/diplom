package com.example.fishop.controller;

import com.example.fishop.model.Order;
import com.example.fishop.model.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class CheckoutController {

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        cart.clear(); // Очищаем корзину
        session.setAttribute("cart", cart); // Обновляем корзину в сессии
        return "redirect:/order-success";
    }

}


