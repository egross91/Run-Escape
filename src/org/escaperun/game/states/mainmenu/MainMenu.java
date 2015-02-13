package org.escaperun.game.states.mainmenu;

import org.escaperun.game.Keyboard;
import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;

public class MainMenu extends GameState {

    private static final Option[] OPTIONS = {
        new Option("Create New Game"),
            new Option("Load Game")
    };

    private int selectedOption = 0;
    public static final int TICKS_PER_MOVEMENT = 10;
    private int ticksSince = 0;

    @Override
    public GameState update(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        int nextIdx = selectedOption;
        if (up) nextIdx--;
        if (down) nextIdx++;
        if (nextIdx >= 0 && nextIdx < OPTIONS.length && ticksSince >= TICKS_PER_MOVEMENT && (up || down)) {
            selectedOption = nextIdx;
            ticksSince = 0;
        }
        boolean enter = pressed[Keyboard.ENTER];

        //TODO: Select and return new game state


        ticksSince++;
        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        Decal[][] ret = new Decal[OPTIONS.length][];
        for (int i = 0; i < OPTIONS.length; i++) {
            if (selectedOption == i) {
                ret[i] = OPTIONS[i].selected[0];
            } else {
                ret[i] = OPTIONS[i].unselected[0];
            }
        }
        return ret;
    }
}
