package com.example.onefit.user.auth;

import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByPhoneNumber(username)
                .orElseThrow(() -> new BadCredentialsException(ExcMessage.BAD_CREDENTIALS));
    }
}
