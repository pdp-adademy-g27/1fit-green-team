package com.example.onefit.rating;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.rating.dto.RatingCreateDto;
import com.example.onefit.rating.dto.RatingResponseDto;
import com.example.onefit.rating.dto.RatingUpdateDto;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class RatingService extends GenericService<UUID, Rating, RatingResponseDto, RatingCreateDto, RatingUpdateDto> {
    private final RatingRepository repository;
    private final RatingMapperDto mapper;
    private final Class<Rating> entityClass = Rating.class;
    private final UserRepository userRepository;


    @Override
    protected RatingResponseDto internalCreate(RatingCreateDto ratingCreateDto) {
        Rating entity = mapper.toEntity(ratingCreateDto);
        entity.setId(UUID.randomUUID());
        Rating saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    protected RatingResponseDto internalUpdate(RatingUpdateDto ratingUpdateDto, UUID uuid) {
        return null;
    }
}
