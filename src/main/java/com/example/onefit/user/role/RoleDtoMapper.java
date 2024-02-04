package com.example.onefit.user.role;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.user.permission.entity.Permission;
import com.example.onefit.user.role.dto.RoleCreateDto;
import com.example.onefit.user.role.dto.RoleResponseDto;
import com.example.onefit.user.role.dto.RoleUpdateDto;
import com.example.onefit.user.role.entiy.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleDtoMapper extends GenericMapper<Role, RoleCreateDto, RoleResponseDto, RoleUpdateDto> {
    private final ModelMapper modelMapper;
    @Override
    protected ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public Role toEntity(RoleCreateDto roleCreateDto) {
        return modelMapper.map(roleCreateDto, Role.class);
    }

    @Override
    public RoleResponseDto toResponse(Role role) {
        RoleResponseDto responseDto = modelMapper.map(role, RoleResponseDto.class);

        Set<String> permissions = role
                .getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        responseDto.setPermissions(permissions);

        return responseDto;
    }

    @Override
    public void toUpdate(RoleUpdateDto roleUpdateDto, Role role) {
        modelMapper.map(roleUpdateDto, role);
    }

    public void toEntity(RoleUpdateDto updateDto, Role role) {
        updateDto.setName(updateDto.getName().toLowerCase());
        modelMapper.map(updateDto, role);
    }
}
