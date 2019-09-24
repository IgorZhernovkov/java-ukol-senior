package com.etnetera.hr.specification.converter;

import java.io.IOException;

/**
 * Generic interface of  conveter from one type to another type
 * @param <FROM> Type of source type
 * @param <TO> Type of destination type
 */
public interface ValueConverter<FROM, TO> {
    /**
     * Convert value
     * @param value source value
     * @return converted value
     * @throws IOException
     */
    TO convert(FROM value) throws IOException;
}
