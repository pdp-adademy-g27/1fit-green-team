package com.example.onefit.user;


import com.example.onefit.active.ActivityDtoMapper;
import com.example.onefit.active.ActivityRepository;
import com.example.onefit.active.dto.ActivityResponseDto;
import com.example.onefit.active.entity.Activity;
import com.example.onefit.common.response.CommonResponse;
import com.example.onefit.common.secirity.JwtService;
import com.example.onefit.exception.*;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.course.CourseRepository;
import com.example.onefit.course.entity.Course;
import com.example.onefit.notification.NotificationService;
import com.example.onefit.otp.otp.OtpRepository;
import com.example.onefit.otp.otp.entity.Otp;
import com.example.onefit.studio.StudioRepository;
import com.example.onefit.studio.entity.Studio;
import com.example.onefit.subscription.SubscriptionRepository;
import com.example.onefit.subscription.entity.Subscription;
import com.example.onefit.user.dto.*;
import com.example.onefit.user.entity.User;
import com.example.onefit.user.role.RoleRepository;
import com.example.onefit.user.role.entiy.Role;
import io.jsonwebtoken.Claims;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.*;
import static com.example.onefit.common.variable.Variables.*;

@Getter
@Service
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> {

    @Value("${one-fit.course.day.duration}")
    private int lessonDuration;

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
    private final RoleRepository roleRepository;
    private final ActivityDtoMapper activityDtoMapper;
    private final NotificationService notificationService;


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
    public ResponseDto signIn(UserSignInDto signInDto) {
        User user = repository.findByPhoneNumber(signInDto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException(ExcMessage.NOT_CORRECT));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(ExcMessage.NOT_CORRECT);
        }
        return mapper.customResponseDto(user);
    }


    @Transactional
    public ResponseDto signUp(UserCreateDto userCreateDto) {
        User entity = mapper.toEntity(userCreateDto);
        Role role = roleRepository.findByName("USER").get();
        entity.setId(UUID.randomUUID());
        entity.setRoles(Set.of(role));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        isPhoneNumberVerified(userCreateDto.getPhoneNumber());
        User saved = repository.save(entity);
        return mapper.customResponseDto(saved);
    }


    @Transactional
    public UserResponseDto bySubscription(UUID subscriptionId, UUID userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(userId)));

        if (!user.isVerify()) {
            throw new AccountNotVerified(ACCOUNT_NOT_VERIFIED);
        }

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
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

        Subscription subscription = user.getSubscription();


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
    public ActivityResponseDto goingToLesson(GoingToLessonDto goingToLessonDto) {

        User user = repository.findById(goingToLessonDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOTFOUND.formatted(goingToLessonDto.getUserId())));

        Activity activity1 = new Activity();

        Set<Course> courses = user.getCourses();

        for (Course course1 : courses) {
            if (course1.getId().equals(goingToLessonDto.getCourseId())) {
                Course course = courseRepository.findById(goingToLessonDto.getCourseId())
                        .orElseThrow(() -> new EntityNotFoundException(COURSE_NOTFOUND.formatted(goingToLessonDto.getCourseId())));

                Activity activity = activityRepository.findById(goingToLessonDto.getActivityId())
                        .orElseThrow(() -> new EntityNotFoundException(ACTIVITY_ID_NOTFOUND.formatted(goingToLessonDto.getActivityId())));

                Studio studio = studioRepository.findById(goingToLessonDto.getStudioId())
                        .orElseThrow(() -> new EntityNotFoundException(STUDIO_ID_NOTFOUND.formatted(goingToLessonDto.getStudioId())));

                if (!user.isMale() && course.isMale()) {
                    throw new NotAllowedFemaleException(NOT_ALLOWED_FEMALE);
                }

                if (user.isMale() && !course.isMale()) {
                    throw new NotAllowedMaleException(NOT_ALLOWED_MALE);
                }

                Subscription subscription = user.getSubscription();
                Integer days = subscription.getDays();

                LocalDateTime start = LocalDateTime.now();
                activity.setStartTime(start);

                activity.setEndTime(start.plusHours(lessonDuration));

                int countLesson = activity.getCountLesson();

                activity.setCountLesson(countLesson + 1);

                activity.setMaxLessons((days / 30) * 12);

                if (activity.getCountLesson() > activity.getMaxLessons()) {
                    throw new BadCredentialsException("the number of lessons is over, buy a new course");
                } else {

                    Set<User> users = activity.getUsers();
                    users.add(user);

                    Set<Course> courses3 = activity.getCourses();
                    courses3.add(course);

                    Set<Studio> studios = activity.getStudios();
                    studios.add(studio);
                    activity.setRateIsActive(true);
                    activityRepository.save(activity);

                    activity1 = activity;
                }
            }
        }
        return activityDtoMapper.toResponse(activity1);
    }

    public String refreshToken(String refreshToken) {
        Claims claims = jwtService.getClaims(refreshToken);
        String userId = claims.getSubject();
        User user = repository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));
        return jwtService.generateToken(user.getPhoneNumber());
    }


    @Transactional
    public CommonResponse forgetPassword() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        if (principal == null) {
            throw new NullPointerException();
        }

        String phoneNumber = principal.getUsername();
        User user = repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        String email = user.getEmail();
        notificationService.forgetPassword(email, FORGET_MESSAGE);

        return new CommonResponse(SEND_SMS_MESSAGE, LocalDateTime.now(), HttpStatus.OK.value());
    }


    @Transactional
    public CommonResponse updatePassword(ForgetPasswordDto forgetPasswordDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        if (principal == null) {
            throw new NullPointerException();
        }

        String username = principal.getUsername();
        User userEntity = repository.findByPhoneNumber(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        String oldPassword = forgetPasswordDto.getOldPassword();
        String newPassword = forgetPasswordDto.getNewPassword();

        if (!oldPassword.equals(newPassword)) {
            throw new IncorrectPassword(PASSWORD_INCORRECT);
        }

        userEntity.setPassword(passwordEncoder.encode(newPassword));
        repository.save(userEntity);

        return new CommonResponse(PASSWORD_UPDATE, LocalDateTime.now(), HttpStatus.OK.value());

    }
}
