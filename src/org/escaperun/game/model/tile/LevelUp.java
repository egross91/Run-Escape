package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

/**
 * Created by abcronin on 2/11/2015.
 */
public class LevelUp extends AreaEffect{

    public LevelUp(){super();}

    public LevelUp(Decal decal){super(decal);}

    public void areaEffect(){
        //TODO: Figure out implementation
    }
    public void onTouch(Entity e){
        //TODO: Figure out implementation (most likely call areaEffect() )
    }
}
