package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class InteractiveItem extends Item {

    public InteractiveItem() {
        super();
    }

    public InteractiveItem(Decal[][] decal, Statistics stats) {
        super(decal, stats);
    }

    @Override
    public void onTouch(Entity e) {
        // TODO: Figure how this will be implemented.
    }

    @Override
    public boolean isActivatable() {
        // TODO: Implement logic for this.
        return false;
    }
}