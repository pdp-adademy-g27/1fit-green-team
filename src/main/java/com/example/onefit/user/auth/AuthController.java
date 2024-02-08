package com.example.onefit.user.auth;


import com.example.onefit.common.response.CommonResponse;
import com.example.onefit.common.secirity.JwtService;

import com.example.onefit.user.UserService;
import com.example.onefit.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        ResponseDto userResponseDto = userService.signUp(userCreateDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto> singIn(
            @RequestBody @Valid UserSignInDto signInDto
    ) {
        ResponseDto userResponseDto = userService.signIn(signInDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }


    @PostMapping("/forget-password")
    public ResponseEntity<CommonResponse> forgetPassword() {
        CommonResponse commonResponse = userService.forgetPassword();
        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping("/update-password")
    public ResponseEntity<CommonResponse> updatePassword(
            @RequestBody @Valid ForgetPasswordDto forgetPasswordDto
    ) {
        CommonResponse commonResponse = userService.updatePassword(forgetPasswordDto);
        return ResponseEntity.ok(commonResponse);
    }


    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam String refreshToken) {
        return userService.refreshToken(refreshToken);
    }


}
