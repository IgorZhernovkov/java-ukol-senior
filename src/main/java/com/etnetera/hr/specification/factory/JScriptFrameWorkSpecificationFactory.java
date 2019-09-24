package com.etnetera.hr.specification.factory;

import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.specification.JScriptFrameworkSpecification;
import com.etnetera.hr.specification.converter.ValueConverterFactory;
import com.etnetera.hr.specification.field.FieldsDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Implementation of factory creating specification object for JavaScriptFramework
 */
@RequiredArgsConstructor
public class JScriptFrameWorkSpecificationFactory implements SpecificationFactory<JavaScriptFramework> {

    /** Factory conveters string value */
    private final ValueConverterFactory<String> converterFactory;
    /** Definitions of searchable entity's fields */
    private final FieldsDefinition fieldsDefinition;

    /**
     * Create new Specification object for the JavaScriptFramework entity using filter DTO
     * @param filterDto Filter DTO object
     * @return Specification object for the JavaScriptFramework entity
     */
    @Override
    public Specification<JavaScriptFramework> getSpecification(FilterDto filterDto) {
        return new JScriptFrameworkSpecification(filterDto, converterFactory, fieldsDefinition);
    }
}
