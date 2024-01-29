package com.example.onefit.course;

import com.example.onefit.course.dto.CourseCreateDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.course.dto.CourseUpdateDto;
import com.example.onefit.location.dto.LocationCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseCreateDto courseCreateDto, @RequestBody LocationCreateDto locationCreateDto) {
        CourseResponseDto courseResponseDto = courseService.create(courseCreateDto, locationCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseResponseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> get(@PathVariable UUID id){

        CourseResponseDto responseDto = courseService.get(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDto>> get(@RequestParam(required = false) String predicate , Pageable pageable){

        Page<CourseResponseDto> responseDtos = courseService.getAll(predicate , pageable);

        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable UUID id , @RequestBody CourseUpdateDto updateDto){

        CourseResponseDto update = courseService.update(updateDto, id);

        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseResponseDto> delete(@PathVariable UUID id){

        courseService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}


