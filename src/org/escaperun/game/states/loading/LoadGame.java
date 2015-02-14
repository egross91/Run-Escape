package org.escaperun.game.states.loading;

import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;

public class LoadGame extends GameState {

    @Override
    public GameState update(boolean[] pressed) {
        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        return new Decal[0][];
    }
}
