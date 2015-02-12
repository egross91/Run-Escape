package org.escaperun.game.model.items;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Eric on 2/11/2015.
 */
public abstract class Item implements Activatable, Collidable {
    private Statistics stats;
    private final Decal[][] decal;

    public Item() {
        this.stats = null;
        this.decal = null;
    }

    public Item(Decal[][] d, Statistics stats) {
        this.stats = stats;
        this.decal = d;
    }

    public Statistics getStats() {
        return this.stats;
    }

    public Decal[][] getDecal() {
        return this.decal;
    }

    public boolean isCollidable() {
        return false;
    }

    public abstract void onTouch(Entity e);

    public abstract boolean isActivatable();
}
