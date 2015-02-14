package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

public class TakeDamage extends AreaEffect{

    public TakeDamage(Decal decal) {
        super(decal);
    }

    public TakeDamage(Decal decal, int damage){
        super(decal, damage);
    }

    public void areaEffect(){
        //TODO: Figure out implementation
    }
    public void onTouch(Entity e){
        e.takeDamage(valueofchange);
    }
}
