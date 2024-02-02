package com.example.onefit.common.secirity;

import com.example.onefit.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public boolean isValidEmail(String email){
      return   userRepository.findByEmail(email).isEmpty();
    }


    public boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$", password);

    }
}
