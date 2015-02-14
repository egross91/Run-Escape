package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

public class Mountain extends Terrain{

    public Mountain(){
        super(new Decal('M', Color.BLACK, Color.LIGHT_GRAY), true);
    }
}
