package com.example.onefit.course;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.course.dto.CourseCreateDto;
import com.example.onefit.course.dto.CourseLocationCreateDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.course.dto.CourseUpdateDto;
import com.example.onefit.course.entity.Course;
import com.example.onefit.location.LocationDtoMapper;
import com.example.onefit.location.LocationRepository;
import com.example.onefit.location.dto.LocationCreateDto;
import com.example.onefit.location.entity.Location;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
//@Getter
@RequiredArgsConstructor
public class CourseService extends GenericService<UUID, Course, CourseResponseDto, CourseCreateDto, CourseUpdateDto> {
    private final CourseRepository courseRepository;
    private final Class<Course> entityClass = Course.class;
    private final CourseDtoMapper courseDtoMapper;
    private final LocationDtoMapper locationDtoMapper;
    private final LocationRepository locationRepository;


    @Transactional
    public CourseResponseDto createCreate(CourseLocationCreateDto createDto) {
        CourseCreateDto createDtoCourse = createDto.getCourse();
        LocationCreateDto locationCreateDto = createDto.getLocation();

        Course course = courseDtoMapper.toEntity(createDtoCourse);
        Location location = locationDtoMapper.toEntity(locationCreateDto);
        location.setId(UUID.randomUUID());
        locationRepository.save(location);
        course.setId(UUID.randomUUID());
        course.setLocation(location);
        courseRepository.save(course);
        return courseDtoMapper.toResponse(course);

    }


    public CourseResponseDto create(CourseCreateDto courseCreateDto, LocationCreateDto locationCreateDto) {
        Course entity = courseDtoMapper.toEntity(courseCreateDto);
        Location location = locationDtoMapper.toEntity(locationCreateDto);
        entity.setId(UUID.randomUUID());
        locationRepository.save(location);
        entity.setLocation(location);
        Course savedCourse = courseRepository.save(entity);
        return courseDtoMapper.toResponse(savedCourse);
    }

    @Override
    protected CourseResponseDto internalUpdate(CourseUpdateDto courseUpdateDto, UUID uuid) {
        Course course = courseRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Course with id %s not found".formatted(uuid)));

        courseDtoMapper.toUpdate(courseUpdateDto, course);
        Course saved = courseRepository.save(course);
        return courseDtoMapper.toResponse(saved);
    }


    @Override
    protected GenericRepository<Course, UUID> getRepository() {
        return null;
    }

    @Override
    protected Class<Course> getEntityClass() {
        return null;
    }

    @Override
    protected GenericMapper<Course, CourseCreateDto, CourseResponseDto, CourseUpdateDto> getMapper() {
        return null;
    }

    @Override
    protected CourseResponseDto internalCreate(CourseCreateDto courseCreateDto) {
        return null;
    }


}
