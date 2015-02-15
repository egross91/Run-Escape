package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InteractiveItem extends Item {

    public InteractiveItem() {
        super();
    }

    public InteractiveItem(Decal decal, Statistics stats) {
        super(decal, stats, false);
    }

    public InteractiveItem(Decal decal, Statistics stats, boolean collidable) {
        super(decal, stats, collidable);
    }

    @Override
    public void onTouch(Entity e) {
        // TODO: Figure how this will be implemented.
    }

    @Override
    public void doAction(Entity e) {

    }

    public String getTypeToString() {
        return "interactive";
    }

    @Override
    public Element save(Document dom) {
        Element interactiveElement = super.save(dom);
        interactiveElement.setAttribute("type", getTypeToString());

        return interactiveElement;
    }
}
