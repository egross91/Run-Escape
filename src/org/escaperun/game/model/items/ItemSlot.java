package org.escaperun.game.model.items;

import org.escaperun.game.view.Decal;

import java.awt.*;

public enum ItemSlot {
    HELMET(0, new Decal('H', Color.BLACK, Color.WHITE)),
    WEAPON(1, new Decal('W', Color.BLACK, Color.WHITE)),
    BOOTS(2, new Decal('B', Color.BLACK, Color.WHITE)),
    GLOVES(3, new Decal('G', Color.BLACK, Color.WHITE)),
    ARMOR(4, new Decal('A', Color.BLACK, Color.WHITE));

    private final int value;
    private final Decal decal;

    ItemSlot(int v, Decal decal) {
        this.value = v;
        this.decal = decal;
    }

    public int getValue() {
        return value;
    }

    public Decal getDecal(){
        return decal;
    }
}
