package com.example.onefit.user;


import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.user.dto.*;
import com.example.onefit.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> implements UserDetailsService {

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final Class<User> entityClass = User.class;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, UUID uuid) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByPhoneNumber(username)
                .orElseThrow(() -> new BadCredentialsException(ExcMessage.BAD_CREDENTIALS));
    }
    @Transactional
    public UserResponseDto signIn(UserSignInDto signInDto) {
        User user = repository.findByPhoneNumber(signInDto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException("Username or password is not correct"));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is not correct");
        }

        return mapper.toResponse(user);
    }


}
