package com.etnetera.hr.data.converter;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.entity.HypeLevel;
import org.springframework.stereotype.Component;

/**
 * Implementation of converter between entity and it's DTOs for the HypeLevel entity
 */
@Component
public class HypeLevelConverter implements Converter<HypeLevelBaseDto, HypeLevelDto, HypeLevel> {

    @Override
    public HypeLevelBaseDto convertToBaseDto(HypeLevel entity, HypeLevelBaseDto dto) {
        dto.setName(entity.getName());
        dto.setScore(entity.getScore());
        return dto;
    }

    @Override
    public HypeLevelDto convertToDto(HypeLevel entity, HypeLevelDto dto) {
        convertToBaseDto(entity, dto);
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public HypeLevel convertToEntity(HypeLevelBaseDto dto, HypeLevel entity) {
        entity.setName(dto.getName());
        entity.setScore(dto.getScore());
        return entity;
    }

    @Override
    public HypeLevel convertToEntity(HypeLevelDto dto, HypeLevel entity) {
        convertToEntity((HypeLevelBaseDto) dto, entity);
        entity.setId(dto.getId());
        return entity;
    }
}
