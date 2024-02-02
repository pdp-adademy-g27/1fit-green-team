package com.example.onefit.otp.otp;

import com.example.onefit.otp.otp.entity.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
}
