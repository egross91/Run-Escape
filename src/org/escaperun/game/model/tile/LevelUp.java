package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class LevelUp extends AreaEffect {

    public LevelUp() {
        super(new Decal('*', Color.BLACK, new Color(255, 215, 0)));
    }

    public void onTouch(Entity e){
        e.levelUp();
        System.out.println("ENTITY GOT LEVEL UP!!!");
    }
}
