package com.example.onefit.otp.otp;

import com.example.onefit.exception.OtpException;
import com.example.onefit.common.response.CommonResponse;
import com.example.onefit.notification.sms.SmsNotificationService;
import com.example.onefit.otp.otp.entity.Otp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.onefit.otp.otp.dto.ValidatePhoneNumberRequestDto;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.example.onefit.common.variable.Variables.*;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final Random random = new Random();
    @Value("${one-fit.otp.retry-wait-time}")
    private int retryWaitTime;
    @Value("${one-fit.otp.retry-count}")
    private int retryCount;
    @Value("${one-fit.otp.time-to-live}")
    private int timeToLive;

    private final SmsNotificationService smsNotificationService;

    @Transactional
    public CommonResponse sendSms(ValidatePhoneNumberRequestDto requestDto) {
        String phoneNumber = requestDto.getPhoneNumber();
        Optional<Otp> existingOtp = otpRepository.findById(phoneNumber);

        if (requestDto.getOtp() == null) {
            if (existingOtp.isPresent()) {
                return reTry(existingOtp.get());
            }

            Otp otp = sendSmsInternal(phoneNumber);
            otpRepository.save(otp);

            return new CommonResponse(SEND_SMS_MESSAGE, LocalDateTime.now(), HttpStatus.OK.value());
        }

        Otp otp = existingOtp
                .orElseThrow(() -> new EntityNotFoundException(WE_DID_VERIFICATION_CODE));

        if (otp.getCode() == requestDto.getOtp()) {
            otp.setVerified(true);
            otpRepository.save(otp);
            return new CommonResponse(OTP_SUCCESSFULLY, LocalDateTime.now(), HttpStatus.OK.value());
        } else {
            return new CommonResponse(OTP_INCORRECT, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        }
    }


    public CommonResponse reTry(Otp otp) {

        if (otp.getLastSentTime().plusSeconds(retryWaitTime).isAfter(LocalDateTime.now())) {
            long resentTime = Duration.between(otp.getLastSentTime(), LocalDateTime.now()).getSeconds();
            throw new OtpException.OtpEarlyResentException(retryWaitTime - resentTime);
        }

        if (otp.getSentCount() >= retryCount) {
            throw new OtpException.OtpLimitExitedException(otp.getSentCount(), otp.getCreated().plusSeconds(timeToLive));
        }

        Otp resentOtp = sendSmsInternal(otp);
        otpRepository.save(resentOtp);

        return new CommonResponse(SMS_RESENT, LocalDateTime.now(), HttpStatus.OK.value());
    }

    private Otp sendSmsInternal(String phoneNumber) {
        int code = random.nextInt(100000, 999999);
        smsNotificationService.sendNotification(phoneNumber, VERIFICATION_MESSAGE.formatted(code));
        return new Otp(phoneNumber, code, 1, LocalDateTime.now(), LocalDateTime.now(), false);
    }

    private Otp sendSmsInternal(Otp otp) {
        int code = random.nextInt(100000, 999999);
        smsNotificationService.sendNotification(otp.getPhoneNumber(), VERIFICATION_MESSAGE.formatted(code));
        otp.setCode(code);
        otp.setLastSentTime(LocalDateTime.now());
        otp.setSentCount(otp.getSentCount() + 1);
        return otp;
    }
}