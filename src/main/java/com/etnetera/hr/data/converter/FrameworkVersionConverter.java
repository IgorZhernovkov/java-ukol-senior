package com.etnetera.hr.data.converter;

import com.etnetera.hr.data.dto.FrameworkVersionBaseDto;
import com.etnetera.hr.data.dto.FrameworkVersionDto;
import com.etnetera.hr.data.entity.FrameworkVersion;
import org.springframework.stereotype.Component;

/**
 * Implementation of converter between entity and it's DTOs for the FrameworkVersion entity
 */
@Component
public class FrameworkVersionConverter implements Converter<FrameworkVersionBaseDto, FrameworkVersionDto, FrameworkVersion> {
    @Override
    public FrameworkVersionBaseDto convertToBaseDto(FrameworkVersion entity, FrameworkVersionBaseDto baseDto) {
        baseDto.setVersion(entity.getVersion());
        baseDto.setReleaseDate(entity.getReleaseDate());
        return baseDto;
    }

    @Override
    public FrameworkVersionDto convertToDto(FrameworkVersion entity, FrameworkVersionDto dto) {
        convertToBaseDto(entity, dto);
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public FrameworkVersion convertToEntity(FrameworkVersionBaseDto baseDto, FrameworkVersion entity) {
        entity.setVersion(baseDto.getVersion());
        entity.setReleaseDate(baseDto.getReleaseDate());
        return entity;
    }

    @Override
    public FrameworkVersion convertToEntity(FrameworkVersionDto dto, FrameworkVersion entity) {
        convertToEntity((FrameworkVersionBaseDto) dto,  entity);
        entity.setId(dto.getId());
        return entity;
    }
}
