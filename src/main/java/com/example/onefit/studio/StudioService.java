package com.example.onefit.studio;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.studio.dto.StudioCreateDto;
import com.example.onefit.studio.dto.StudioResponseDto;
import com.example.onefit.studio.dto.StudioUpdateDto;
import com.example.onefit.studio.entity.Studio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class StudioService extends GenericService<UUID, Studio, StudioResponseDto, StudioCreateDto, StudioUpdateDto> {

    private final StudioRepository repository;

    private final Class<Studio> entityClass = Studio.class;

    private final StudioMapperDto mapper;

    @Override
    protected StudioResponseDto internalCreate(StudioCreateDto studioCreateDto) {
        Studio entity = mapper.toEntity(studioCreateDto);
        entity.setId(UUID.randomUUID());
        Studio saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    protected StudioResponseDto internalUpdate(StudioUpdateDto studioUpdateDto, UUID uuid) {
        Studio studio = repository.findById(uuid).orElseThrow();
        studio.setName(studioUpdateDto.getName());
        studio.setDescription(studioUpdateDto.getDescription());
        studio.setLocation(studioUpdateDto.getLocation());
        studio.setActivities(studioUpdateDto.getActivities());
        return mapper.toResponse(repository.save(studio));
    }
}
