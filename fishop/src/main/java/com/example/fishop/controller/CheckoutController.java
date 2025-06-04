package com.example.fishop.controller;

import com.example.fishop.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class CheckoutController {

    @PostMapping("/checkout")
    public String checkout() {
        return "redirect:/order-success";
    }

}


