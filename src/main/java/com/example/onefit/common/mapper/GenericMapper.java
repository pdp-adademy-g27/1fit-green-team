package com.example.onefit.common.mapper;

import org.modelmapper.ModelMapper;

public abstract class GenericMapper<ENTITY, CREATE_DTO, RESPONSE_DTO, UPDATE_DTO> {
    protected abstract ModelMapper getMapper();
    public abstract ENTITY toEntity(CREATE_DTO createDto);

    public abstract RESPONSE_DTO toResponse(ENTITY entity);
    public abstract void toUpdate(UPDATE_DTO updateDto, ENTITY entity );
}
