package com.example.onefit.notification.sms;

public interface SmsNotificationService {
    void sendNotification(String phoneNumber, String message);
}
