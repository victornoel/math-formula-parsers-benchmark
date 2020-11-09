package com.github.victornoel.bench.math.libs;

import java.util.Arrays;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

public final class MxParser implements DoublesToDoubleFunction {

    private final Argument[] arguments;
    private final Expression expression;

    public MxParser(String expression, String... variables) {
        this.arguments = Arrays.stream(variables).map(v -> new Argument(v, 0)).toArray(Argument[]::new);
        this.expression = new Expression(expression, arguments);
    }

    @Override
    public double apply(double... value) {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i].setArgumentValue(value[i]);
        }
        return expression.calculate();
    }
}
