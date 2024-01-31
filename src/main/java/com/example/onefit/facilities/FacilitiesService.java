package com.example.onefit.facilities;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.facilities.dto.FacilitiesCreateDto;
import com.example.onefit.facilities.dto.FacilitiesResponseDto;
import com.example.onefit.facilities.dto.FacilitiesUpdateDto;
import com.example.onefit.facilities.entity.Facilities;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class FacilitiesService extends GenericService<UUID, Facilities, FacilitiesResponseDto, FacilitiesCreateDto, FacilitiesUpdateDto> {

    private final FacilitiesRepository repository;
    private final Class<Facilities> entityClass = Facilities.class;
    private final FacilitiesDtoMapper mapper;


    @Override
    protected FacilitiesResponseDto internalCreate(FacilitiesCreateDto facilitiesCreateDto) {
        Facilities facilities = mapper.toEntity(facilitiesCreateDto);
        facilities.setId(UUID.randomUUID());
        repository.save(facilities);
        return mapper.toResponse(facilities);
    }

    @Override
    protected FacilitiesResponseDto internalUpdate(FacilitiesUpdateDto facilitiesUpdateDto, UUID id) {
        Facilities facilities = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExcMessage.FACILITIES_NOTFOUND.formatted(id)));
        mapper.toUpdate(facilitiesUpdateDto, facilities);
        Facilities saved = repository.save(facilities);
        return mapper.toResponse(saved);
    }
}


