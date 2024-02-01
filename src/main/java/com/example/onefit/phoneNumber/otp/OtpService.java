package com.example.onefit.phoneNumber.otp;

import com.example.onefit.common.exeptions.OtpException;
import com.example.onefit.common.response.CommonResponse;
import com.example.onefit.notification.sms.SmsNotificationService;
import com.example.onefit.phoneNumber.otp.entity.Otp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.onefit.phoneNumber.otp.dto.ValidatePhoneNumberRequestDto;



import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
// todo fix logic  here
public class OtpService {
    private final OtpRepository otpRepository;
    private final Random random = new Random();
    @Value("${one-fit.otp.retry-wait-time}")
    private int retryWaitTime;
    @Value("${one-fit.otp.retry-count}")
    private int retryCount;
    @Value("${one-fit.otp.time-to-live}")
    private int timeToLive;
    private final String VERIFICATION_MESSAGE = "Your verification code is: %d%n";
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

            return new CommonResponse("Sms was sent successfully", LocalDateTime.now(), HttpStatus.OK.value());
        }

        Otp otp = existingOtp
                .orElseThrow(() -> new EntityNotFoundException("we didn't send you any verification code"));

        if (otp.getCode() == requestDto.getOtp()) {
            otp.setVerified(true);
            otpRepository.save(otp);
            return new CommonResponse("Otp was successfully verified", LocalDateTime.now(), HttpStatus.OK.value());
        } else {
            return new CommonResponse("Otp was incorrect", LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
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

        return new CommonResponse("Sms was re-sent successfully", LocalDateTime.now(), HttpStatus.OK.value());
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