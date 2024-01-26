package com.example.onefit.location;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.location.dto.LocationCreateDto;
import com.example.onefit.location.dto.LocationResponseDto;
import com.example.onefit.location.dto.LocationUpdateDto;
import com.example.onefit.location.entity.Location;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationDtoMapper extends GenericMapper<Location, LocationCreateDto, LocationResponseDto, LocationUpdateDto> {

    private final ModelMapper modelMapper;

    @Override
    protected ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public Location toEntity(LocationCreateDto locationCreateDto) {
        return modelMapper.map(locationCreateDto, Location.class);
    }

    @Override
    public LocationResponseDto toResponse(Location location) {
        return modelMapper.map(location, LocationResponseDto.class);
    }

    @Override
    public void toUpdate(LocationUpdateDto locationUpdateDto, Location location) {
        modelMapper.map(locationUpdateDto, location);
    }
}
