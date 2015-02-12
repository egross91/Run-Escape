package org.escaperun.game.model.tile;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.items.Item;

/**
 * Created by Eric on 2/11/2015.
 */
public class Tile implements Drawable {
    private final Terrain terrain;
    //private final AreaEffect areaEffect;
    private Item item;

    public Tile() {
        // TODO: Uncomment/Change when classes are implemented.
        this.terrain = null;
//        this.areaEffect = new AreaEffect();
        this.item = null;
    }

    public Tile(Terrain t, /*AreaEffect a,*/ Item i) {
        this.terrain = t;
//        this.areaEffect = a;
        this.item = i;
    }

    private void removeItem() {
        this.item = null;
    }

//    private void removeAreaEffect() {
//        this.areaEffect = null;
//    }

    public void startAoE(Entity e) {
        // TODO: Implement logic for AoE effects once they're implemented.
    }

    @Override
    public int[][] getDecal() {
        return this.terrain.getDecal();
    }
}
