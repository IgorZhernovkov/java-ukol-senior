package com.etnetera.hr.specification.field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.JoinType;

/**
 * Using for describing dearchable field of entity
 */
@Getter
@RequiredArgsConstructor
public class FieldDefinitionInfo {
    /** name of filed */
    private final String fieldName;
    /** class of field */
    private final Class<?> valueClass;
    /**
     * meta informatio for creating Path object for CriteriaBuilder.
     * Empty for root fields of entity. But for fields of child entities we must fill it by names of fields in the parent entity, splitting by dot.
     * For example, for searching by field JavaScriptFramework.hypeLevel.releaseDate we need to use "hypeLevel" value in
     * the refEntityNames and "releaseDate" in the fieldName.
     */
    private final String refEntityNames;
}
