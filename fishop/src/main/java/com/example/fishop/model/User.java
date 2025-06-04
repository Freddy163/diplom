package com.example.fishop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String email;
    private String roles = "ROLE_USER"; // Роли через запятую, если их несколько

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(","))
                .map(String::trim) // Удаляем пробелы вокруг ролей
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()); // Более надежный способ, чем .toList()
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Или ваша логика проверки
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Или ваша логика проверки
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Или ваша логика проверки
    }

    @Override
    public boolean isEnabled() {
        return true; // Или ваша логика проверки
    }
}