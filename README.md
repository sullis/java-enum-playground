# java-enum-playground

A collection of [JMH](https://github.com/openjdk/jmh) microbenchmarks exploring Java enum performance characteristics across multiple JVM versions (8, 11, 17, 21).

## Benchmarks

| Class | What it measures |
|---|---|
| `EnumValuesBenchmark` | `Enum.values()` (allocates a new array) vs a cached static copy |
| `EnumScalingBenchmark` | Same comparison at enum cardinalities of 4, 16, and 32 constants |
| `EnumLookupBenchmark` | `Enum.valueOf()` across different ordinal positions, iteration, `ordinal()`, `name()` |
| `EnumCollectionsBenchmark` | `EnumSet` vs `HashSet` and `EnumMap` vs `HashMap` for contains/get/iterate |

## Running benchmarks

```bash
# Build the benchmark JAR
./gradlew jmhJar

# Run all benchmarks
./gradlew jmh

# Run a single benchmark class
./gradlew jmh -Dbenchmark=EnumValuesBenchmark

# Run the unit tests
./gradlew test
```

JSON results are written to `build/jmh-results.json` after each run.

## CI / multi-JDK matrix

The [benchmark workflow](.github/workflows/benchmark.yml) can be triggered manually from GitHub Actions. It discovers all `*Benchmark` classes automatically and runs each one on Java 8, 11, 17, and 21 in parallel.

## Key takeaways

- **Cache `Enum.values()`** — every call allocates a defensive array copy; store it in a `static final` field.
- **Prefer `EnumSet` / `EnumMap`** — bit-vector and ordinal-array implementations are significantly faster than `HashSet` / `HashMap` for enum keys.
- **`Enum.valueOf()` is O(n)** — it scans the name table linearly; avoid calling it in hot paths.
