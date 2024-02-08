package com.example.onefit;

import com.example.onefit.user.UserRepository;
import com.example.onefit.user.entity.User;
import com.example.onefit.user.role.RoleRepository;
import com.example.onefit.user.role.entiy.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
@EnableFeignClients
public class OneFitApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(OneFitApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createAdmin();
    }

    private void createAdmin() {
        String phoneNumber = "998997770707";
        String email = "mavjudbekdev@gmail.com";

        if (!userRepository.existsByPhoneNumberOrEmail(phoneNumber, email)) {
            Role role = roleRepository.findByName("ADMIN").get();
            User user = new User(UUID.randomUUID(),
                    "Admin",
                    "Admin",
                    LocalDate.now(),
                    phoneNumber,
                    email,
                    passwordEncoder.encode("admin"),
                    true,
                    true, LocalDateTime.now(),
                    LocalDateTime.now(),
                    null,
                    Set.of(role),
                    null,null,null,null
                    );
            userRepository.save(user);
        }
    }

}
