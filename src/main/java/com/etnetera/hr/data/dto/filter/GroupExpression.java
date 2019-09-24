package com.etnetera.hr.data.dto.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class GroupExpression {
    @NotNull
    private final LogicalOperator operator;
    @NotNull
    private final Collection<BasicExpression> expressions;

    public GroupExpression() {
        this.operator = LogicalOperator.AND;
        this.expressions = new ArrayList<>(0);

    }
}
