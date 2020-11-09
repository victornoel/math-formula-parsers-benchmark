package com.github.victornoel.bench.math.libs;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

public final class JaninoExpr implements DoublesToDoubleFunction {

    private final ExpressionEvaluator expression;

    public JaninoExpr(String expression, String... variables) {
        this.expression = new ExpressionEvaluator();
        Class<?>[] classes = new Class[variables.length];
        Arrays.fill(classes, double.class);
        this.expression.setParameters(variables, classes);
        this.expression.setExpressionType(double.class);
        try {
            this.expression.cook(expression);
        } catch (CompileException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double apply(double... value) {
        try {
            Object[] inputs = new Object[value.length];
            for (int i = 0; i < value.length; i++) {
                inputs[i] = value[i];
            }
            return (double) expression.evaluate(inputs);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
