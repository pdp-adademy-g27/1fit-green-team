package com.example.onefit.otp;

import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserSignInDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class OtpControllerRequest {
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper objectMapper;

    static {
        objectMapper=new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);

    }
    public UserResponseDto signUp(UserCreateDto userCreateDto){
            try {
                RequestBuilder request = MockMvcRequestBuilders.post("/auth/sign-up")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(userCreateDto));
                ResultActions resultActions = mockMvc.perform(request)
                    .andExpect(status().isCreated());
                String response = resultActions.andReturn().getResponse().getContentAsString();
                    return objectMapper.readValue(response,UserResponseDto.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

         }
    public UserResponseDto signIn(UserSignInDto signInDto) {
        try {
            MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/auth/sign-in")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(signInDto));
            ResultActions resultActions = mockMvc.perform(content)
                    .andExpect(status().isOk());
            String response = resultActions.andReturn().getResponse().getContentAsString();
            return objectMapper.readValue(response,UserResponseDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
