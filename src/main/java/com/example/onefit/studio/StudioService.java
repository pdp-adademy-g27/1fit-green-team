package com.example.onefit.studio;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.studio.dto.StudioCreateDto;
import com.example.onefit.studio.dto.StudioResponseDto;
import com.example.onefit.studio.dto.StudioUpdateDto;
import com.example.onefit.studio.entity.Studio;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.STUDIO_NOTFOUND;

@Service
@Getter
@RequiredArgsConstructor
public class StudioService extends GenericService<UUID, Studio, StudioResponseDto, StudioCreateDto, StudioUpdateDto> {

    private final StudioRepository repository;

    private final Class<Studio> entityClass = Studio.class;

    private final StudioMapperDto mapper;

    @Transactional
    @Override
    public StudioResponseDto internalCreate(StudioCreateDto studioCreateDto) {
        Studio entity = mapper.toEntity(studioCreateDto);
        entity.setId(UUID.randomUUID());
        Studio saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    @Override
    public StudioResponseDto internalUpdate(StudioUpdateDto studioUpdateDto, UUID id) {
        Studio studio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(STUDIO_NOTFOUND.formatted(id)));
        studio.setName(studioUpdateDto.getName());
        studio.setDescription(studioUpdateDto.getDescription());
        studio.setLocation(studioUpdateDto.getLocation());
        studio.setActivities(studioUpdateDto.getActivities());
        return mapper.toResponse(repository.save(studio));
    }
}
