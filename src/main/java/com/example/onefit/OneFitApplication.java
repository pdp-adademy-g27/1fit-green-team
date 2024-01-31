package com.example.onefit;

import com.example.onefit.user.UserRepository;
import com.example.onefit.user.entity.User;
import com.example.onefit.user.role.RoleRepository;
import com.example.onefit.user.role.entiy.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class OneFitApplication {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(OneFitApplication.class, args);
    }



}
