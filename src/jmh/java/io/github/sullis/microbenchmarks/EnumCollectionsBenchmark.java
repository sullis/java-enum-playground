package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Compares the performance of enum-specialised collections ({@link EnumSet}, {@link EnumMap})
 * against their general-purpose equivalents ({@link HashSet}, {@link HashMap}).
 *
 * <p>Because {@code EnumSet} uses a bit-vector and {@code EnumMap} uses a plain array indexed
 * by {@link Enum#ordinal()}, both are expected to outperform the hash-based alternatives for
 * {@code contains} and {@code get} operations.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class EnumCollectionsBenchmark {

    private EnumSet<Fruit> enumSet;
    private Set<Fruit> hashSet;
    private EnumMap<Fruit, String> enumMap;
    private Map<Fruit, String> hashMap;

    @Setup
    public void setup() {
        enumSet = EnumSet.allOf(Fruit.class);
        hashSet = new HashSet<>(Arrays.asList(Fruit.values()));

        enumMap = new EnumMap<>(Fruit.class);
        hashMap = new HashMap<>();
        for (Fruit f : Fruit.values()) {
            enumMap.put(f, f.getId());
            hashMap.put(f, f.getId());
        }
    }

    @Benchmark
    public void enumSetContains(Blackhole bh) {
        bh.consume(enumSet.contains(Fruit.MANGO));
    }

    @Benchmark
    public void hashSetContains(Blackhole bh) {
        bh.consume(hashSet.contains(Fruit.MANGO));
    }

    @Benchmark
    public void enumMapGet(Blackhole bh) {
        bh.consume(enumMap.get(Fruit.MANGO));
    }

    @Benchmark
    public void hashMapGet(Blackhole bh) {
        bh.consume(hashMap.get(Fruit.MANGO));
    }

    @Benchmark
    public void enumSetIterate(Blackhole bh) {
        for (Fruit f : enumSet) {
            bh.consume(f);
        }
    }

    @Benchmark
    public void hashSetIterate(Blackhole bh) {
        for (Fruit f : hashSet) {
            bh.consume(f);
        }
    }
}
