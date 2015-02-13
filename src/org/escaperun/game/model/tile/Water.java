package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Matthew LoGalbo on 2/11/2015.
 */
public class Water extends Terrain {
    public Water(){
        super(new Decal('W', Color.lightGray, Color.BLUE), true); //TODO: Unicode needs testing. Most likely too large a unicode. Also check color.
    }
}
