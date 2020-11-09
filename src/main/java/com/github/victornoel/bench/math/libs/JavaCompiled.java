package com.github.victornoel.bench.math.libs;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

import net.openhft.compiler.CompilerUtils;

public final class JavaCompiled implements DoublesToDoubleFunction {

    private final DoublesToDoubleFunction function;

    @SuppressWarnings("unchecked")
    public JavaCompiled(String expression, String... variables) {
        try {
            var clazz = new JavaClass(expression, variables);
            this.function = (DoublesToDoubleFunction) CompilerUtils.CACHED_COMPILER.loadFromJava(
                clazz.packageName() + "." + clazz.name(),
                clazz.source()
            ).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double apply(double... value) {
        return function.apply(value);
    }
}
