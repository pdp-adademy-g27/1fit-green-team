package com.example.onefit.user;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.subscription.SubscriptionDtoMapper;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserUpdateDto;
import com.example.onefit.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDtoMapper extends GenericMapper<User, UserCreateDto, UserResponseDto, UserUpdateDto> {

    private final ModelMapper mapper;
    private final SubscriptionDtoMapper subscriptionDtoMapper;


    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return mapper.map(userCreateDto,User.class);
    }

    @Override
    public UserResponseDto toResponse(User user) {
       return mapper.map(user, UserResponseDto.class);

    }


    @Override
    public void toUpdate(UserUpdateDto userUpdateDto, User user) {
        mapper.map(userUpdateDto,user);
    }
}
