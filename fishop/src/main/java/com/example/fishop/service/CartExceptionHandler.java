package com.example.fishop.service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleCartException(Exception ex, Model model) {
        model.addAttribute("error", "Ошибка работы с корзиной: " + ex.getMessage());
        return "error";
    }
}

