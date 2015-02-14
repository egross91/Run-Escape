package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class TakeDamage extends AreaEffect{

    public TakeDamage(int damage) {
        super(new Decal((char) 30, Color.BLACK, new Color(255, 100, 0)), damage);
    }

    public void onTouch(Entity e){
        e.takeDamage(valueofchange);
        System.out.println("ENTITY GOT TAKE DAMAGE!!!");
    }
}
