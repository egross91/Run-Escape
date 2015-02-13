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
    private Decal decal;
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
        setAreaEffect(areaEffect);
        this.item = null;
    }

    public Tile(Item item) {
        this.terrain = new Grass();
        this.areaEffect = null;
        setItem(item);
    }

    public Tile(AreaEffect areaEffect, Item item) {
        this.terrain = new Grass();
        this.areaEffect = areaEffect;
        setItem(item);
    }

    public Tile(Terrain terrain, AreaEffect areaEffect, Item item) {
        this.terrain = terrain;
        this.areaEffect = areaEffect;
        setItem(item);
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item i) {
        this.decal = i.getDecal();
        this.item = i;
    }

    public AreaEffect getAreaEffect() {
        return this.areaEffect;
    }

    public void setAreaEffect(AreaEffect aoe) {
        if (this.item == null)
            this.decal = this.areaEffect.getDecal();
        this.areaEffect = aoe;
    }

    private void removeItem() {
        this.item = null;
        if (this.areaEffect != null)
            this.decal = this.areaEffect.getDecal();
        else
            this.decal = this.terrain.getDecal();
    }

    private void removeAreaEffect() {
        this.areaEffect = null;
        this.decal = this.terrain.getDecal();
    }

    public void startAoE(Entity e) {
        // TODO: Implement logic for AoE effects on Entity.
        if (this.areaEffect != null) {
            // Perform logic for AoE on Entity.
        }
        // Otherwise, do nothing.
    }

    @Override
    public Decal getDecal() {
        if (this.decal == null)
            this.decal = this.terrain.getDecal();
        return this.decal;
    }

    public void setDecal(Decal decal) {
        this.decal = decal;
    }
}
