package com.etnetera.hr.specification.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Implementation of factory creating converter value of String type to other supported types
 */
public class StringValueConverterFactory implements ValueConverterFactory<String> {

    /** Map of supported converters */
    private final Map<Class<?>, ValueConverter<String, ?>> converters;
    /** Object mapper */
    private final ObjectMapper objectMapper;

    public StringValueConverterFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        converters = new HashMap<>();
        converters.put(Integer.class, (ValueConverter<String, Integer>) value -> objectMapper.readValue(value, Integer.class));
        converters.put(Long.class, (ValueConverter<String, Long>) value -> objectMapper.readValue(value, Long.class));
        converters.put(Short.class, (ValueConverter<String, Short>) value -> objectMapper.readValue(value, Short.class));
        converters.put(Byte.class, (ValueConverter<String, Short>) value -> objectMapper.readValue(value, Short.class));
        converters.put(Float.class, (ValueConverter<String, Float>) value -> objectMapper.readValue(value, Float.class));
        converters.put(Double.class, (ValueConverter<String, Double>) value -> objectMapper.readValue(value, Double.class));
        converters.put(Boolean.class, (ValueConverter<String, Boolean>) value -> objectMapper.readValue(value, Boolean.class));
        converters.put(Date.class, (ValueConverter<String, Date>) value -> objectMapper.readValue(value, Date.class));
        converters.put(LocalDate.class, (ValueConverter<String, LocalDate>) value -> objectMapper.readValue(value, LocalDate.class));
    }


    @Override
    @SuppressWarnings("unchecked")
    public <TO> ValueConverter<String, TO> getConverter(Class<TO> toType) {
        ValueConverter<String, ?> converter = converters.get(toType);
        if (converter != null) {
            return (ValueConverter<String, TO>) converter;
        }
        throw new NoSuchElementException(String.format("No converter for class", toType.getName()));
    }
}
