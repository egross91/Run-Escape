package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class Mountain extends Terrain{

//    public Mountain(){
//        super(new Decal('M', Color.BLACK, Color.LIGHT_GRAY), true);
//    }

    //Changed the decal to look more like a mountain... please revert if this is not good.
    public Mountain(){
        super(new Decal((char) 30, Color.BLACK, Color.LIGHT_GRAY), true);
    }

    public String getTypeToString() {
        return "mountain";
    }

    @Override
    public Element save(Document dom) {
        Element mountainTerrain = super.save(dom);
        mountainTerrain.setAttribute("type", getTypeToString());

        return mountainTerrain;
    }
}
