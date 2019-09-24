package com.etnetera.hr.data.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Filter DTO class. It is needed for broking relations between DAO implementation and our program.
 */
@AllArgsConstructor
@Getter
public class FilterDto implements Serializable {
    /** Type of logical operation */
    @NotNull
    private final LogicalOperator operator;

    /** Collection of grouped expressions */
    @NotNull
    private final Collection<GroupExpression> expressions;

    public FilterDto() {
        operator = LogicalOperator.AND;
        expressions = new ArrayList<>(0);
    }
}
