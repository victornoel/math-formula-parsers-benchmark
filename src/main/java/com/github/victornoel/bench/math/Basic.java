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
public class Basic {

    private DoublesToDoubleFunction mxparser = new MxParser(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction exp4j = new Exp4J(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction evalex = new EvalEX(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction parserng = new ParserNG(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction janino = new JaninoExpr(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction janinoCompiled = new JaninoCompiled(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction javacCompiled = new JavaCompiled(
        "x / (1 - y)",
        "x", "y"
    );

    private DoublesToDoubleFunction parsii = new Parsii(
        "x / (1 - y)",
        "x", "y"
    );

    private Random random = new Random();

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double x() {
        return random.nextDouble() * 100;
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double y() {
        return Math.min(0.999, random.nextDouble() - 0.001);
    }

    @Benchmark
    public double nativeMath() {
        var x = x();
        var y = y();
        return x / (1.0 - y);
    }

    @Benchmark
    public double mxparser() {
        return mxparser.apply(x(), y());
    }

    @Benchmark
    public double exp4j() {
        return exp4j.apply(x(), y());
    }

    @Benchmark
    public double parsii() {
        return parsii.apply(x(), y());
    }

    @Benchmark
    public double parserng() {
        return parserng.apply(x(), y());
    }

    @Benchmark
    public double janino() {
        return janino.apply(x(), y());
    }

    @Benchmark
    public double janinoCompiled() {
        return janinoCompiled.apply(x(), y());
    }

    @Benchmark
    public double javac() {
        return javacCompiled.apply(x(), y());
    }

    @Benchmark
    public double evalex() {
        return evalex.apply(x(), y());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(Basic.class.getSimpleName())
            .warmupIterations(0)
            .measurementIterations(1)
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
