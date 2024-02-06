package com.example.onefit.user;


import com.example.onefit.active.ActivityRepository;
import com.example.onefit.active.entity.Activity;
import com.example.onefit.common.secirity.JwtService;
import com.example.onefit.exception.*;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.course.CourseRepository;
import com.example.onefit.course.entity.Course;
import com.example.onefit.otp.otp.OtpRepository;
import com.example.onefit.otp.otp.entity.Otp;
import com.example.onefit.studio.StudioRepository;
import com.example.onefit.studio.entity.Studio;
import com.example.onefit.subscription.SubscriptionRepository;
import com.example.onefit.subscription.entity.Subscription;
import com.example.onefit.user.dto.*;
import com.example.onefit.user.entity.User;
import io.jsonwebtoken.Claims;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.*;

@Getter
@Service
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> {

    @Value("${one-fit.course.day.duration}")
    private  int lessonDuration;

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final Class<User> entityClass = User.class;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CourseRepository courseRepository;
    private final JwtService jwtService;
    private final ActivityRepository activityRepository;
    private final StudioRepository studioRepository;




    @Transactional
    @Override
    public UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        User entity = mapper.toEntity(userCreateDto);
        entity.setId(UUID.randomUUID());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        isPhoneNumberVerified(userCreateDto.getPhoneNumber());
        User saved = repository.save(entity);
        return mapper.toResponse(saved);

    }

    private void isPhoneNumberVerified(String phoneNumber) {
        Otp otp = otpRepository
                .findById(phoneNumber)
                .orElseThrow(() -> new OtpException.PhoneNumberNotVerified(phoneNumber));

        if (!otp.isVerified()) {
            throw new OtpException.PhoneNumberNotVerified(phoneNumber);
        }
    }

    @Transactional
    @Override
    public UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, UUID uuid) {
        User user = repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(uuid)));
        mapper.toUpdate(userUpdateDto, user);
        User savedUser = repository.save(user);
        return mapper.toResponse(savedUser);
    }


    @Transactional
    public UserResponseDto signIn(UserSignInDto signInDto) {
        User user = repository.findByPhoneNumber(signInDto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException(ExcMessage.NOT_CORRECT));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(ExcMessage.NOT_CORRECT);
        }
        return mapper.toResponse(user);
    }


    @Transactional
    public UserResponseDto signUp(UserCreateDto userCreateDto) {
        User entity = mapper.toEntity(userCreateDto);
        entity.setId(UUID.randomUUID());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        isPhoneNumberVerified(userCreateDto.getPhoneNumber());
        User saved = repository.save(entity);
        return mapper.toResponse(saved);
    }


    @Transactional
    public UserResponseDto bySubscription(UUID subscriptionId, UUID userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(userId)));

        if (!user.isVerify()) {
            throw new AccountNotVerified(ACCOUNT_NOT_VERIFIED);
        }

        com.example.onefit.subscription.entity.Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_NOTFOUND.formatted(subscriptionId)));
        user.setSubscription(subscription);
        User saved = repository.save(user);
        return mapper.toResponse(saved);
    }


    @Transactional
    public UserResponseDto byCourse(UUID userId, UUID courseId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(userId)));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(courseId)));

        com.example.onefit.subscription.entity.Subscription subscription = user.getSubscription();


        if (subscription == null) {
            throw new SubscriptionNotFoundException(SUBSCRIPTION_NOTFOUND_WITH_USER);
        }
        if (!user.isMale() && course.isMale()) {
            throw new NotAllowedFemaleException(NOT_ALLOWED_FEMALE);
        }

        if (user.isMale() && !course.isMale()) {
            throw new NotAllowedMaleException(NOT_ALLOWED_MALE);
        }

        subscriptionRepository
                .findById(subscription.getId())
                .orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_NOTFOUND
                        .formatted(subscription.getId())));
        Set<Course> courses = user.getCourses();

        courses.add(course);


        User saved = repository.save(user);
        return mapper.toResponse(saved);
    }
    @Transactional
    public Activity lessonActive(UUID userId, UUID courseId,UUID activityId, UUID studioId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(userId)));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(courseId)));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException(ACTIVITY_ID_NOTFOUND.formatted(activityId)));

        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new EntityNotFoundException(STUDIO_ID_NOTFOUND.formatted(studioId)));


        if (!user.isMale() && course.isMale()) {
            throw new NotAllowedFemaleException(NOT_ALLOWED_FEMALE);
        }

        if (user.isMale() && !course.isMale()) {
            throw new NotAllowedMaleException(NOT_ALLOWED_MALE);
        }


        com.example.onefit.subscription.entity.Subscription subscription = user.getSubscription();
        Integer days = subscription.getDays();

        Set<User> users = activity.getUsers();
        users.add(user);

        Set<Course> courses = activity.getCourses();
        courses.add(course);

        Set<Studio> studios = activity.getStudios();
        studios.add(studio);

        LocalDateTime start = LocalDateTime.now();
        activity.setStartTime(start);

        activity.setEndTime(start.plusHours(lessonDuration));

        int countLesson = activity.getCountLesson();

        activity.setCountLesson(countLesson+1);

        activity.setMaxLessons((days/30)*12);

        if(activity.getCountLesson()<=activity.getMaxLessons()){
            activity.setRateIsActive(true);
        }
        activityRepository.save(activity);

        return activity;
    }




    public String refreshToken(String refreshToken) {
        Claims claims = jwtService.getClaims(refreshToken);
        String userId = claims.getSubject();
        User user = repository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));
        return jwtService.generateToken(user.getPhoneNumber());
    }
}
