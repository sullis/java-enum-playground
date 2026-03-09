
package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Compares the cost of calling {@code Enum.values()} on every invocation (which allocates a new
 * array each time) against accessing a pre-cached static copy of the same array.
 *
 * <p>The JIT can sometimes eliminate the allocation, but in practice — especially across
 * different JVM versions — caching the result is a safe micro-optimisation.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
@Threads(1)
public class EnumValuesBenchmark {

    @Benchmark
    public void enumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.values().length);
    }

    @Benchmark
    public void staticEnumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.STATIC_FRUIT_VALUES.length);
    }
}