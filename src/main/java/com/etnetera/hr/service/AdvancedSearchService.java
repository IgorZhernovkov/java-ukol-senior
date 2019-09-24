package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.dto.WithId;
import com.etnetera.hr.data.dto.filter.FilterDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/** Generic interface for advanced searching */
public interface AdvancedSearchService<TEntity, TId, TDto extends WithId<TId>>  {
    /**
     * Search using filter DTO, paging adn sorting
     * @param filter Filter DTO
     * @param pageable Paging info object
     * @return records (entity's full DTO)
     */
    SearchResultDto<TId, TDto> findAll(FilterDto filter, Pageable pageable);
}
