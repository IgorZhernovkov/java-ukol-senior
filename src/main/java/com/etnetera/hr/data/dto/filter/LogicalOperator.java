package com.etnetera.hr.data.dto.filter;

import java.util.Locale;

/**
 * Type of filter's logical operations
 */
public enum LogicalOperator {
    AND, OR;

    /**
     * get type of logical operation by it's string name
     * @param value string representation of logical operation
     * @return type of logical operations
     */
    public LogicalOperator fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception var2) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for LogicalOperator given! " +
                    "Allowed values are %s, %s", "AND", "OR"));
        }
    }
}