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
public class Gauss {

    private DoublesToDoubleFunction mxparser = new MxParser(
        "(1/(s*sqrt(2*pi)))*exp((-(mp-m)^2)/(2*s^2))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction exp4j = new Exp4J(
        "(1/(s*sqrt(2*pi)))*exp((-(mp-m)^2)/(2*s^2))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction evalex = new EvalEX(
        "(1/(s*sqrt(2*pi)))*e^((-(mp-m)^2)/(2*s^2))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction parserng = new ParserNG(
        "(1/(s*sqrt(2*pi)))*exp((-(mp-m)^2)/(2*s^2))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction janino = new JaninoExpr(
        "(1/(s*Math.sqrt(2*Math.PI)))*Math.exp((-Math.pow(mp-m, 2))/(2*Math.pow(s,2)))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction janinoCompiled = new JaninoCompiled(
        "(1/(s*Math.sqrt(2*Math.PI)))*Math.exp((-Math.pow(mp-m, 2))/(2*Math.pow(s,2)))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction javacCompiled = new JavaCompiled(
        "(1/(s*Math.sqrt(2*Math.PI)))*Math.exp((-Math.pow(mp-m, 2))/(2*Math.pow(s,2)))",
        "s", "mp", "m"
    );

    private DoublesToDoubleFunction parsii = new Parsii(
        "(1/(s*sqrt(2*pi)))*exp((-(mp-m)^2)/(2*s^2))",
        "s", "mp", "m"
    );

    private Random random = new Random();

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double s() {
        return random.nextDouble();
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double mp() {
        return random.nextDouble();
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    private double m() {
        return random.nextDouble();
    }

    @Benchmark
    public double nativeMath() {
        var s = s();
        var mp = mp();
        var m = m();
        return (1 / (s * Math.sqrt(2 * Math.PI))) * Math.exp((-Math.pow(mp - m, 2)) / (2 * Math.pow(s, 2)));
    }

    @Benchmark
    public double mxparser() {
        return mxparser.apply(s(), mp(), m());
    }

    @Benchmark
    public double exp4j() {
        return exp4j.apply(s(), mp(), m());
    }

    @Benchmark
    public double parsii() {
        return parsii.apply(s(), mp(), m());
    }

    @Benchmark
    public double parserng() {
        return parserng.apply(s(), mp(), m());
    }

    @Benchmark
    public double janino() {
        return janino.apply(s(), mp(), m());
    }

    @Benchmark
    public double janinoCompiled() {
        return janinoCompiled.apply(s(), mp(), m());
    }

    @Benchmark
    public double javac() {
        return javacCompiled.apply(s(), mp(), m());
    }

    @Benchmark
    public double evalex() {
        return evalex.apply(s(), mp(), m());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(Gauss.class.getSimpleName())
            .warmupIterations(0)
            .measurementIterations(1)
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
