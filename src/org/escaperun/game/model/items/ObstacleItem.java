package org.escaperun.game.model.items;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class ObstacleItem extends Item implements Drawable {

    public ObstacleItem() {
        super(new Decal('B', Color.BLACK, Color.DARK_GRAY), new Statistics(), true);
    }

    @Override
    public void doAction(Entity e) {

    }

    @Override
    public void onTouch(Entity e) {
        // Swallow the function call.
    }

    public String getTypeToString() {
        return "obstacle";
    }

    @Override
    public Element save(Document dom) {
        Element obstacleElement = super.save(dom);
        obstacleElement.setAttribute("type", getTypeToString());

        return obstacleElement;
    }
}
