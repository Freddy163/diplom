package com.example.fishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // если используешь Thymeleaf / шаблонизатор
    }
}
