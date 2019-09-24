package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.service.impl.CrudAndSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * Implementation of basic crud and advanced searching operations on entity
 * @param <TId> Type of entity's ID
 * @param <TEntity> Type of entity
 * @param <TBaseDto> Type of base DTO
 * @param <TDto> Type of full DTo
 */
@Slf4j
public class CrudAndSearchController<TId, TEntity extends WithId<TId>, TBaseDto, TDto  extends WithId<TId>> extends BasicCrudController<TBaseDto, TDto, TId>
                                                        implements AdvancedSearchController<TId, TDto> {

    /** Service for CRUD and advanced ssearching operations on an entity */
    private final CrudAndSearchService<TEntity, TBaseDto, TDto, TId> service;


    public CrudAndSearchController(CrudAndSearchService<TEntity, TBaseDto, TDto, TId> service, Class<TDto> dtoClass) {
        super(service, dtoClass);
        this.service = service;
    }

    @PostMapping("/search")
    @Override
    public HttpEntity<SearchResultDto<TId, TDto>> findAll(@RequestBody FilterDto filter,
                                                          @RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "30") Integer size,
                                                          @RequestParam(required = false) String sortDirection,
                                                          @RequestParam(required = false) String sortColumn) {

        final Pageable pageRequest;
        if(sortColumn == null) {
            pageRequest = PageRequest.of(page, size);
            return ResponseEntity.ok(service.findAll(filter, pageRequest));
        }
        Sort sort = sortDirection != null ? Sort.by(new Sort.Order(Sort.Direction.fromString(sortDirection.toUpperCase()), sortColumn)) :
                Sort.by(Sort.Order.asc(sortColumn));
        pageRequest = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(service.findAll(filter, pageRequest));
    }
}
