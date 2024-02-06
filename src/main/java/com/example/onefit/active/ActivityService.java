package com.example.onefit.active;

import com.example.onefit.active.dto.ActivityCreateDto;
import com.example.onefit.active.dto.ActivityResponseDto;
import com.example.onefit.active.dto.ActivityUpdateDto;
import com.example.onefit.active.entity.Activity;
import com.example.onefit.common.service.GenericService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class ActivityService extends GenericService<UUID, Activity, ActivityResponseDto, ActivityCreateDto, ActivityUpdateDto> {

    private final Class<Activity> entityClass = Activity.class;
    private final ActivityRepository repository;
    private final ActivityDtoMapper mapper;



    @Override
    protected ActivityResponseDto internalCreate(ActivityCreateDto activityCreateDto) {
        return null;
    }

    @Override
    protected ActivityResponseDto internalUpdate(ActivityUpdateDto activityUpdateDto, UUID uuid) {
        return null;
    }
}
