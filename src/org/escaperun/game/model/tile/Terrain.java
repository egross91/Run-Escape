package org.escaperun.game.model.tile;

import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;

/**
 * Created by Matthew LoGalbo on 2/11/2015.
 */
public abstract class Terrain implements Collidable, Drawable {
    protected int decalID;
    protected boolean collidable;

    public int[][] getDecal(){return new int[][] {{decalID}}; }

    public boolean isCollidable() {return collidable;}
}
