package org.escaperun.game.model.tile;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

public abstract class AreaEffect implements Activatable, Drawable {

    protected Decal decal;

    public AreaEffect(Decal decal){
        this.decal = decal;
    }
    public AreaEffect(Decal decal, int value) {
        this.decal = decal;
        this.valueofchange = value;
    }

    protected int valueofchange;
    public abstract void areaEffect();
    public abstract void onTouch(Entity e);

    public boolean isActivatable(Entity e) {
        return true;
    }

    public Decal getDecal() {
        return decal;
    }

}
