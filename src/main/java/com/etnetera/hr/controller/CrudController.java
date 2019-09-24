package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.WithId;
import org.springframework.http.HttpEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Interface for all bacic CRUD operations on entity
 * @param <TBaseDto> Type of base DTO
 * @param <TDto> Type of full DTO
 * @param <TId> Type entity's ID
 */
public interface CrudController<TBaseDto, TDto extends WithId<TId>, TId> {
    /**
     * Create new entity
     * @param dto Base DTO of entity
     * @return full dtp of persisted entity
     */
    HttpEntity<TDto> save(@Valid TBaseDto dto);

    /**
     * Update entity
     * @param id entity's ID
     * @param dto base DTO of entity with updated data
     * @return updated full DTO of entity
     */
    HttpEntity<TDto> update(@NotNull TId id, @Valid TBaseDto dto);

    /**
     * Get entity by ID
     * @param id entity' ID value
     * @return founded entity
     */
    HttpEntity<TDto> getById(@NotNull TId id);

    /**
     * Delete entity
     * @param id entity's ID value
     * @return Status of operation
     */
    HttpEntity deleteById(@NotNull TId id);
}
