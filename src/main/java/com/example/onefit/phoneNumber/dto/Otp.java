package com.example.onefit.phoneNumber.dto;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
// todo need to read time to live from property
@RedisHash(value = "otp", timeToLive = 60 * 60 * 24)
@EntityListeners(AuditingEntityListener.class)
public class Otp {
    @Id
    private String phoneNumber;
    private int code;
    private int sentCount;
    private LocalDateTime created;
    private LocalDateTime lastSentTime;
    private boolean isVerified;
}
