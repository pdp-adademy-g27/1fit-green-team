package com.example.onefit.active;

import com.example.onefit.active.dto.ActivityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;


    @GetMapping
    @PreAuthorize(value = "hasAuthority('active:read')")
    public ResponseEntity<Page<ActivityResponseDto>> get(@RequestParam(required = false) String predicate , Pageable pageable){
        Page<ActivityResponseDto> responseDto = activityService.getAll(predicate , pageable);
        return ResponseEntity.ok(responseDto);
    }
}