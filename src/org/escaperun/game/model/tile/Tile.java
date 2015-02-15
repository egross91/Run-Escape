package org.escaperun.game.model.tile;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Tile implements Drawable, Savable {

    private Terrain terrain;
    private AreaEffect areaEffect;
    private Item item;

    public Tile() {
        this.terrain = null;
        this.areaEffect = null;
        this.item = null;
    }

    public Tile(Terrain terrain, Item item, AreaEffect area) {
        this.terrain = terrain;
        this.item = item;
        this.areaEffect = area;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain t) {
        terrain = t;
    }

    public Item getItem() {
        return item;
    }

    public boolean setItem(Item i) {
        if (item != null) return false;
        item = i;
        return true;
    }

    public Item removeItem() {
        if (item == null) throw new RuntimeException("item is null");
        Item cur = item;
        item = null;
        return cur;
    }

    public AreaEffect getAreaEffect() {
        return areaEffect;
    }

    public boolean setAreaEffect(AreaEffect aoe) {
        if (areaEffect != null) return false;
        areaEffect = aoe;
        return true;
    }

    public AreaEffect removeAreaEffect() {
        if (areaEffect == null) throw new RuntimeException("areaEffect is null");
        AreaEffect aoe = areaEffect;
        areaEffect = null;
        return aoe;
    }

    public void onTouch(Entity entity){
        if (item != null)
            item.onTouch(entity);
        if (areaEffect != null)
            areaEffect.onTouch(entity);
    }

    @Override
    public Decal getDecal() {
        if (item != null) return item.getDecal();
        if (areaEffect != null) return areaEffect.getDecal();
        if (terrain != null) return terrain.getDecal();
        return null;
    }

    @Override
    public Element save(Document dom) {
        Element tileElement = dom.createElement("Tile");
        if (item != null) {
            tileElement.appendChild(item.save(dom));
        }
        if (areaEffect != null) {
            tileElement.appendChild(areaEffect.save(dom));
        }
        if (terrain != null) {
            tileElement.appendChild(terrain.save(dom));
        }

        return tileElement;
    }
}
