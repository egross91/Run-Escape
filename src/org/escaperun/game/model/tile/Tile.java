package org.escaperun.game.model.tile;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.view.Decal;

public class Tile implements Drawable {

    private Terrain terrain;
    private AreaEffect areaEffect;
    private Item item;

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

    @Override
    public Decal getDecal() {
        if (item != null) return item.getDecal();
        if (areaEffect != null) return areaEffect.getDecal();
        return terrain.getDecal();
    }
}
