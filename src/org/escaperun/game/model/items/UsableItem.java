package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    @Override
    public void doAction(Entity e) {
        e.useItem(this);
    }

    @Override
    public void onTouch(Entity e) {
        if (e.addItemToInventory(this));
        //TODO: Add functionality based upon whether or not this add to inventory was successful.
    }

    public String getTypeToString() {
        return "usable";
    }
}
