package org.escaperun.game.model.tile;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class Tile implements Drawable {
    private final Terrain terrain;
    private AreaEffect areaEffect;
    private Item item;

    public Tile() {
        this.terrain = new Grass();
        this.areaEffect = null;
        this.item = null;
    }

    public Tile(Terrain terrain) {
        this.terrain = terrain;
        this.areaEffect = null;
        this.item = null;
    }

    public Tile(AreaEffect areaEffect) {
        this.terrain = new Grass();
        this.areaEffect = areaEffect;
        this.item = null;
    }

    public Tile(Item item) {
        this.terrain = new Grass();
        this.areaEffect = null;
        this.item = item;
    }

    public Tile(AreaEffect areaEffect, Item item) {
        this.terrain = new Grass();
        this.areaEffect = areaEffect;
        this.item = item;
    }

    public Tile(Terrain terrain, AreaEffect areaEffect, Item item) {
        this.terrain = terrain;
        this.areaEffect = areaEffect;
        this.item = item;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item i) {
        this.item = i;
    }

    public AreaEffect getAreaEffect() {
        return this.areaEffect;
    }

    public void setAreaEffect(AreaEffect aoe) {
        this.areaEffect = aoe;
    }

    private void removeItem() {
        this.item = null;
    }

    private void removeAreaEffect() {
        this.areaEffect = null;
    }

    public void startAoE(Entity e) {
        // TODO: Implement logic for AoE effects on Entity.
        if (this.areaEffect != null) {
            // Perform logic for AoE on Entity.
        }
        // Otherwise, do nothing.
    }

    @Override
    public Decal[][] getDecal() {
        return this.terrain.getDecal();
    }
}
