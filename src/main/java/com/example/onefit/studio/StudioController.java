package com.example.onefit.studio;

import com.example.onefit.course.CourseService;
import com.example.onefit.course.dto.CourseCreateDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.studio.dto.StudioCreateDto;
import com.example.onefit.studio.dto.StudioResponseDto;
import com.example.onefit.studio.dto.StudioUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/studio")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<StudioResponseDto> create(@RequestBody @Valid StudioCreateDto studioCreateDto){
        StudioResponseDto studioResponseDto = studioService.create(studioCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studioResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<StudioResponseDto>> getAll(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<StudioResponseDto> studioResponseDTOS = studioService.getAll(predicate, pageable);
        return ResponseEntity.ok().body(studioResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudioResponseDto> get(@PathVariable UUID id){
        StudioResponseDto studioResponseDto = studioService.get(id);
        return ResponseEntity.ok().body(studioResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudioResponseDto> update(@PathVariable UUID id, @RequestBody StudioUpdateDto studioUpdateDto){
        StudioResponseDto updated = studioService.update(studioUpdateDto, id);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        studioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/addCourses/{courseId}/studio/{studioId}")
    public ResponseEntity<StudioResponseDto> addCourse(@PathVariable UUID coursId, @PathVariable UUID studioId){
        StudioResponseDto studioResponseDto = studioService.addCourse(coursId, studioId);
        return ResponseEntity.ok(studioResponseDto);
    }



}
