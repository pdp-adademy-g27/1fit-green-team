package com.example.onefit.location;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.location.dto.LocationCreateDto;
import com.example.onefit.location.dto.LocationResponseDto;
import com.example.onefit.location.dto.LocationUpdateDto;
import com.example.onefit.location.entity.Location;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class LocationService extends GenericService<UUID, Location, LocationResponseDto, LocationCreateDto, LocationUpdateDto> {

    private final LocationDtoMapper mapper;
    private final LocationRepository repository;
    private final Class<Location> entityClass = Location.class;

    @Override
    protected LocationResponseDto internalCreate(LocationCreateDto locationCreateDto) {
        Location location = mapper.toEntity(locationCreateDto);
        location.setId(UUID.randomUUID());
        Location savedLocation = repository.save(location);
        return mapper.toResponse(savedLocation);
    }

    @Override
    protected LocationResponseDto internalUpdate(LocationUpdateDto locationUpdateDto, UUID id) {
        Location location = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExcMessage.LOCATION_NOTFOUND.formatted(id)));
        mapper.toUpdate(locationUpdateDto, location);
        Location updatedLocation = repository.save(location);
        return mapper.toResponse(updatedLocation);
    }
}
