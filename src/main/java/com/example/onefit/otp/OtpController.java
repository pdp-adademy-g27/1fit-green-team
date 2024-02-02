package com.example.onefit.otp;

import com.example.onefit.common.response.CommonResponse;
import com.example.onefit.common.secirity.JwtService;
import com.example.onefit.otp.otp.OtpService;
import com.example.onefit.otp.otp.dto.ValidatePhoneNumberRequestDto;
import com.example.onefit.user.UserService;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserSignInDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class OtpController {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;

    @PostMapping("/auth/validate")
    public ResponseEntity<CommonResponse> validatePhoneNumber(
            @RequestBody @Valid ValidatePhoneNumberRequestDto requestDto
    ) {
        CommonResponse commonResponse = otpService.sendSms(requestDto);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<UserResponseDto> signUp(
            @RequestBody @Valid UserCreateDto userCreateDto
    ) {
        UserResponseDto userResponseDto = userService.signUp(userCreateDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }


    @PostMapping("/auth/sign-in")
    public ResponseEntity<UserResponseDto> singIn(
            @RequestBody @Valid UserSignInDto signInDto
    ) {
        UserResponseDto userResponseDto = userService.signIn(signInDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }


}