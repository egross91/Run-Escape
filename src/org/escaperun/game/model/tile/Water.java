package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class Water extends Terrain {
    public Water(){
        super(new Decal((char) 247, Color.BLACK, new Color(0, 200, 255)), true);
    }

    public String getTypeToString() {
        return "water";
    }

    @Override
    public Element save(Document dom) {
        Element waterTerrain = super.save(dom);
        waterTerrain.setAttribute("type", getTypeToString());

        return waterTerrain;
    }
}
