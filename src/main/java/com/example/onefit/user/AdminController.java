package com.example.onefit.user;

import com.example.onefit.user.permission.PermissionService;
import com.example.onefit.user.permission.dto.PermissionResponseDto;
import com.example.onefit.user.permission.dto.PermissionUpdateDto;
import com.example.onefit.user.role.RoleService;
import com.example.onefit.user.role.dto.RoleResponseDto;
import com.example.onefit.user.role.dto.RoleUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleService roleService;
    private final PermissionService permissionService;
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable UUID roleId, @RequestBody @Valid RoleUpdateDto updateDto){
        RoleResponseDto roleResponseDto = roleService.update(updateDto, roleId);
        return ResponseEntity.ok(roleResponseDto);
    }
    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionResponseDto> updatePermission(@PathVariable UUID permissionId, @RequestBody PermissionUpdateDto permissionUpdateDto){
        PermissionResponseDto update = permissionService.update(permissionUpdateDto, permissionId);
        return ResponseEntity.ok(update);

    }
}
