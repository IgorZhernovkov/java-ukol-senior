package com.etnetera.hr.specification.field;

import java.io.Serializable;

/**
 * Interface for getting field fefinition by it's name
 */
public interface FieldsDefinition extends Serializable {
    /**
     * Get field field definition by it's name
     * @param fieldName field's name
     * @return definition of field
     */
    FieldDefinitionInfo getFieldDefinition(String fieldName);
}
