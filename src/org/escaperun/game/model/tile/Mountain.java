package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Matthew LoGalbo on 2/11/2015.
 */
public class Mountain extends Terrain{

    public Mountain(){
        super(new Decal[][] {{new Decal('\u26F0', Color.lightGray, Color.DARK_GRAY)}}, true);   //TODO: check mountain color and unicode is correct.
    }
}
