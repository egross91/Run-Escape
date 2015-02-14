package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class InstantDeath extends AreaEffect {

    public InstantDeath() {
        super(new Decal((char) 2, Color.BLACK, Color.WHITE));
    }

    @Override
    public void onTouch(Entity e) {
        e.takeDamage(9999999); //Auto-kill gg wp no-re
    }
}
