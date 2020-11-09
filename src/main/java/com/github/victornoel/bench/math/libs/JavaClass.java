package com.github.victornoel.bench.math.libs;

import java.util.concurrent.atomic.AtomicLong;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

public final class JavaClass {

    private static final AtomicLong COMPILED_CLASS_INDEX = new AtomicLong();

    private final String expression;
    private final String[] variables;
    private final String packageName;
    private final String name;

    public JavaClass(String expression, String[] variables) {
        this.expression = expression;
        this.variables = variables.clone();
        this.packageName = getClass().getPackage().getName() + ".compiled";
        this.name = getClass().getSimpleName() + COMPILED_CLASS_INDEX.incrementAndGet();
    }

    public String name() {
        return this.name;
    }

    public String packageName() {
        return this.packageName;
    }

    public String source() {
        String[] varDecls = new String[variables.length];
        for (int i = 0; i < variables.length; i++) {
            varDecls[i] = String.format("final double %s = xs[%d];", variables[i], i);
        }
        return "package " + packageName + ";\n"
            + "import static java.lang.Math.*;\n"
            + "public final class " + name + " implements " + DoublesToDoubleFunction.class.getCanonicalName() + " {\n"
            + "  public double apply(final double... xs) {\n"
            + String.join("\n", varDecls) + "\n"
            + "    return (" + expression + ");\n"
            + "  }\n"
            + "}";
    }
}
