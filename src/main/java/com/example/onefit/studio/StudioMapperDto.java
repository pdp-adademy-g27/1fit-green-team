package com.example.onefit.studio;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.studio.dto.StudioCreateDto;
import com.example.onefit.studio.dto.StudioResponseDto;
import com.example.onefit.studio.dto.StudioUpdateDto;
import com.example.onefit.studio.entity.Studio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudioMapperDto extends GenericMapper<Studio, StudioCreateDto, StudioResponseDto, StudioUpdateDto> {

    private final ModelMapper mapper;
    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public Studio toEntity(StudioCreateDto studioCreateDto) {
        return mapper.map(studioCreateDto, Studio.class);
    }

    @Override
    public StudioResponseDto toResponse(Studio studio) {
        return mapper.map(studio, StudioResponseDto.class);
    }

    @Override
    public void toUpdate(StudioUpdateDto studioUpdateDto, Studio studio) {
        mapper.map(studioUpdateDto, studio);
    }
}
