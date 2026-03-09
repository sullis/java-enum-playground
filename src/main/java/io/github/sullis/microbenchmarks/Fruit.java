package io.github.sullis.microbenchmarks;

public enum Fruit {
    APPLE("Apple"),
    APRICOT("Apricot"),
    BANANA("Banana"),
    BLUEBERRY("Blueberry"),
    CHERRY("Cherry"),
    GRAPE("Grape"),
    KIWI("Kiwi"),
    LEMON("Lemon"),
    LIME("Lime"),
    MANGO("Mango"),
    ORANGE("Orange"),
    PAPAYA("Papaya"),
    PEACH("Peach"),
    PEAR("Pear"),
    PINEAPPLE("Pineapple"),
    STRAWBERRY("Strawberry");

    /** Pre-cached copy of {@link #values()} to avoid repeated array allocation. */
    public static final Fruit[] STATIC_FRUIT_VALUES = values();

    private final String id;

    Fruit(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
