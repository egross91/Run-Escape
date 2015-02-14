package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

public class TakeableItem extends Item {

    public TakeableItem() {
        super();
    }

    public TakeableItem(Statistics stats) {
        super(stats);
    }

    public TakeableItem(Decal decal, Statistics stats) {
        super(decal, stats, false);
    }


    public TakeableItem(Decal decal, Statistics stats, boolean collidable) {
        super(decal, stats, collidable);
    }

    @Override
    public void doAction(Entity e) {

    }

    @Override
    public void onTouch(Entity e) {

    }
}
