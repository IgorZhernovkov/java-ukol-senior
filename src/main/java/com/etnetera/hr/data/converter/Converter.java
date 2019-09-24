package com.etnetera.hr.data.converter;

import com.etnetera.hr.data.dto.WithId;

/**
 * Generic interface for convertations between entity, it's base anf full DTO
 * @param <TBaseDto> Type of base DTO
 * @param <TDto> Type  of full DTO
 * @param <TEntity> Type of Entity
 */
public interface Converter<TBaseDto, TDto extends WithId<?>, TEntity extends WithId<?>> {
    /**
     * Convert an entity to
     * @param entity entity
     * @param dto instance of base DTO
     * @return base DTO of entity
     */
    TBaseDto convertToBaseDto(TEntity entity, TBaseDto dto);

    /**
     * Convert an entity to full DTO
     * @param entity entity
     * @param dto instance of full DTO
     * @return full DTO of entity
     */
    TDto convertToDto(TEntity entity, TDto dto);

    /**
     * Convert an base DTO to entity
     * @param baseDto base DTO of entity
     * @param entity instance of entity class
     * @return entity
     */
    TEntity convertToEntity(TBaseDto baseDto, TEntity entity);

    /**
     * Convert full DTO to entity
     * @param dto full dto of entity
     * @param entity instance of entity class
     * @return entity
     */
    TEntity convertToEntity(TDto dto, TEntity entity);
}
