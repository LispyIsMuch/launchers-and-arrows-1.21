package net.stln.launchersandarrows.util;

public enum AttributeEnum {
    FLAME(0),
    FROST(1),
    LIGHTNING(2),
    ACID(3),
    FLOOD(4),
    ECHO(5),
    INJURY(6),
    ;

    private final int id;

    private AttributeEnum(int i) {
        this.id = i;
    }

    public int get() {
        return this.id;
    }
}
