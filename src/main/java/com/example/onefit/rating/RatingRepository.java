package com.example.onefit.rating;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.rating.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends GenericRepository<Rating, UUID> {

}
