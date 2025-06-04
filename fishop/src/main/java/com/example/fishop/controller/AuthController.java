package com.example.fishop.controller;

import com.example.fishop.model.User;
import com.example.fishop.repository.UserRepository;
import com.example.fishop.service.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final View error;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          View error) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.error = error;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "redirect:/products";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                               BindingResult bindingResult)  {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.user", "Логин уже занят");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles("ROLE_USER"); // По умолчанию роль USER

        userRepository.save(user);
        return "redirect:/login?registered";
    }

}