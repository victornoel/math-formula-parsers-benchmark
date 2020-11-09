package com.github.victornoel.bench.math.libs;

import java.math.BigDecimal;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;
import com.udojava.evalex.Expression;

public final class EvalEX implements DoublesToDoubleFunction {

    private final String[] variables;
    private final Expression expression;

    public EvalEX(String expression, String... variables) {
        this.expression = new Expression(expression).setPrecision(4);
        this.variables = variables.clone();
    }

    @Override
    public double apply(double... value) {
        for (int i = 0; i < variables.length; i++) {
            expression.with(variables[i], new BigDecimal(value[i]));
        }
        return expression.eval().doubleValue();
    }
}
