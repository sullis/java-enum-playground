package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Benchmarks common enum lookup operations:
 * <ul>
 *   <li>{@code Enum.valueOf()} – linear scan of the enum's name table</li>
 *   <li>Iterating over {@code values()}</li>
 *   <li>{@code ordinal()} and {@code name()} accessors</li>
 * </ul>
 *
 * <p>The {@code @Param} on {@code fruitName} drives {@code enumValueOf} across all four ordinal
 * positions so results reveal whether lookup cost varies by position.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class EnumLookupBenchmark {

    @Param({"APPLE", "BANANA", "MANGO", "STRAWBERRY"})
    public String fruitName;

    @Benchmark
    public void enumValueOf(Blackhole bh) {
        bh.consume(Fruit.valueOf(fruitName));
    }

    @Benchmark
    public void enumValuesAndIterate(Blackhole bh) {
        for (Fruit f : Fruit.values()) {
            bh.consume(f);
        }
    }

    @Benchmark
    public void enumOrdinal(Blackhole bh) {
        bh.consume(Fruit.APPLE.ordinal());
    }

    @Benchmark
    public void enumName(Blackhole bh) {
        bh.consume(Fruit.APPLE.name());
    }
}
