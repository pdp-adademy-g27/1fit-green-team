package com.example.onefit.user;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.user.dto.*;
import com.example.onefit.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> implements UserDetailsService {

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final Class<User> entityClass = User.class;


    @Override
    protected UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, UUID uuid) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        return repository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExcMessage.USERNAME_NOTFOUND));
    }


}
