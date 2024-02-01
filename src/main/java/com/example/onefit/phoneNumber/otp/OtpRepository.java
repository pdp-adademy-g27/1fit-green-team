package com.example.onefit.phoneNumber.otp;

import com.example.onefit.phoneNumber.otp.entity.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
}
