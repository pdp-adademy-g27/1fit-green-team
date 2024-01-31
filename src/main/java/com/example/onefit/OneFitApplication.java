package com.example.onefit;

import com.example.onefit.user.UserRepository;
import com.example.onefit.user.entity.User;
import com.example.onefit.user.role.RoleRepository;
import com.example.onefit.user.role.entiy.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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


//    private void createAdmin() {
//        String phoneNumber = "998937797499";
//        String email = "asilbekdev@gmail.com";
//        if (!userRepository.existsByPhoneNumberOrEmail(phoneNumber, email)) {
//            Role role = roleRepository.findByName("ADMIN").get();
//            User user = new User(UUID.randomUUID(),
//                    "Mavjudbek",
//                    "Sokhibov",
//                    LocalDate.of(2003, 1, 23),
//                    "998944222222",
//                    "dev@gmail.com",
//                    passwordEncoder.encode("admin"),
//                    true,
//                    LocalDateTime.now(),
//                    LocalDateTime.now(),
//                    null,
//                    Set.of(role), null, null, null, null);
//            userRepository.save(user);
//        }
//    }

}
