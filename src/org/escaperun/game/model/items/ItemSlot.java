package org.escaperun.game.model.items;

import org.escaperun.game.view.Decal;

import java.awt.*;

public enum ItemSlot {
    HELMET(0),
    WEAPON(1),
    BOOTS(2),
    GLOVES(3),
    ARMOR(4);

    private final int value;
    private final Decal decal;

    ItemSlot(int v) {
        this.value = v;
        this.decal = setDecal();
    }

    public int getValue() {
        return value;
    }

    private Decal setDecal() {
        switch (value) {
            case(0):
                return new Decal('H', Color.BLACK, Color.WHITE);
            case(1):
                return new Decal('W', Color.BLACK, Color.WHITE);
            case(2):
                return new Decal('B', Color.BLACK, Color.WHITE);
            case(3):
                return new Decal('G', Color.BLACK, Color.WHITE);
            case(4):
                return new Decal('A', Color.BLACK, Color.WHITE);
            default:
                return null;
        }
    }

    public int getItemSlot() {
        return value;
    }

    public Decal getDecal(){
        return decal;
    }
}
