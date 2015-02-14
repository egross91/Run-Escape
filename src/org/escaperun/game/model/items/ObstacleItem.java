package org.escaperun.game.model.items;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class ObstacleItem extends Item implements Drawable {

    public ObstacleItem() {
        super(new Decal('B', Color.BLACK, Color.DARK_GRAY), null, true);
    }

    @Override
    public void doAction(Entity e) {

    }

    @Override
    public void onTouch(Entity e) {
        // Swallow the function call.
    }
}
