package org.escaperun.game.model;

import org.escaperun.game.model.entities.Entity;

/**
 * Created by Eric on 2/11/2015.
 */
public interface Activatable {
    public void onTouch(Entity e);
    public boolean isActivatable();
}
