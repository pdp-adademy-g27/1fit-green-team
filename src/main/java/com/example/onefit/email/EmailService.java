package com.example.onefit.email;

import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.email.dto.EmailDto;
import com.example.onefit.exception.DataNotFoundException;
import com.example.onefit.exception.IncorrectPassword;
import com.example.onefit.exception.TimeOut;
import com.example.onefit.notification.NotificationService;
import com.example.onefit.user.UserRepository;
import com.example.onefit.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.example.onefit.common.variable.ExcMessage.EMAIL_NOTFOUND;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final RedisTemplate<String, EmailDto> redisTemplate;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final Random random = new Random();

    public void emailVerified(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException(ExcMessage.USER_NOT_FOUND));

        int code = random.nextInt(1000, 10000);
        notificationService.sendVerifyCode(email, code);
        EmailDto emailDto = new EmailDto(email, String.valueOf(code));
        redisTemplate.opsForValue().set(email, emailDto, 3, TimeUnit.MINUTES);
    }


    public String verified(EmailDto emailEntity) {
        EmailDto email = redisTemplate.opsForValue().get(emailEntity.getEmail());

        User user = userRepository.findByEmail(emailEntity.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(EMAIL_NOTFOUND));

        if (email != null) {
            if (email.getMessage().equals(emailEntity.getMessage())) {
                user.setVerify(true);
                return ExcMessage.SUCCESSFULLY_VERIFICATION;
            }
            throw new IncorrectPassword(ExcMessage.INCORRECT_EMAIL_VER);
        }
        throw new TimeOut(ExcMessage.TIME_OUT);
    }


}
