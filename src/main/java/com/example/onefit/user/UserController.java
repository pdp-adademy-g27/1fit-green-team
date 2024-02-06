package com.example.onefit.user;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.user.dto.*;
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

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable,
                                                        @RequestParam(required = false) String predicate) {
        Page<UserResponseDto> all = userService.getAll(predicate, pageable);
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


    @PutMapping("/byCourse{userId}/course/{courseId}")
    public ResponseEntity<UserResponseDto> byCourse(@PathVariable UUID userId, @PathVariable UUID courseId) {
        UserResponseDto responseDto = userService.byCourse(userId, courseId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/subscription/{subscriptionId}/user/{userId}")
    public ResponseEntity<UserResponseDto> bySubscription(@PathVariable UUID subscriptionId, @PathVariable UUID userId){
        UserResponseDto subscription = userService.bySubscription(subscriptionId, userId);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/user/{userId}/course/{courseId}/activity/{activityId}/studio/{studioId}")
    public ResponseEntity<Activity> lessonActive(@PathVariable UUID userId, @PathVariable UUID courseId, @PathVariable UUID activityId, @PathVariable UUID studioId) {
        Activity activity = userService.lessonActive(userId, courseId, activityId, studioId);
        return ResponseEntity.ok(activity);
    }

}
