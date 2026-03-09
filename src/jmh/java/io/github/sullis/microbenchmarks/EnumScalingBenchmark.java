package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Benchmarks the cost of {@code Enum.values()} versus a cached static array across enums of
 * three different cardinalities (4, 16, 32 constants), revealing how the allocation overhead
 * scales with enum size.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class EnumScalingBenchmark {

    // ---- 4-constant enum ----
    enum Size4 { A, B, C, D }
    private static final Size4[] SIZE4_CACHED = Size4.values();

    @Benchmark public void size4_dynamicValues(Blackhole bh) { bh.consume(Size4.values().length); }
    @Benchmark public void size4_cachedValues(Blackhole bh)  { bh.consume(SIZE4_CACHED.length); }

    // ---- 16-constant enum ----
    enum Size16 { A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P }
    private static final Size16[] SIZE16_CACHED = Size16.values();

    @Benchmark public void size16_dynamicValues(Blackhole bh) { bh.consume(Size16.values().length); }
    @Benchmark public void size16_cachedValues(Blackhole bh)  { bh.consume(SIZE16_CACHED.length); }

    // ---- 32-constant enum ----
    enum Size32 {
        A,  B,  C,  D,  E,  F,  G,  H,
        I,  J,  K,  L,  M,  N,  O,  P,
        Q,  R,  S,  T,  U,  V,  W,  X,
        Y,  Z,  AA, BB, CC, DD, EE, FF
    }
    private static final Size32[] SIZE32_CACHED = Size32.values();

    @Benchmark public void size32_dynamicValues(Blackhole bh) { bh.consume(Size32.values().length); }
    @Benchmark public void size32_cachedValues(Blackhole bh)  { bh.consume(SIZE32_CACHED.length); }
}
