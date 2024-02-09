package com.example.onefit.feedback;

import com.example.onefit.feedback.dto.FeedBackCreateDto;
import com.example.onefit.feedback.dto.FeedBackResponseDto;
import com.example.onefit.feedback.dto.FeedBackUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedBackController {

    private final FeedBackService feedBackService;

    @PostMapping
    @PreAuthorize("hasAuthority('feedback:create')")
    public ResponseEntity<FeedBackResponseDto> create(@RequestBody @Valid FeedBackCreateDto feedBackCreateDto){
        FeedBackResponseDto feedBackResponseDto = feedBackService.create(feedBackCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedBackResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('feedback:read')")
    public ResponseEntity<Page<FeedBackResponseDto>> getAll(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<FeedBackResponseDto> feedBackResponseDTOS = feedBackService.getAll(predicate, pageable);
        return ResponseEntity.ok().body(feedBackResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('feedback:read')")
    public ResponseEntity<FeedBackResponseDto> get(@PathVariable UUID id){
        FeedBackResponseDto feedBackResponseDto = feedBackService.get(id);
        return ResponseEntity.ok().body(feedBackResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('feedback:update')")
    public ResponseEntity<FeedBackResponseDto> update(@PathVariable UUID id, @RequestBody FeedBackUpdateDto feedBackUpdateDto){
        FeedBackResponseDto updated = feedBackService.update(feedBackUpdateDto, id);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('feedback:delete')")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        feedBackService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
