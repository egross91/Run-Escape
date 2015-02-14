package org.escaperun.game.model.items;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

public abstract class Item implements Activatable, Collidable {
    private Statistics stats;
    private final boolean collidable;
    private final Decal decal;

    public Item() {
        this.collidable = false;
        this.stats = null;
        this.decal = null;
    }

    public Item(Statistics stats) {
        this.stats = stats;
        this.decal = getDecal();
        this.collidable = false;
    }

    public Item(Decal decal, Statistics stats, boolean collidable) {
        this.stats = stats;
        this.decal = decal;
        this.collidable = collidable;
    }

    public Statistics getStats() {
        return this.stats;
    }

    public Decal getDecal() {
        return this.decal;
    }

    public boolean isCollidable() {
        return this.collidable;
    }

    public abstract void onTouch(Entity e);

    public abstract boolean isActivatable(Entity e);
}
