package org.escaperun.game.states;

import org.escaperun.game.view.Decal;

public abstract class GameState {

    public abstract GameState update(boolean[] pressed);
    public abstract Decal[][] getRenderable();
}
