package com.github.victornoel.bench.math.libs;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.util.ResourceFinderClassLoader;
import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.StringResource;

import com.github.victornoel.bench.math.DoublesToDoubleFunction;

public final class JaninoCompiled implements DoublesToDoubleFunction {

    private final DoublesToDoubleFunction function;

    public JaninoCompiled(String expression, String... variables) {
        try {
            var compiler = CompilerFactoryFactory.getDefaultCompilerFactory().newCompiler();

            // Store generated .class files in a Map:
            Map<String, byte[]> classes = new HashMap<>();
            compiler.setClassFileCreator(new MapResourceCreator(classes));

            var clazz = new JavaClass(expression, variables);

            // Now compile two units from strings:
            compiler.compile(
                new Resource[] {
                    new StringResource(
                        clazz.name() + ".java",
                        clazz.source()
                    )
                }
            );

            // Set up a class loader that uses the generated classes.
            ClassLoader cl = new ResourceFinderClassLoader(
                new MapResourceFinder(classes), // resourceFinder
                ClassLoader.getSystemClassLoader() // parent
            );

            this.function = (DoublesToDoubleFunction) cl.loadClass(clazz.packageName() + "." + clazz.name())
                .getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double apply(double... value) {
        return function.apply(value);
    }
}
