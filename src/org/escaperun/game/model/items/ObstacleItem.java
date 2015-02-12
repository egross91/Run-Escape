package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;

/**
 * Created by Eric on 2/11/2015.
 */
public class ObstacleItem extends Item {

    public ObstacleItem() {
        super(new int[0][0], null);
    }

    @Override
    public boolean isCollidable() {
        // Don't allow Entities to pass through.
        return true;
    }

    @Override
    public void onTouch(Entity e) {
        // Swallow the function call.
    }

    @Override
    public boolean isActivatable() {
        return false;
    }
}
