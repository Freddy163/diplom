package com.example.fishop.controller;

import com.example.fishop.model.User;
import com.example.fishop.repository.UserRepository;
import com.example.fishop.service.ProfileUpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String profilePage(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Если форма еще не отправлялась, создаем новую с данными пользователя
        if (!model.containsAttribute("userForm")) {
            ProfileUpdateForm form = new ProfileUpdateForm();
            form.setEmail(user.getEmail());
            model.addAttribute("userForm", form);
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @Valid @ModelAttribute("userForm") ProfileUpdateForm form,
            BindingResult bindingResult,
            Principal principal,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки, возвращаем на страницу с формой
            User user = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user);
            return "profile";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Используем данные из формы, а не несуществующие переменные
        user.setEmail(form.getEmail());

        if (form.getNewPassword() != null && !form.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        }

        userRepository.save(user);
        return "redirect:/profile?success";
    }
}
