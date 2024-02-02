package com.example.onefit;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
@EnableFeignClients
public class OneFitApplication {
    public static void main(String[] args) {
        SpringApplication.run(OneFitApplication.class, args);
    }
}
