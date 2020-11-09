package com.github.victornoel.bench.math.libs;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

import parser.Function;

public final class ParserNG implements DoublesToDoubleFunction {

    private final Function function;

    public ParserNG(String expression, String... variables) {
        this.function = new Function(
            String.format(
                "f(%s) = %s",
                String.join(",", variables),
                expression
            )
        );
    }

    @Override
    public double apply(double... value) {
        return function.calc(value);
    }
}
