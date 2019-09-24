package com.etnetera.hr.data.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Class holding basic filter expressions
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BasicExpression {
    /** name of entity's field */
    private String field;
    /**  type of operation */
    private OperationType operation;
    /** value for operation */
    private String value;
}
