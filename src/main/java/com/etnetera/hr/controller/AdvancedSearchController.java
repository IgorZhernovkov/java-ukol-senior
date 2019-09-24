package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.data.dto.filter.FilterDto;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Interface for advance seaching data using search DTO
 * @param <TId> Type of ID entity
 * @param <TDto> Type of full DTO
 */
public interface AdvancedSearchController<TId, TDto extends WithId<TId>> {
    /**
     * Find records in entity by
     * @param filter search DTO
     * @param page number of result's page
     * @param size size of page
     * @param sortDirection  direction of sort
     * @param sortColumn column for sorting
     * @return found records
     */
    HttpEntity<SearchResultDto<TId, TDto>> findAll(@NotNull @Valid FilterDto filter,
                                                   @RequestParam Integer page,
                                                   @RequestParam Integer size,
                                                   @RequestParam String sortDirection,
                                                   @RequestParam String sortColumn);
}
