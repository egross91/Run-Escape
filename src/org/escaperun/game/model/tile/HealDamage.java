package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

public class HealDamage extends AreaEffect{

    public HealDamage(Decal decal) {
        super(decal);
    }

    public void areaEffect(){
        //TODO: Figure out implementation
    }
    public void onTouch(Entity e){
        //TODO: Figure out implementation (most likely call areaEffect() )
    }
}
