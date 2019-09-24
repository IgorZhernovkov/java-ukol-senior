package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

/**
 * Generic implementation of CrudController (all basic CRUD operations on specific entity)
 * @param <TBaseDto> Type of base DTO
 * @param <TDto> Type of full DTO
 * @param <TId> type of ID Entity
 */
@Slf4j
@RequiredArgsConstructor
public class BasicCrudController<TBaseDto, TDto extends WithId<TId>, TId> implements CrudController<TBaseDto, TDto, TId> {
    /** CRUD implementation Service */
    private final CrudService<TBaseDto, TDto, TId> service;
    /**  Class of full DTO */
    private final Class<TDto> dtoClass;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public HttpEntity<TDto> save(@RequestBody TBaseDto baseDto) {
        try {
            TDto resultDto = service.create(baseDto);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);

        } catch (Exception exc) {
            log.warn("Error during saving '{}'", dtoClass.getSimpleName(), exc);
            throw exc;
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public HttpEntity<TDto> update(@PathVariable("id") TId id, @RequestBody TBaseDto baseDto) {
        try {
            TDto resultDto = service.update(id, baseDto);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } catch (Exception exc) {
            log.warn("Error during updating '{}' with id='{}'", dtoClass.getSimpleName(), id, exc);
            throw exc;
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public HttpEntity<TDto> getById(@PathVariable("id") TId id) {
        try {
            TDto resultDto = service.get(id);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } catch (Exception exc) {
            log.warn("Error during getting '{}' with id='{}'", dtoClass.getSimpleName(), id, exc);
            throw exc;
        }
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public HttpEntity deleteById(@PathVariable("id") TId id) {
        try {
            service.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception exc) {
            log.warn("Error during deleting '{}' with id='{}'", dtoClass.getSimpleName(), id, exc);
            throw exc;
        }
    }
}
