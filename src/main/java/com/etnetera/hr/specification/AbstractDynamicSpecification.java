package com.etnetera.hr.specification;

import com.etnetera.hr.data.dto.filter.BasicExpression;
import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.data.dto.filter.GroupExpression;
import com.etnetera.hr.data.dto.filter.LogicalOperator;
import com.etnetera.hr.specification.converter.ValueConverter;
import com.etnetera.hr.specification.converter.ValueConverterFactory;
import com.etnetera.hr.specification.field.FieldDefinitionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Abstract class common methods for creating Specification object using filter DTO
 * @param <T> Type of entity
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public abstract class AbstractDynamicSpecification<T> implements Specification<T> {

    private final FilterDto filterDto;
    private final ValueConverterFactory<String> converterFactory;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        AtomicReference<Exception> exception = new AtomicReference<>();
        if (filterDto != null && filterDto.getExpressions() != null && !filterDto.getExpressions().isEmpty()) {
            List<Predicate> predicates = filterDto.getExpressions().stream().map(expr -> {
                try {
                    return toPredicate(expr, filterDto.getOperator(), cb, root);
                } catch (Exception exc) {
                    exception.set(exc);
                }
                return null;

            }).filter(Objects::nonNull).collect(Collectors.toList());

            if(exception.get() != null) {
                throw new RuntimeException(exception.get());
            }
            Predicate resultPredicate = createPredicate(predicates.toArray(new Predicate[predicates.size()]), filterDto.getOperator(), cb);
            return resultPredicate;
        }
        return cb.and();
    }

    private Predicate toPredicate(GroupExpression expr, LogicalOperator logicalOperator, CriteriaBuilder cb, Root<T> root) throws Exception {
        if (expr != null) {
            return toPredicate(expr.getExpressions(), logicalOperator, cb, root);
        }
        return cb.and();
    }

    private Predicate toPredicate(Collection<BasicExpression> expressions, LogicalOperator logicalOperator,
                                  CriteriaBuilder cb, Root<T> root) throws Exception {
        AtomicReference<Exception> exception = new AtomicReference<>();
        if(expressions != null && !expressions.isEmpty()) {
            List<Predicate> predicates = expressions.stream().map(expr -> {
                try {
                    return toPredicate(expr, cb, root);
                } catch (Exception exc) {
                    exception.set(exc);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            if (exception.get() != null) {
               throw exception.get();
            }
            return createPredicate(predicates.toArray(new Predicate[predicates.size()]), logicalOperator, cb);
        }
        return cb.and();
    }

    protected abstract Predicate toPredicate(BasicExpression expr, CriteriaBuilder cb, Root<T> root) throws IOException, NoSuchFieldException;

    private Predicate createPredicate(Predicate[] predicates, LogicalOperator logicalOperator, CriteriaBuilder cb) {
        if (predicates != null && predicates.length > 0) {
            switch (logicalOperator) {
                case AND: return cb.and(predicates);
                case OR: return cb.or(predicates);
                default: throw new IllegalArgumentException(String.format("logical operator '%s' is not supported",
                        logicalOperator.name()));
            }
        }
        return cb.and();
    }

    protected Predicate toEqualPredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField, CriteriaBuilder cb) throws IOException {
        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if( Number.class.isAssignableFrom(fieldClass) ) {
            Class<? extends Number> numberFieldClass = (Class<? extends Number>)fieldClass;
            return cb.equal(pathField, castToNumber(expr.getValue(), numberFieldClass));
        }
        if( LocalDate.class.isAssignableFrom(fieldClass) ) {
            Class<LocalDate> localDateFieldClass = (Class<LocalDate>)fieldClass;
            return cb.equal((Path<LocalDate>)pathField, castToLocalDate(expr.getValue(), localDateFieldClass));
        }
        if (String.class.isAssignableFrom(fieldClass)) {
            return cb.equal(pathField, expr.getValue());
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    protected Predicate toLikePredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField,
                                      CriteriaBuilder cb) {
        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if (String.class.isAssignableFrom(fieldClass)) {
            Character escapeChar = getEscapeChar( expr.getValue() );
            String searchStr = replaceByEscapeChar( expr.getValue(), escapeChar );
            return cb.like(
                    (Path<String>)pathField, "%" + searchStr + "%", escapeChar);
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    protected Predicate toGtPredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField,
                                    CriteriaBuilder cb) throws IOException {
        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if( Number.class.isAssignableFrom(fieldClass) ) {
            Class<? extends Number> numberFieldClass = (Class<? extends Number>)fieldClass;
            return cb.gt((Path<? extends Number>) pathField, castToNumber(expr.getValue(), numberFieldClass));
        }
        if( LocalDate.class.isAssignableFrom(fieldClass) ) {
            Class<LocalDate> localDateFieldClass = (Class<LocalDate>)fieldClass;
            return cb.greaterThan((Path<LocalDate>) pathField, castToLocalDate(expr.getValue(), localDateFieldClass));
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    protected Predicate toGtePredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField,
                                     CriteriaBuilder cb) throws IOException {
        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if( Number.class.isAssignableFrom(fieldClass) ) {
            Class<? extends Number> numberFieldClass = (Class<? extends Number>)fieldClass;
            return cb.ge((Path<? extends Number>) pathField, castToNumber(expr.getValue(), numberFieldClass));
        }
        if( LocalDate.class.isAssignableFrom(fieldClass) ) {
            Class<LocalDate> localDateFieldClass = (Class<LocalDate>)fieldClass;
            return cb.greaterThanOrEqualTo((Path<LocalDate>) pathField, castToLocalDate(expr.getValue(), localDateFieldClass));
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    protected Predicate toLtPredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField,
                                    CriteriaBuilder cb) throws IOException {

        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if( Number.class.isAssignableFrom(fieldClass) ) {
            Class<? extends Number> numberFieldClass = (Class<? extends Number>)fieldClass;
            return cb.lt((Path<? extends Number>) pathField, castToNumber(expr.getValue(), numberFieldClass));
        }
        if( LocalDate.class.isAssignableFrom(fieldClass) ) {
            Class<LocalDate> localDateFieldClass = (Class<LocalDate>)fieldClass;
            return cb.lessThan((Path<LocalDate>) pathField, castToLocalDate(expr.getValue(), localDateFieldClass));
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    protected Predicate toLtePredicate(FieldDefinitionInfo fieldDefinitionInfo, BasicExpression expr, Path<?> pathField,
                                     CriteriaBuilder cb) throws IOException {
        Class<?> fieldClass = fieldDefinitionInfo.getValueClass();
        if( Number.class.isAssignableFrom(fieldClass) ) {
            Class<? extends Number> numberFieldClass = (Class<? extends Number>)fieldClass;
            return cb.le((Path<? extends Number>) pathField, castToNumber(expr.getValue(), numberFieldClass));
        }
        if( LocalDate.class.isAssignableFrom(fieldClass) ) {
            Class<LocalDate> localDateFieldClass = (Class<LocalDate>)fieldClass;
            return cb.lessThanOrEqualTo((Path<LocalDate>) pathField, castToLocalDate(expr.getValue(), localDateFieldClass));
        }
        throw new UnsupportedOperationException(String.format("Operation '%s' is not supported for field='%s' class='%s'",
                expr.getOperation().name(), fieldDefinitionInfo.getFieldName(), fieldClass.getSimpleName()));
    }

    private final static char[] specialSymbols = {'#', '&', '\\', '!' };

    private static Character getEscapeChar(String value) {
        for (Character ch : specialSymbols)
            if( !value.contains(ch.toString()) )
                return ch;
        return '#';
    }

    private static String replaceByEscapeChar(String value, Character escapeChar) {
        return value.replaceAll("([%_])", escapeChar.toString() + "$1");
    }

    private Number castToNumber(String value, Class<? extends Number> valueClass) throws IOException {
        ValueConverter<String, ? extends Number> converter = converterFactory.getConverter(valueClass);
        return valueClass.cast(converter.convert(value));
    }

    private LocalDate castToLocalDate(String value, Class<LocalDate> valueClass) throws IOException {
        ValueConverter<String, LocalDate> converter = converterFactory.getConverter(valueClass);
        return valueClass.cast(converter.convert(value));
    }
}
