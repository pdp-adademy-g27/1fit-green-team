package com.example.onefit.location;

import com.example.onefit.location.dto.LocationCreateDto;
import com.example.onefit.location.dto.LocationResponseDto;
import com.example.onefit.location.dto.LocationUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponseDto> create(@Valid @RequestBody LocationCreateDto createDto) {
        LocationResponseDto responseDto = locationService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDto> getById(@PathVariable UUID id) {
        LocationResponseDto responseDto = locationService.get(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<LocationResponseDto>> getAll(@Valid @RequestParam(required = false) String predicate, Pageable pageable) {
        Page<LocationResponseDto> getAll = locationService.get(predicate, pageable);
        return ResponseEntity.ok(getAll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDto> update(@Valid @PathVariable UUID id,
                                                      @RequestBody LocationUpdateDto updateDto) {
        LocationResponseDto responseDto = locationService.update(updateDto, id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        locationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
