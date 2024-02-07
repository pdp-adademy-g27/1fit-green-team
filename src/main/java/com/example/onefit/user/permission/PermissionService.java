package com.example.onefit.user.permission;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.user.permission.dto.PermissionCreateDto;
import com.example.onefit.user.permission.dto.PermissionResponseDto;
import com.example.onefit.user.permission.dto.PermissionUpdateDto;
import com.example.onefit.user.permission.entity.Permission;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import static com.example.onefit.common.variable.ExcMessage.PERMISSION_ID_NOTFOUND;

@Service
@RequiredArgsConstructor
public class PermissionService extends GenericService<Permission, UUID, PermissionResponseDto, PermissionCreateDto, PermissionUpdateDto> {
    private final PermissionDtoMapper permissionMapper;
    private final Class<Permission> entityClass = Permission.class;
    private final PermissionRepository permissionRepository;


    @Transactional
    @Override
    protected PermissionResponseDto internalCreate(PermissionCreateDto permissionCreateDto) {
        Permission entity = permissionMapper.toEntity(permissionCreateDto);
        entity.setId(UUID.randomUUID());
        Permission saved = permissionRepository.save(entity);
        return permissionMapper.toResponse(saved);
    }

    @Override
    protected PermissionResponseDto internalUpdate(PermissionUpdateDto permissionUpdateDto, Permission permission) {
        return null;
    }

    public PermissionResponseDto getByName(String name) {
        Permission permission = permissionRepository
                .findByName(name)
                .orElseThrow(
                        () -> new EntityNotFoundException("Role with name: %s not found".formatted(name))
                );
        return permissionMapper.toResponse(permission);
    }


    @Override
    protected GenericRepository<UUID, Permission> getRepository() {
        return null;
    }

    @Override
    protected Class<UUID> getEntityClass() {
        return null;
    }

    @Override
    protected GenericMapper<UUID, PermissionCreateDto, PermissionResponseDto, PermissionUpdateDto> getMapper() {
        return null;
    }

    public PermissionResponseDto update(PermissionUpdateDto permissionUpdateDto, UUID id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PERMISSION_ID_NOTFOUND.formatted(id)));
        permissionMapper.toUpdate(permissionUpdateDto, permission);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(savedPermission);

    }

}
