package com.example.onefit.user.permission;

import com.example.onefit.user.permission.dto.PermissionCreateDto;
import com.example.onefit.user.permission.dto.PermissionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponseDto> createPermission(@RequestBody @Valid PermissionCreateDto permissionCreateDto){
        PermissionResponseDto responseDto = permissionService.create(permissionCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    public  ResponseEntity<Page<PermissionResponseDto>> getPermissions(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<PermissionResponseDto> responseDtos = permissionService.getAll(predicate, pageable);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PermissionResponseDto> getPermission(@PathVariable String name){
        PermissionResponseDto responseDto = permissionService.getByName(name);
        return ResponseEntity.ok(responseDto);
    }
}
