package com.example.onefit.user;

import com.example.onefit.user.dto.*;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.create(userCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable,
                                                            @RequestParam(required = false) String predicate) {
        Page<UserResponseDto> all = userService.getAll(predicate,pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable UUID id) {
        UserResponseDto responseDto = userService.get(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDto updateDto) {
        UserResponseDto responseDto = userService.update(updateDto, id);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
