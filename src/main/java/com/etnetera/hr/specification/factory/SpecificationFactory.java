package com.etnetera.hr.specification.factory;

import com.etnetera.hr.data.dto.filter.FilterDto;
import org.springframework.data.jpa.domain.Specification;

/**
 * Generic interface of factory creating Specification object for Spring Data searching using Filter DTO object
 * @param <T>
 */
public interface SpecificationFactory<T> {
    /**
     * Create specification
     * @param filterDto Filter DTO object
     * @return created sprecification
     */
    Specification<T> getSpecification(FilterDto filterDto);
}
