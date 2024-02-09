package com.example.onefit.category;

import com.example.onefit.category.dto.CategoryCreateDto;
import com.example.onefit.category.dto.CategoryResponseDto;
import com.example.onefit.category.dto.CategoryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('category:create')")
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryCreateDto categoryCreateDto) {
        CategoryResponseDto responseDto = categoryService.create(categoryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('category:read')")
    public ResponseEntity<Page<CategoryResponseDto>> get(@RequestParam(required = false) String predicate, Pageable pageable) {
        Page<CategoryResponseDto> responseDto = categoryService.getAll(predicate, pageable);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('category:read')")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id) {
        CategoryResponseDto responseDto = categoryService.get(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('category:update')")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable UUID id, @RequestBody CategoryUpdateDto updateDto) {
        CategoryResponseDto update = categoryService.update(updateDto, id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('category:delete')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
