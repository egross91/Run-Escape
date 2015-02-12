package org.escaperun.game.model.items;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;

/**
 * Created by Eric on 2/11/2015.
 */
public abstract class Item implements Drawable, Activatable, Collidable {
    private final int[][] decal;
    private Statistics stats;

    public Item() {
        this.decal = null;
        this.stats = null;
    }

    public Item(int[][] decal, Statistics stats) {
        this.decal = decal;
        this.stats = stats;
    }

    public int[][] getDecal() {
        return this.decal;
    }

    public Statistics getStats() {
        return this.stats;
    }

    public boolean isCollidable() {
        return false;
    }

    public abstract void onTouch(Entity e);

    public abstract boolean isActivatable();
}
