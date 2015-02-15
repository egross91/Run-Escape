package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class LevelUp extends AreaEffect {

    public LevelUp() {
        super(new Decal('*', Color.BLACK, new Color(255, 215, 0)));
    }

    public void onTouch(Entity e){
        e.levelUp();
        e.healDamage(99999);
    }

    public String getTypeToString() {
        return "levelup";
    }

    @Override
    public Element save(Document dom) {
        Element levelUpElement = super.save(dom);
        levelUpElement.setAttribute("type", getTypeToString());

        return levelUpElement;
    }
}
