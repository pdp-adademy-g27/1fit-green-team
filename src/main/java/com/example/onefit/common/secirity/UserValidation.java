package com.example.onefit.common.secirity;

import com.example.onefit.user.UserRepository;
import com.example.onefit.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public boolean isValidUserEmail(String email){
      return   userRepository.findByEmail(email).isEmpty();
    }


    public boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$", password);

    }


    public boolean isValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return false;
        }
        return Pattern.matches("^[a-zA-Z0-9_! #$%&'*+/=?`{|}~^. -]+@[a-zA-Z0-9. -]+$", email);
    }


    public boolean isValidPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            return false;
        }
        return Pattern.matches("^\\+998((0-9){2}|[0-9]{2})[0-9]{7}$", phoneNumber);
    }

    
}
