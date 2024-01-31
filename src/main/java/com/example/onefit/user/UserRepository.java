package com.example.onefit.user;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    boolean existsByPhoneNumberOrEmail(String phoneNumber, String email);
}
