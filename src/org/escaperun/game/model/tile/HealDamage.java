package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class HealDamage extends AreaEffect {

    public HealDamage(int heal) {
        super(new Decal((char) 3, Color.BLACK, Color.RED.brighter()), heal);
    }

    @Override
    public void onTouch(Entity e) {
        e.healDamage(valueofchange); //Auto-heal iz de bes!
    }
}
