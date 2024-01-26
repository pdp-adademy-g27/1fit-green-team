package com.example.onefit.user.permission;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.user.permission.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionRepository extends GenericRepository<Permission, UUID> {
    Set<Permission> findAllByNameIn(Set<String> permissions);
    Optional<Permission> findByName(String name);
}
