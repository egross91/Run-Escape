package org.escaperun.game.model.tile;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.view.Decal;

public class InstantDeath extends AreaEffect{

    public InstantDeath(Decal decal) {
        super(decal);
    }

    @Override
    public  void areaEffect() {
        // TODO: figure out implementation
    }
    @Override
    public void onTouch(Entity e) {
        // TODO: figure out implementation (most likely call areaEffect() )
    }
}
