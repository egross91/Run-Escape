package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

public class Water extends Terrain {
    public Water(){
        super(new Decal((char) 247, Color.BLACK, new Color(0, 200, 255)), true);
    }
}
