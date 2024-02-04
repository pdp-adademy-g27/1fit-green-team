package com.example.onefit.course;


import com.example.onefit.category.CategoryRepository;
import com.example.onefit.category.entity.Category;
import com.example.onefit.common.service.GenericService;

import com.example.onefit.course.dto.*;
import com.example.onefit.course.entity.Course;
import com.example.onefit.facilities.FacilitiesRepository;
import com.example.onefit.facilities.entity.Facilities;
import com.example.onefit.location.LocationDtoMapper;
import com.example.onefit.location.LocationRepository;
import com.example.onefit.location.dto.LocationCreateDto;
import com.example.onefit.location.entity.Location;
import com.example.onefit.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.*;


@Service
@Getter
@RequiredArgsConstructor
public class CourseService extends GenericService<UUID, Course, CourseResponseDto, CourseCreateDto, CourseUpdateDto> {

    private final CourseRepository repository;
    private final Class<Course> entityClass = Course.class;
    private final CourseDtoMapper mapper;
    private final LocationDtoMapper locationDtoMapper;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FacilitiesRepository facilitiesRepository;


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


    @Transactional
    public CourseResponseDto create(CourseCreateDto courseCreateDto, LocationCreateDto locationCreateDto) {
        Course entity = mapper.toEntity(courseCreateDto);
        Location location = locationDtoMapper.toEntity(locationCreateDto);
        entity.setId(UUID.randomUUID());
        locationRepository.save(location);
        entity.setLocation(location);
        Course savedCourse = repository.save(entity);
        return mapper.toResponse(savedCourse);
    }

    @Transactional
    @Override
    public CourseResponseDto internalUpdate(CourseUpdateDto courseUpdateDto, UUID uuid) {
        Course course = repository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(uuid)));
        mapper.toUpdate(courseUpdateDto, course);
        Course saved = repository.save(course);
        return mapper.toResponse(saved);
    }

    @Override
    protected CourseResponseDto internalCreate(CourseCreateDto courseCreateDto) {
        return null;
    }



    @Transactional
    public CourseResponseDto addCategoryInCourse(UUID categoryId, UUID courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(courseId)));

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(CATEGORY_NOTFOUND.formatted(categoryId)));

        Set<Category> categories = course.getCategories();
        categories.add(category);
        Course saved = repository.save(course);
        System.out.println(categories);
        return mapper.toResponse(saved);
    }


    @Transactional
    public CourseResponseDto addFacilities(UUID facilitiesId, UUID courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(courseId)));

        Facilities facilities = facilitiesRepository.findById(facilitiesId).orElseThrow(
                () -> new EntityNotFoundException(FACILITIES_NOTFOUND.formatted(facilitiesId)));

        Set<Facilities> facilitiesSet = course.getFacilities();
        facilitiesSet.add(facilities);
        Course saved = repository.save(course);
        return mapper.toResponse(saved);
    }
}
