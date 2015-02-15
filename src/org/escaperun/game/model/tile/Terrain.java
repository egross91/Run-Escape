package org.escaperun.game.model.tile;

import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Terrain implements Collidable, Drawable, Savable {

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

    @Override
    public Element save(Document dom) {
        Element terrainElement = dom.createElement("Terrain");

        return terrainElement;
    }
}
