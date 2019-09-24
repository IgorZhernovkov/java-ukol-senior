package com.etnetera.hr.service.impl;

import com.etnetera.hr.data.converter.Converter;
import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.repository.CrudAndSearchRepository;
import com.etnetera.hr.service.AdvancedSearchService;
import com.etnetera.hr.specification.factory.SpecificationFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

abstract public class CrudAndSearchService<TEntity extends WithId<TId>, TBaseDto, TDto extends WithId<TId>, TId>
        extends BasicCrudService<TEntity, TBaseDto, TDto, TId> implements AdvancedSearchService<TEntity, TId, TDto> {

    private final CrudAndSearchRepository<TEntity, TId> repository;
    private final Converter<TBaseDto, TDto, TEntity> converter;
    private final SpecificationFactory<TEntity> specificationFactory;

    public CrudAndSearchService(Converter<TBaseDto, TDto, TEntity> converter, CrudAndSearchRepository<TEntity, TId> repository,
                                SpecificationFactory<TEntity> specificationFactory) {
        super(converter, repository);
        this.repository = repository;
        this.converter = converter;
        this.specificationFactory = specificationFactory;
    }

    @Override
    public SearchResultDto<TId, TDto> findAll(FilterDto filterDto, Pageable pageable) {
        Specification<TEntity> specification = specificationFactory.getSpecification(filterDto);
        Page<TEntity> page = repository.findAll(specification, pageable);
        Page<TDto> pageOfDtos = page.map( entity -> converter.convertToDto(entity, newInstanceDto()) );
        return convertToSearchResult(pageOfDtos);
    }

    abstract protected TDto newInstanceDto();

    abstract SearchResultDto<TId, TDto>  convertToSearchResult(Page<TDto> pageofDtos);


}
