package org.escaperun.game.model.tile;

import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.view.Decal;

public abstract class Terrain implements Collidable, Drawable {

    protected final Decal decal;
    protected boolean collidable;

    protected Terrain(Decal decal, boolean collidable){
        this.decal = decal;
        this.collidable = collidable;
    }

    public Decal getDecal() {
        return this.decal;
    }

    public boolean isCollidable() {
        return collidable;
    }
}
