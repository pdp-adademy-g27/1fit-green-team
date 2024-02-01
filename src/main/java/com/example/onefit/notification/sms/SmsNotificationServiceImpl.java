package com.example.onefit.notification.sms;

import com.example.leetcode.notification.sms.eskiz.dto.SendSmsRequestDto;
import com.example.onefit.notification.sms.eskiz.EskizFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "production")
@RequiredArgsConstructor
@Slf4j
public class SmsNotificationServiceImpl implements SmsNotificationService {

    @Value("${one-fit.notification.sms.eskiz}")
    private String token;
    private final EskizFeignClient feignClient;

    @Override
    public void sendNotification(String phoneNumber, String message) {
        try {
            feignClient.smsSend("Bearer " + token, new SendSmsRequestDto(phoneNumber, message));
        } catch (FeignException.Unauthorized e) {
            token = feignClient.refresh("Bearer " + token).getData().get("token");
            feignClient.smsSend("Bearer " + token, new SendSmsRequestDto(phoneNumber, message));
        } catch (Exception e) {
            log.error("Exception happened while sending notification. phoneNumber: {} message: {}", phoneNumber, message, e);
        }
    }
}
