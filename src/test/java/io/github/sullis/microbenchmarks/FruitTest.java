package io.github.sullis.microbenchmarks;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class FruitTest {

    @Test
    void valuesReturnsAllConstants() {
        assertEquals(16, Fruit.values().length);
    }

    @Test
    void staticCacheMatchesDynamicValues() {
        assertArrayEquals(Fruit.values(), Fruit.STATIC_FRUIT_VALUES);
    }

    @Test
    void staticCacheIsNotSameReferenceAsDynamicValues() {
        // values() must return a fresh array every time — the cache is a separate copy
        assertNotSame(Fruit.values(), Fruit.STATIC_FRUIT_VALUES);
    }

    @Test
    void valueOfReturnsByName() {
        assertEquals(Fruit.APPLE,      Fruit.valueOf("APPLE"));
        assertEquals(Fruit.STRAWBERRY, Fruit.valueOf("STRAWBERRY"));
        assertEquals(Fruit.MANGO,      Fruit.valueOf("MANGO"));
    }

    @Test
    void valueOfThrowsForUnknownName() {
        assertThrows(IllegalArgumentException.class, () -> Fruit.valueOf("DURIAN"));
    }

    @Test
    void ordinalsAreStable() {
        assertEquals(0,  Fruit.APPLE.ordinal());
        assertEquals(15, Fruit.STRAWBERRY.ordinal());
    }

    @Test
    void getIdReturnsDisplayName() {
        assertEquals("Apple",      Fruit.APPLE.getId());
        assertEquals("Strawberry", Fruit.STRAWBERRY.getId());
        assertEquals("Mango",      Fruit.MANGO.getId());
    }

    @Test
    void enumSetContainsAll() {
        EnumSet<Fruit> all = EnumSet.allOf(Fruit.class);
        assertEquals(Fruit.values().length, all.size());
        for (Fruit f : Fruit.values()) {
            assertTrue(all.contains(f));
        }
    }
}
