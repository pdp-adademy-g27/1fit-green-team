package com.example.onefit.feedback;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.feedback.dto.FeedBackCreateDto;
import com.example.onefit.feedback.dto.FeedBackResponseDto;
import com.example.onefit.feedback.dto.FeedBackUpdateDto;
import com.example.onefit.feedback.entity.FeedBack;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackMapperDto extends GenericMapper<FeedBack, FeedBackCreateDto, FeedBackResponseDto, FeedBackUpdateDto> {
    private final ModelMapper mapper;
    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public FeedBack toEntity(FeedBackCreateDto feedBackCreateDto) {
        return mapper.map(feedBackCreateDto, FeedBack.class);
    }

    @Override
    public FeedBackResponseDto toResponse(FeedBack feedBack) {
        return mapper.map(feedBack, FeedBackResponseDto.class);
    }

    @Override
    public void toUpdate(FeedBackUpdateDto feedBackUpdateDto, FeedBack feedBack) {
        mapper.map(feedBackUpdateDto, feedBack);
    }
}
