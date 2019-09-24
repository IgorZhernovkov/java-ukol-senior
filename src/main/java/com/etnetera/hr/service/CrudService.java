package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.WithId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Generic interface for basic crud operations
 * @param <TBaseDto> Type of base DTO
 * @param <TDto> Type of full DTO
 * @param <TId> Type of ID
 */
public interface CrudService<TBaseDto, TDto extends WithId<TId>, TId> {
    /**
     * Create new object
     * @param baseDto Base DTO
     * @return full DTO saved object
     */
    TDto create(@NotNull @Valid TBaseDto baseDto);

    /**
     * Update existing object
     * @param id object's ID
     * @param baseDto Base Dto
     * @return Full DTO updated object
     */
    TDto update(@NotNull TId id, @NotNull @Valid TBaseDto baseDto);

    /**
     * Get object by id
     * @param id ID of object
     * @return full DTo founded object
     */
    TDto get(@NotNull TId id);

    /**
     * Delete object by it's ID
     * @param id object's ID
     */
    void deleteById(@NotNull TId id);
}
