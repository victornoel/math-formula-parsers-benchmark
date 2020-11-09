# math-formula-parsers-benchmark

Some benchmarks about Java-based math formula parsers

## Preliminary results

### `x / (1 - y)`

| Benchmark              |  Mode  | Cnt  |        Score   | Error  | Units |
| ---------------------- | ------ | ----:| --------------:| ------ | ----- |
| Basic.evalex           | thrpt  |   2  |   647356,001   |        | ops/s |
| Basic.exp4j            | thrpt  |   2  |  7228381,746   |        | ops/s |
| Basic.janino           | thrpt  |   2  | 18659431,677   |        | ops/s |
| Basic.janinoCompiled   | thrpt  |   2  | 25447816,144   |        | ops/s |
| Basic.javac            | thrpt  |   2  | 25450234,430   |        | ops/s |
| Basic.mxparser         | thrpt  |   2  |   330771,480   |        | ops/s |
| Basic.nativeMath       | thrpt  |   2  | 25581230,576   |        | ops/s |
| Basic.parserng         | thrpt  |   2  |   117605,587   |        | ops/s |
| Basic.parsii           | thrpt  |   2  | 24747297,648   |        | ops/s |

### `q * exp(-(A/(1-((p-C)/p))))`

| Benchmark              |  Mode  | Cnt  |        Score   | Error  | Units |
| ---------------------- | ------ | ----:| --------------:| ------ | ----- |
| Simple.evalex          | thrpt  |   2  |   231115,831   |        | ops/s |
| Simple.exp4j           | thrpt  |   2  |  3063390,670   |        | ops/s |
| Simple.janino          | thrpt  |   2  |  8441898,920   |        | ops/s |
| Simple.janinoCompiled  | thrpt  |   2  | 10258686,665   |        | ops/s |
| Simple.javac           | thrpt  |   2  | 10257949,017   |        | ops/s |
| Simple.mxparser        | thrpt  |   2  |     2527,348   |        | ops/s |
| Simple.nativeMath      | thrpt  |   2  | 10268847,961   |        | ops/s |
| Simple.parserng        | thrpt  |   2  |    45929,844   |        | ops/s |
| Simple.parsii          | thrpt  |   2  |  7840311,389   |        | ops/s |

### `(1/(s*sqrt(2*pi)))*exp((-(mp-m)^2)/(2*s^2))`

Note: EvalEx failed on the next one…

| Benchmark              |  Mode  | Cnt  |        Score   | Error  | Units |
| ---------------------- | ------ | ----:| --------------:| ------ | ----- |
| Gauss.exp4j            | thrpt  |   2  |  2839587,106   |        | ops/s |
| Gauss.janino           | thrpt  |   2  | 10537378,895   |        | ops/s |
| Gauss.janinoCompiled   | thrpt  |   2  | 13440744,947   |        | ops/s |
| Gauss.mxparser         | thrpt  |   2  |    55094,967   |        | ops/s |
| Gauss.nativeMath       | thrpt  |   2  | 13395646,833   |        | ops/s |
| Gauss.openhft          | thrpt  |   2  | 13442079,648   |        | ops/s |
| Gauss.parserng         | thrpt  |   2  |     7393,070   |        | ops/s |
| Gauss.parsii           | thrpt  |   2  |  6857224,088   |        | ops/s |
