package com.example.onefit.notification;

import com.example.onefit.common.variable.Variables;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void sendVerifyCode(String email,int code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Variables.MY_EMAIL);
            message.setTo(email);
            message.setSubject(Variables.VERIFY_CODE_MESSAGE);
            message.setText(String.valueOf(code));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void forgetPassword(String email, String url) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Variables.MY_EMAIL);
            message.setTo(email);
            message.setSubject(Variables.VERIFY_CODE_MESSAGE);
            message.setText(url);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
