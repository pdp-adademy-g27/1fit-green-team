package com.example.onefit.user.auth;


import com.example.onefit.common.secirity.JwtService;
import com.example.onefit.user.UserService;
import com.example.onefit.user.dto.ResponseDto;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserSignInDto;
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

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam String refreshToken){
        return userService.refreshToken(refreshToken);
    }



}
