package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class EquipableItem extends TakeableItem {

    public EquipableItem() {
        super();
    }

    public EquipableItem(Decal[][] decal, Statistics stats) {
        super(decal, stats);
    }

    // TODO: Implement the logic for this Item.
}
