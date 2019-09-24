package com.etnetera.hr.specification;

import com.etnetera.hr.data.dto.filter.BasicExpression;
import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.specification.converter.ValueConverterFactory;
import com.etnetera.hr.specification.field.FieldDefinitionInfo;
import com.etnetera.hr.specification.field.FieldsDefinition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Specification class for the JavaScriptFramework entity
 */
@Getter
@Setter
@SuppressWarnings("unchecked")
public class JScriptFrameworkSpecification extends AbstractDynamicSpecification<JavaScriptFramework> {

    private final FieldsDefinition fieldsDefinitions;

    public JScriptFrameworkSpecification(FilterDto filterDto, ValueConverterFactory<String> valueConverterFactory,
                                         FieldsDefinition fieldsDefinitions) {
        super(filterDto, valueConverterFactory);
        this.fieldsDefinitions = fieldsDefinitions;
    }

    @Override
    protected Predicate toPredicate(BasicExpression expr, CriteriaBuilder cb, Root<JavaScriptFramework> root) throws IOException, NoSuchFieldException {
        if(expr != null) {
            String fieldName = expr.getField();
            FieldDefinitionInfo fieldDefinitionInfo = fieldsDefinitions.getFieldDefinition(fieldName);
            Path<?> pathField = getPath(fieldDefinitionInfo, root);
            switch (expr.getOperation()) {
                case EQUALS: return toEqualPredicate(fieldDefinitionInfo, expr, pathField, cb);
                case LIKE: return toLikePredicate(fieldDefinitionInfo, expr, pathField, cb);
                case GT: return toGtPredicate(fieldDefinitionInfo, expr, pathField, cb);
                case GTE: return toGtePredicate(fieldDefinitionInfo, expr, pathField, cb);
                case LT: return toLtPredicate(fieldDefinitionInfo, expr, pathField, cb);
                case LTE: return toLtePredicate(fieldDefinitionInfo, expr, pathField, cb);
            }
        }
        return cb.and();
    }

    protected Path<?> getPath(FieldDefinitionInfo fieldDefinitionInfo, Root<JavaScriptFramework> root)  {
        String fieldName = fieldDefinitionInfo.getRefEntityNames() != null && !fieldDefinitionInfo.getRefEntityNames().isEmpty()
            ? fieldDefinitionInfo.getFieldName().substring(fieldDefinitionInfo.getRefEntityNames().length() + 1) : fieldDefinitionInfo.getFieldName();
        Path<?> path = root;

        if(fieldDefinitionInfo.getRefEntityNames() != null && !fieldDefinitionInfo.getRefEntityNames().isEmpty()) {
            String[] refEntityNames = fieldDefinitionInfo.getRefEntityNames().split("\\.");
            for (String refEntityName : refEntityNames) {
                path = path.get(refEntityName);
            }
        }

        if (Number.class.isAssignableFrom(fieldDefinitionInfo.getValueClass())) {
            return path.<Path<? extends Number>>get(fieldName);
        }
        if (LocalDate.class.isAssignableFrom(fieldDefinitionInfo.getValueClass())) {
            return path.<Path<LocalDate>>get(fieldName);
        }
        return path.get(fieldName);
    }

}
