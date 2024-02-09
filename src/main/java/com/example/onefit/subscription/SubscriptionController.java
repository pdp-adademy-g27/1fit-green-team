package com.example.onefit.subscription;

import com.example.onefit.subscription.dto.SubscriptionCreateDto;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.dto.SubscriptionUpdateDto;
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
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('subscription:create')")
    public ResponseEntity<SubscriptionResponseDto> create(@RequestBody @Valid SubscriptionCreateDto subscriptionCreateDto){
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.create(subscriptionCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponseDto);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('subscription:read')")
    public ResponseEntity<Page<SubscriptionResponseDto>> getAll(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<SubscriptionResponseDto> subscriptionResponseDTOS = subscriptionService.getAll(predicate, pageable);
        return ResponseEntity.ok().body(subscriptionResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('subscription:read')")
    public ResponseEntity<SubscriptionResponseDto> get(@PathVariable UUID id){
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.get(id);
        return ResponseEntity.ok().body(subscriptionResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('subscription:update')")
    public ResponseEntity<SubscriptionResponseDto> update(@PathVariable UUID id, @RequestBody SubscriptionUpdateDto subscriptionUpdateDto){
        SubscriptionResponseDto updated = subscriptionService.update(subscriptionUpdateDto, id);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('subscription:delete')")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        subscriptionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}