package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

public class HealDamage extends AreaEffect{

    public HealDamage(Decal decal, int heal) {
        super(decal, heal);
    }

    public void areaEffect(){
        //TODO: Figure out implementation
    }
    public void onTouch(Entity e){
        e.healDamage(valueofchange); //Auto-heal iz de bes!
    }
}
