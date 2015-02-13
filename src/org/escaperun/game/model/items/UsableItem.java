package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class UsableItem extends TakeableItem {

    public UsableItem() {
        super();
    }

    public UsableItem(Decal decal, Statistics stats) {
        super(decal, stats, false);
    }

    public UsableItem(Decal decal, Statistics stats, boolean collidable) {
        super(decal, stats, collidable);
    }

    public void statChange(Entity e) {
        // TODO: Implement logic for Entity statistic changes.
    }
}
