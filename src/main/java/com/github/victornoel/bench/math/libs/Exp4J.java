package com.github.victornoel.bench.math.libs;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public final class Exp4J implements DoublesToDoubleFunction {

    private final String[] variables;
    private final Expression expression;

    public Exp4J(String expression, String... variables) {
        this.expression = new ExpressionBuilder(expression)
            .variables(variables)
            .build();
        this.variables = variables.clone();
    }

    @Override
    public double apply(double... value) {
        for (int i = 0; i < variables.length; i++) {
            expression.setVariable(variables[i], value[i]);
        }
        return expression.evaluate();
    }
}
