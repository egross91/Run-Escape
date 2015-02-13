package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

public class Grass extends Terrain {
    public Grass() {
        super(new Decal('#', Color.BLACK, Color.GREEN), false);
    }
}
