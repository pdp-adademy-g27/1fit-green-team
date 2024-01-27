package com.example.onefit.user.permission;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.user.permission.dto.PermissionCreateDto;
import com.example.onefit.user.permission.dto.PermissionResponseDto;
import com.example.onefit.user.permission.dto.PermissionUpdateDto;
import com.example.onefit.user.permission.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionDtoMapper extends GenericMapper<Permission, PermissionCreateDto, PermissionResponseDto, PermissionUpdateDto> {
    private final ModelMapper modelMapper;
    @Override
    protected ModelMapper getMapper() {
        return null;
    }

    @Override
    public Permission toEntity(PermissionCreateDto permissionCreateDto) {
        return modelMapper.map(permissionCreateDto, Permission.class);
    }

    @Override
    public PermissionResponseDto toResponse(Permission permission) {
        return modelMapper.map(permission, PermissionResponseDto.class);
    }

    @Override
    public void toUpdate(PermissionUpdateDto permissionUpdateDto, Permission permission) {
        modelMapper.map(permissionUpdateDto, permission);
    }
}
