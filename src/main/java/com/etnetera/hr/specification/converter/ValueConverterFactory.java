package com.etnetera.hr.specification.converter;

/**
 * Generic interface of factory creating converters value one type to another type
 * @param <FROM> Type of source value
 */
public interface ValueConverterFactory<FROM> {
        /**
         * Create conveter
         * @param toType Destination class of value
         * @param <TO> Destication type of value
         * @return converter
         */
        <TO> ValueConverter<FROM,TO> getConverter(Class<TO> toType);
}
