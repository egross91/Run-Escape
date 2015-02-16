package org.escaperun.game.model.items;

import org.escaperun.game.model.Activatable;
import org.escaperun.game.model.Touchable;
import org.escaperun.game.model.Collidable;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public abstract class Item implements Activatable, Touchable, Collidable, Savable {
    private Statistics stats;
    private final boolean collidable;
    private final Decal decal;

    public Item() {
        this.collidable = false;
        this.stats = null;
        this.decal = null;
    }

    public Item(Statistics stats) {
        this.stats = stats;
        this.decal = null;
        this.collidable = false;
    }

    public Item(Decal decal, Statistics stats, boolean collidable) {
        this.stats = stats;
        this.decal = decal;
        this.collidable = collidable;
    }

    public Statistics getStats() {
        return this.stats;
    }

    public Decal getDecal() {
        return this.decal;
    }

    public boolean isCollidable() {
        return this.collidable;
    }


    public abstract String getTypeToString();

    @Override
    public Element save(Document dom) {
        Element itemElement = dom.createElement("Item");
        itemElement.setAttribute("type", getTypeToString());
        if (stats != null) {
            Element statsElement = stats.save(dom);
            itemElement.appendChild(statsElement);
        }
        if (decal != null) {
            Element decalElement = decal.save(dom);
            itemElement.appendChild(decalElement);
        }
        return itemElement;
    }
}
