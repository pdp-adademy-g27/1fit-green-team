package com.example.onefit.facilities;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.facilities.dto.FacilitiesCreateDto;
import com.example.onefit.facilities.dto.FacilitiesResponseDto;
import com.example.onefit.facilities.dto.FacilitiesUpdateDto;
import com.example.onefit.facilities.entity.Facilities;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacilitiesDtoMapper extends GenericMapper<Facilities, FacilitiesCreateDto, FacilitiesResponseDto, FacilitiesUpdateDto> {


    private final ModelMapper mapper;

    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public Facilities toEntity(FacilitiesCreateDto facilitiesCreateDto) {
        return mapper.map(facilitiesCreateDto , Facilities.class);
    }

    @Override
    public FacilitiesResponseDto toResponse(Facilities facilities) {
        return mapper.map(facilities, FacilitiesResponseDto.class);
    }

    @Override
    public void toUpdate(FacilitiesUpdateDto facilitiesUpdateDto, Facilities facilities) {
        mapper.map(facilitiesUpdateDto , facilities) ;
    }
}
