package com.example.onefit.course;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Getter
@RequiredArgsConstructor
public class CourseService extends GenericService<UUID, Course, CourseResponseDto, CourseCreateDto, CourseUpdateDto> {

    private final CourseRepository repository;
    private final Class<Course> entityClass = Course.class;
    private final CourseDtoMapper mapper;
    private final LocationDtoMapper locationDtoMapper;
    private final LocationRepository locationRepository;


    @Transactional
    public CourseResponseDto createCreate(CourseLocationCreateDto createDto) {
        CourseCreateDto createDtoCourse = createDto.getCourse();
        LocationCreateDto locationCreateDto = createDto.getLocation();

        Course course = mapper.toEntity(createDtoCourse);
        Location location = locationDtoMapper.toEntity(locationCreateDto);
        location.setId(UUID.randomUUID());
        locationRepository.save(location);
        course.setId(UUID.randomUUID());
        course.setLocation(location);
        repository.save(course);
        return mapper.toResponse(course);

    }


    public CourseResponseDto create(CourseCreateDto courseCreateDto, LocationCreateDto locationCreateDto) {
        Course entity = mapper.toEntity(courseCreateDto);
        Location location = locationDtoMapper.toEntity(locationCreateDto);
        entity.setId(UUID.randomUUID());
        locationRepository.save(location);
        entity.setLocation(location);
        Course savedCourse = repository.save(entity);
        return mapper.toResponse(savedCourse);
    }

    @Override
    protected CourseResponseDto internalUpdate(CourseUpdateDto courseUpdateDto, UUID uuid) {
        Course course = repository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException(ExcMessage.COURSE_NOTFOUND.formatted(uuid)));
        mapper.toUpdate(courseUpdateDto, course);
        Course saved = repository.save(course);
        return mapper.toResponse(saved);
    }

    @Override
    protected CourseResponseDto internalCreate(CourseCreateDto courseCreateDto) {
        return null;
    }


}
