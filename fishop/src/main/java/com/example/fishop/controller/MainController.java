package com.example.fishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String Main(Model model) {
        model.addAttribute("message", "Добро пожаловать в магазин рыбалки!");
        return "main";
    }

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main";
    }
}
