package com.example.onefit.otp;

import com.example.onefit.OneFitApplicationTests;
import com.example.onefit.user.UserRepository;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserSignInDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;





@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

class OtpControllerTest extends OneFitApplicationTests {
    @Autowired
    private OtpControllerRequest controllerRequest;
    @Autowired
    private UserRepository userRepository;


    @Test
    void validatePhoneNumber() {
        // given
        // when
        // then
    }

    @Test
    void signUp(){
        // given

        UserCreateDto userCreateDto = new UserCreateDto();

        userCreateDto.setCreated(LocalDateTime.now());
        userCreateDto.setUpdated(LocalDateTime.now());
        userCreateDto.setPassword("12345");
        userCreateDto.setEmail("shuhrat2002@gmail.com");
        userCreateDto.setBirth(LocalDate.of(2002,4,15));
        userCreateDto.setFirstName("Shuhrat");
        userCreateDto.setLastName("Haqnazarov");
        userCreateDto.setPhoneNumber("998883488008");

        UserResponseDto response = controllerRequest.signUp(userCreateDto);

        Assertions.assertEquals("shuhrat2002@gmail.com",response.getEmail());
        Assertions.assertEquals("Shuhrat",response.getFirstName());
        Assertions.assertEquals("Haqnazarov",response.getLastName());
        Assertions.assertEquals("998883488008",response.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(2002,4,15),response.getBirth());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getUpdated());
        Assertions.assertNotNull(response.getCreated());

        Assertions.assertEquals(1,userRepository.findAll().size());

    }

    @Test
    void singIn() {
        UserSignInDto userSignInDto = new UserSignInDto("998883488008", "12345");

        UserResponseDto response = controllerRequest.signIn(userSignInDto);

        Assertions.assertEquals(userRepository.findAll().getFirst().getPassword(), userSignInDto.getPassword());
        Assertions.assertEquals(userRepository.findAll().getFirst().getPhoneNumber(), userSignInDto.getPhoneNumber());

    }




}