package com.example.onefit.active;

import com.example.onefit.active.dto.ActivityCreateDto;
import com.example.onefit.active.dto.ActivityResponseDto;
import com.example.onefit.active.dto.ActivityUpdateDto;
import com.example.onefit.active.entity.Activity;
import com.example.onefit.common.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityDtoMapper extends GenericMapper<Activity, ActivityCreateDto, ActivityResponseDto, ActivityUpdateDto> {

    private final ModelMapper mapper;
    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public Activity toEntity(ActivityCreateDto activityCreateDto) {
        return mapper.map(activityCreateDto,Activity.class);
    }

    @Override
    public ActivityResponseDto toResponse(Activity activity) {
        return mapper.map(activity, ActivityResponseDto.class);
    }

    @Override
    public void toUpdate(ActivityUpdateDto activityUpdateDto, Activity activity) {
        mapper.map(activityUpdateDto,activity);
    }
}
