package org.escaperun.game;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.mainmenu.MainMenu;
import org.escaperun.game.view.Decal;

public class Game {

    private GameState state;

    public Game() {
        state = new MainMenu();
    }

    // return false if done updating (exit)
    public boolean update(boolean[] pressed) {
        if (state == null) throw new RuntimeException("game has no state");

        GameState res = state.update(pressed);
        if (res != null) {
            // game state changing!
            state = res;
        }

        return true;
    }

    public Decal[][] getRenderable() {
        if (state == null) throw new RuntimeException("game is not drawable");
        return state.getRenderable();
    }
}