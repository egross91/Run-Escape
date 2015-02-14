package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;
import java.util.Random;

public class Grass extends Terrain {

    public static final Random RANDOM = new Random();

    public Grass() {
        super(new Decal('#', Color.BLACK, RANDOM_GREEN()), false);
    }

    public static final Color RANDOM_GREEN() {
        int next = RANDOM.nextInt(5);
        if (next == 0) {
            return Color.GREEN;
        } else if (next == 1) {
            return Color.GREEN.darker();
        } else if (next == 2) {
            return Color.GREEN.darker().darker();
        } else if (next == 3) {
            return Color.GREEN.brighter();
        } else if (next == 4) {
            return Color.GREEN.brighter().brighter();
        }
        return null;
    }
}