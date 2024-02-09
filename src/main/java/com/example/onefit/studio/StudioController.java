package com.example.onefit.studio;

import com.example.onefit.course.CourseService;
import com.example.onefit.studio.dto.StudioCreateDto;
import com.example.onefit.studio.dto.StudioResponseDto;
import com.example.onefit.studio.dto.StudioUpdateDto;
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
@RequestMapping("/studio")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAuthority('studio:create')")
    public ResponseEntity<StudioResponseDto> create(@RequestBody @Valid StudioCreateDto studioCreateDto){
        StudioResponseDto studioResponseDto = studioService.create(studioCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studioResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('studio:read')")
    public ResponseEntity<Page<StudioResponseDto>> getAll(@RequestParam(required = false) String predicate, Pageable pageable){
        Page<StudioResponseDto> studioResponseDTOS = studioService.getAll(predicate, pageable);
        return ResponseEntity.ok().body(studioResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('studio:read')")
    public ResponseEntity<StudioResponseDto> get(@PathVariable UUID id){
        StudioResponseDto studioResponseDto = studioService.get(id);
        return ResponseEntity.ok().body(studioResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('studio:update')")
    public ResponseEntity<StudioResponseDto> update(@PathVariable UUID id, @RequestBody StudioUpdateDto studioUpdateDto){
        StudioResponseDto updated = studioService.update(studioUpdateDto, id);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('studio:delete')")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        studioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/addCourses/{courseId}/studio/{studioId}")
    @PreAuthorize("hasAuthority('studio:addCourse')")
    public ResponseEntity<StudioResponseDto> addCourse(@PathVariable UUID courseId, @PathVariable UUID studioId){
        StudioResponseDto studioResponseDto = studioService.addCourse(courseId, studioId);
        return ResponseEntity.ok(studioResponseDto);
    }



}
