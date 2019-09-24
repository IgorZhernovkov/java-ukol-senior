package com.etnetera.hr.specification.field;

import com.etnetera.hr.data.entity.FrameworkVersion;
import com.etnetera.hr.data.entity.HypeLevel;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.specification.annotation.NotSearchable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Class contains searchable fields's definitions of the JavaScriptFramework enity
 */
public class JScriptFrameworkFieldsDefinition implements FieldsDefinition{

    private final Map<String, FieldDefinitionInfo> fieldsDefinitions;

    public JScriptFrameworkFieldsDefinition() {

        Map<String, FieldDefinitionInfo> definitions = new HashMap<>();

        // Fields of JavaScriptFramework
        definitions.putAll( retrieveFieldsDefinitions(JavaScriptFramework.class, "") );
        // Fields of HypeLevel
        definitions.putAll( retrieveFieldsDefinitions(HypeLevel.class, "hypeLevel") );
        // Fields of FrameworkVersion
        definitions.putAll( retrieveFieldsDefinitions(FrameworkVersion.class, "versions") );
        this.fieldsDefinitions = Collections.unmodifiableMap(definitions);
    }

    /**
     * Get field definition using it's name
     * @param fieldName field's name
     * @return field definition
     */
    @Override
    public FieldDefinitionInfo getFieldDefinition(String fieldName) {
        if (fieldsDefinitions.containsKey(fieldName)) {
            return fieldsDefinitions.get(fieldName);
        }
        throw new NoSuchElementException(String.format("the field with name '%s' doesn't exist", fieldName));
    }

    private Map<String, FieldDefinitionInfo> retrieveFieldsDefinitions(Class<?> entityClass, String refEntityNames) {
        Field[] rootFields = entityClass.getDeclaredFields();
        if (rootFields.length > 0) {
            return Arrays.stream(rootFields).filter(f -> !f.isAnnotationPresent(NotSearchable.class))
                    .map(f -> new FieldDefinitionInfo(refEntityNames != null && !refEntityNames.isEmpty() ?
                            refEntityNames + "." + f.getName() : f.getName(), f.getType(), refEntityNames ))
                    .collect(Collectors.toMap(FieldDefinitionInfo::getFieldName, definitionInfo -> definitionInfo));
        }
        return Collections.emptyMap();
    }
}
