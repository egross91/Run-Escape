package org.escaperun.game.model.items;

public enum ItemSlot {
    HELMET(0),
    WEAPON(1),
    BOOTS(2),
    GLOVES(3),
    ARMOR(4);

    private final int value;

    ItemSlot(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
