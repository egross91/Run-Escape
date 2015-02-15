package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OneShotItem extends Item {

    public OneShotItem() {
        super();
    }

    public OneShotItem(Decal decal, Statistics stats) {
        super(decal, stats, false);
    }

    public OneShotItem(Decal decal, Statistics stats, boolean collidable) {
        super(decal, stats, collidable);
    }

    @Override
    public void doAction(Entity e) {

    }

    @Override
    public void onTouch(Entity e) {
        // TODO: Apply the items stat changing effects to Entity.
    }

    public String getTypeToString() {
        return "oneshot";
    }

    @Override
    public Element save(Document dom) {
        Element oneShotElement = super.save(dom);
        oneShotElement.setAttribute("type", getTypeToString());

        return oneShotElement;
    }
}
