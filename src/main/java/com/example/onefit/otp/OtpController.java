package com.example.onefit.otp;

import com.example.onefit.common.response.CommonResponse;

import com.example.onefit.otp.otp.OtpService;
import com.example.onefit.otp.otp.dto.ValidatePhoneNumberRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/auth/validate")
    public ResponseEntity<CommonResponse> validatePhoneNumber(
            @RequestBody @Valid ValidatePhoneNumberRequestDto requestDto
    ) {
        CommonResponse commonResponse = otpService.sendSms(requestDto);
        return ResponseEntity.ok(commonResponse);
    }

}