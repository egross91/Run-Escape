package org.escaperun.game.model.tile;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

/**
 * Created by abcronin on 2/11/2015.
 */
public abstract class AreaEffect implements Activatable, Drawable {
    private Decal[][] decal;

    public AreaEffect(){
        this.decal = null;
    }

    public AreaEffect(Decal[][] decal){
        this.decal = decal;
    }

    public abstract void areaEffect();
    public abstract void onTouch(Entity e);
    public boolean isActivatable() { return true; }
    public Decal[][] getDecal(){return decal;}

}
