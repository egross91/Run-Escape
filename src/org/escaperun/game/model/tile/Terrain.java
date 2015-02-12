package org.escaperun.game.model.tile;

import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.view.Decal;

/**
 * Created by Matthew LoGalbo on 2/11/2015.
 */
public abstract class Terrain implements Collidable, Drawable {
    protected Decal[][] decal;
    protected boolean collidable;

    protected Terrain(Decal[][] decal, boolean collidable){
        this.decal = decal;
        this.collidable = collidable;
    }

    public Decal[][] getDecal(){return new Decal[][] {{}}; }

    public boolean isCollidable() {return collidable;}
}
