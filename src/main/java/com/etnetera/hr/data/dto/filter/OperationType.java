package com.etnetera.hr.data.dto.filter;

import java.util.Locale;

/**
 * Type of basic filter's expression
 */
public enum OperationType {
    LIKE, EQUALS, LT, LTE, GT, GTE;

    /**
     * Get basic expression's operation type
     * @param value string representation of operation type
     * @return type of basic filter's expression
     */
    public static OperationType fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception var2) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for filter operation given! Supported LIKE, " +
                    "LIKE, EQUALS, LT, LTE, GT, GTE", value));
        }
    }
}
