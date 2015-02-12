package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;

/**
 * Created by Eric on 2/11/2015.
 */
public class UsableItem extends TakeableItem {

    public UsableItem() {
        super();
    }

    public UsableItem(int[][] decal, Statistics stats) {
        super(decal, stats);
    }

    public void statChange(Entity e) {
        // TODO: Implement logic for Entity statistic changes.
    }
}
