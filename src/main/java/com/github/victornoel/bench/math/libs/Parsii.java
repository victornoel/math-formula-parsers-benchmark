package com.github.victornoel.bench.math.libs;

import java.util.Arrays;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.eval.Variable;
import parsii.tokenizer.ParseException;

public final class Parsii implements DoublesToDoubleFunction {

    private final Variable[] variables;
    private final Expression expression;

    public Parsii(String expression, String... variables) {
        Scope scope = new Scope();
        this.variables = Arrays.stream(variables).map(v -> scope.create(v)).toArray(Variable[]::new);
        try {
            this.expression = Parser.parse(expression, scope);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double apply(double... value) {
        for (int i = 0; i < variables.length; i++) {
            variables[i].setValue(value[i]);
        }
        return expression.evaluate();
    }
}
