package com.example.onefit.rating;

import com.example.onefit.rating.dto.RatingCreateDto;
import com.example.onefit.rating.dto.RatingResponseDto;
import com.example.onefit.rating.dto.RatingUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponseDto> create(@RequestBody @Valid RatingCreateDto ratingCreateDto){
        RatingResponseDto ratingResponseDto = ratingService.create(ratingCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<RatingResponseDto>> get(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<RatingResponseDto> ratingResponseDTOS = ratingService.get(predicate, pageable);
        return ResponseEntity.ok(ratingResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDto> get(@PathVariable UUID id){
        RatingResponseDto ratingResponseDto = ratingService.get(id);
        return ResponseEntity.ok(ratingResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> update(@PathVariable UUID id, @RequestBody RatingUpdateDto ratingUpdateDto){
        RatingResponseDto ratingResponseDto = ratingService.update(ratingUpdateDto, id);
        return ResponseEntity.ok(ratingResponseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        ratingService.delete(id);
    }
}
