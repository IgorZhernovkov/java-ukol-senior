package com.etnetera.hr.service.impl;

import com.etnetera.hr.data.converter.Converter;
import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public abstract class BasicCrudService<TEntity extends WithId<TId>, TBaseDto, TDto extends WithId<TId>, TId> implements CrudService<TBaseDto, TDto, TId> {

    private final Converter<TBaseDto, TDto, TEntity> converter;
    private final CrudRepository<TEntity, TId> repository;

    public BasicCrudService(Converter<TBaseDto, TDto, TEntity> converter, CrudRepository<TEntity, TId> repository) {
        this.converter = converter;
        this.repository = repository;
    }

    protected abstract Dictionary<TBaseDto, TDto, TEntity> getDictionary();

    @Override
    public TDto create(TBaseDto baseDto) {
        TEntity entity = converter.convertToEntity(baseDto, getDictionary().newEntity());
        TEntity savedEntity = repository.save(entity);
        log.debug("Saved entity '{}', id = {}", getDictionary().getEntityClassName(), savedEntity.getId());
        return converter.convertToDto(savedEntity, getDictionary().newDto());
    }

    @Override
    public TDto update(TId id, TBaseDto baseDto) {
        Optional<TEntity> foundEntity = repository.findById(id);
        if( !foundEntity.isPresent() ) {
            throw new NoSuchElementException(String.format("entity '%s' id='%s'", getDictionary().getEntityClassName(), id));
        }
        converter.convertToEntity(baseDto, foundEntity.get());
        TEntity updatedEntity = repository.save(foundEntity.get());
        log.debug("Updated entity '{}', id = {}", getDictionary().getEntityClassName(), updatedEntity.getId());
        return converter.convertToDto(updatedEntity, getDictionary().newDto());
    }

    @Override
    public TDto get(TId id) {
        Optional<TEntity> foundEntity = repository.findById(id);
        if( !foundEntity.isPresent() ) {
            throw new NoSuchElementException(String.format("entity '%s' id='%s'", getDictionary().getEntityClassName(), id));
        }
        log.debug("Got entity '{}', id = {}", getDictionary().getEntityClassName(), foundEntity.get().getId());
        return converter.convertToDto(foundEntity.get(), getDictionary().newDto());
    }

    @Override
    public void deleteById(TId id) {
        repository.deleteById(id);
        log.debug("Deleted entity '{}', id = {}", getDictionary().getEntityClassName(), id);
    }

    public interface Dictionary<TBaseDto, TDto, TEntity> {
        TEntity newEntity();
        String getEntityClassName();

        TBaseDto newBaseDto();
        TDto newDto();
    }
}
