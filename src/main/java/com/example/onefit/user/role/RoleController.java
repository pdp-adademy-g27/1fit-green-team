package com.example.onefit.user.role;

import com.example.onefit.user.role.dto.RoleCreateDto;
import com.example.onefit.user.role.dto.RoleResponseDto;
import com.example.onefit.user.role.dto.RoleUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;


    @PostMapping
    @PreAuthorize(value = "hasAuthority('role:create')")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid RoleCreateDto roleCreateDto) {
        RoleResponseDto responseDto = roleService.create(roleCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('role:read')")
    public ResponseEntity<Page<RoleResponseDto>> getRoles(@RequestParam(required = false) String predicate, Pageable pageable) {
        Page<RoleResponseDto> responseDtos = roleService.getAll(predicate, pageable);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{name}")
    @PreAuthorize(value = "hasAuthority('role:read')")
    public ResponseEntity<RoleResponseDto> getRole(@PathVariable String name) {
        RoleResponseDto responseDto = roleService.getByName(name);
        return ResponseEntity.ok(responseDto);
    }

}

