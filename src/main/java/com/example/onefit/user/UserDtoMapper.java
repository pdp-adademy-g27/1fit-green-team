package com.example.onefit.user;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserUpdateDto;
import com.example.onefit.user.entity.User;
import org.modelmapper.ModelMapper;

public class UserDtoMapper extends GenericMapper<User, UserCreateDto, UserResponseDto, UserUpdateDto> {
    @Override
    protected ModelMapper getMapper() {
        return null;
    }

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public UserResponseDto toResponse(User user) {
        return null;
    }

    @Override
    public void toUpdate(UserUpdateDto userUpdateDto, User user) {

    }
}
