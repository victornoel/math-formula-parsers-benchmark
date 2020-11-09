package com.github.victornoel.bench.math;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.github.victornoel.bench.math.libs.EvalEX;
import com.github.victornoel.bench.math.libs.Exp4J;
import com.github.victornoel.bench.math.libs.JaninoCompiled;
import com.github.victornoel.bench.math.libs.JaninoExpr;
import com.github.victornoel.bench.math.libs.JavaCompiled;
import com.github.victornoel.bench.math.libs.MxParser;
import com.github.victornoel.bench.math.libs.ParserNG;
import com.github.victornoel.bench.math.libs.Parsii;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
public class Simple {

    private DoublesToDoubleFunction mxparser = new MxParser(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction exp4j = new Exp4J(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction evalex = new EvalEX(
        "q * e^(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction parserng = new ParserNG(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction janino = new JaninoExpr(
        "q * java.lang.Math.exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction janinoCompiled = new JaninoCompiled(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction javacCompiled = new JavaCompiled(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private DoublesToDoubleFunction parsii = new Parsii(
        "q * exp(-(A/(1-((p-C)/p))))",
        "q", "A", "p", "C"
    );

    private Random random = new Random();

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double q() {
        return random.nextDouble() * 100;
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double A() {
        return random.nextDouble();
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double p() {
        return 1 + random.nextDouble() * 100;
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double C() {
        return 1 + random.nextDouble() * 50;
    }

    @Benchmark
    public double nativeMath() {
        var q = q();
        var A = A();
        var p = p();
        var C = C();
        return q * Math.exp(-(A / (1 - ((p - C) / p))));
    }

    @Benchmark
    public double mxparser() {
        return mxparser.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double exp4j() {
        return exp4j.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double parsii() {
        return parsii.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double parserng() {
        return parserng.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double janino() {
        return janino.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double janinoCompiled() {
        return janinoCompiled.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double javac() {
        return javacCompiled.apply(q(), A(), p(), C());
    }

    @Benchmark
    public double evalex() {
        return evalex.apply(q(), A(), p(), C());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(Simple.class.getSimpleName())
            .warmupIterations(0)
            .measurementIterations(1)
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
