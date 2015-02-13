package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class TakeableItem extends Item {

    public TakeableItem() {
        super();
    }

    public TakeableItem(Decal decal, Statistics stats) {
        super(decal, stats, false);
    }

    public TakeableItem(Decal decal, Statistics stats, boolean collidable) {
        super(decal, stats, collidable);
    }

    @Override
    public void onTouch(Entity e) {
        // TODO: Figure how this will be implemented.
    }

    @Override
    public boolean isActivatable() {
        return false;
    }
}
