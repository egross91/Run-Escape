package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;

/**
 * Created by Eric on 2/11/2015.
 */
public class OneShotItem extends Item {

    public OneShotItem() {
        super();
    }

    public OneShotItem(int[][] decal, Statistics stats) {
        super(decal, stats);
    }

    @Override
    public void onTouch(Entity e) {
        // TODO: Apply the items stat changing effects to Entity.
    }

    @Override
    public boolean isActivatable() {
        return true;
    }
}
