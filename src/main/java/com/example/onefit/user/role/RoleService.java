package com.example.onefit.user.role;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.user.permission.PermissionRepository;
import com.example.onefit.user.permission.entity.Permission;
import com.example.onefit.user.role.dto.RoleCreateDto;
import com.example.onefit.user.role.dto.RoleResponseDto;
import com.example.onefit.user.role.dto.RoleUpdateDto;
import com.example.onefit.user.role.entiy.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.example.onefit.common.variable.ExcMessage.ROLE_ID_NOTFOUND;

@Service
@RequiredArgsConstructor
@Getter
public class RoleService extends GenericService<Role, UUID, RoleResponseDto, RoleCreateDto, RoleUpdateDto> {

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;
    private final Class<Role> entityClass = Role.class;
    private final RoleDtoMapper roleDtoMapper;

    @Override
    @Transactional
    protected RoleResponseDto internalCreate(RoleCreateDto roleCreateDto) {
        Role entity = roleDtoMapper.toEntity(roleCreateDto);

        Set<String> dtoPermissionNames = roleCreateDto.getPermissions();
        Set<Permission> permissions = permissionRepository.findAllByNameIn(roleCreateDto.getPermissions());

        if (dtoPermissionNames.size() != permissions.size()) {
            Set<String> permissionNames = permissions
                    .stream()
                    .map(Permission::getName)
                    .collect(Collectors.toSet());

            dtoPermissionNames.removeAll(permissionNames);

            throw new EntityNotFoundException("Permissions with these names are not found. Permissions: %s".formatted(dtoPermissionNames));
        }

        entity.setPermissions(permissions);
        entity.setId(UUID.randomUUID());

        Role saved = repository.save(entity);
        return roleDtoMapper.toResponse(saved);
    }
    
    public RoleResponseDto getByName(String name) {
            Role role = repository
                    .findByName(name)
                    .orElseThrow(
                            () -> new EntityNotFoundException("Role with name: %s not found".formatted(name)));
            return roleDtoMapper.toResponse(role);
    }
        

    public RoleResponseDto update(RoleUpdateDto updateDto, UUID id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_ID_NOTFOUND.formatted(id)));
        roleDtoMapper.toUpdate(updateDto, role);
        Role savedRole = repository.save(role);
        return roleDtoMapper.toResponse(savedRole);
    }
    @Override
    protected RoleResponseDto internalUpdate(RoleUpdateDto roleUpdateDto, Role role) {
        return null;
    }

    @Override
    protected Class<UUID> getEntityClass() {
        return null;
    }
    @Override
    protected GenericRepository<UUID, Role> getRepository() {
        return null;
    }

    @Override
    protected GenericMapper<UUID, RoleCreateDto, RoleResponseDto, RoleUpdateDto> getMapper() {
        return null;
    }


}


