package com.example.onefit.rating;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.rating.dto.RatingCreateDto;
import com.example.onefit.rating.dto.RatingResponseDto;
import com.example.onefit.rating.dto.RatingUpdateDto;
import com.example.onefit.rating.entity.Rating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RatingMapperDto extends GenericMapper<Rating, RatingCreateDto, RatingResponseDto, RatingUpdateDto> {
    private final ModelMapper modelMapper;


    @Override
    protected ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public Rating toEntity(RatingCreateDto ratingCreateDto) {
        return modelMapper.map(ratingCreateDto, Rating.class);
    }

    @Override
    public RatingResponseDto toResponse(Rating rating) {
        return modelMapper.map(rating, RatingResponseDto.class);
    }

    @Override
    public void toUpdate(RatingUpdateDto ratingUpdateDto, Rating rating) {
        modelMapper.map(ratingUpdateDto, rating);
    }
}
