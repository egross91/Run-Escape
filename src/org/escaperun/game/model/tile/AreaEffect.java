package org.escaperun.game.model.tile;

import org.escaperun.game.model.Touchable;
import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AreaEffect implements Touchable, Drawable, Savable {
    protected int valueofchange;
    protected Decal decal;

    public AreaEffect(Decal decal){
        this.decal = decal;
    }
    public AreaEffect(Decal decal, int value) {
        this.decal = decal;
        this.valueofchange = value;
    }

    public abstract void onTouch(Entity e);

    public Decal getDecal() {
        return decal;
    }

    @Override
    public Element save(Document dom) {
        Element areaEffectElement = dom.createElement("AreaEffect");
        areaEffectElement.setTextContent(Integer.toString(valueofchange));

        return areaEffectElement;
    }
}
