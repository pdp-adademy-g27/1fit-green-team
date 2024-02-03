package com.example.onefit.user;


import com.example.onefit.common.secirity.JwtService;
import com.example.onefit.exception.DataNotFoundException;
import com.example.onefit.exception.OtpException;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.course.CourseRepository;

import com.example.onefit.course.entity.Course;
import com.example.onefit.exception.SubscriptionNotFoundException;
import com.example.onefit.otp.otp.OtpRepository;
import com.example.onefit.otp.otp.entity.Otp;
import com.example.onefit.subscription.SubscriptionRepository;
import com.example.onefit.subscription.entity.Subscription;
import com.example.onefit.user.dto.*;
import com.example.onefit.user.entity.User;
import io.jsonwebtoken.Claims;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.*;

@Getter
@Service
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> implements UserDetailsService {

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final Class<User> entityClass = User.class;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CourseRepository courseRepository;
    private final JwtService jwtService;



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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByPhoneNumber(username)
                .orElseThrow(() -> new BadCredentialsException(ExcMessage.BAD_CREDENTIALS));
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
            throw new SubscriptionNotFoundException("Subscription not found");
        }
        subscriptionRepository.findById(subscription.getId())
                .orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_NOTFOUND.formatted(subscription.getId())));
        Set<Course> courses = user.getCourses();
        courses.add(course);
        User saved = repository.save(user);
        return mapper.toResponse(saved);
    }


    public String refreshToken(String refreshToken){
        Claims claims = jwtService.getClaims(refreshToken);
        String userId = claims.getSubject();
        User user = repository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return jwtService.generateToken(user.getPhoneNumber());
    }
}
