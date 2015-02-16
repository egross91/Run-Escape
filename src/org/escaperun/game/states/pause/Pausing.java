package org.escaperun.game.states.pause;

import org.escaperun.game.Keyboard;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.mainmenu.MainMenu;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.states.saving.SaveGame;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

public class Pausing extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private final Option[] options;

    private int selectedOption = 0;
    private int ticksSince = 0;
    private Playing prevState;

    public Pausing(Playing prevState) {
        this.prevState = prevState;
        options = new Option[3];
        options[0] = new Option("Save Game", new SaveGame(prevState));
        options[1] = new Option("Return", prevState);
        options[2] = new Option("Exit to Main Menu", new MainMenu());
    }

    @Override
    public GameState update(boolean[] pressed) {
        boolean escape = pressed[Keyboard.ESCAPE];
        if (escape) {
            pressed[Keyboard.ESCAPE] = false; // TODO: Global cooldown on keys.
            return prevState;
        }

        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        int nextIdx = selectedOption;
        if (up) nextIdx--;
        if (down) nextIdx++;
        if (nextIdx >= 0 && nextIdx < options.length && ticksSince >= TICKS_PER_MOVEMENT && (up || down)) {
            selectedOption = nextIdx;
            ticksSince = 0;
        }
        ticksSince++;


        boolean enter = pressed[Keyboard.ENTER];
        if (enter) {
            GameState next = options[selectedOption].nextState;
            pressed[Keyboard.ENTER] = false;
            return next;
        }
        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        for (int i = 0; i < options.length; i++) {
            int x = GameWindow.ROWS/ options.length-2+ options.length*i;
            int y = GameWindow.COLUMNS/2- options[i].text.length()/2;
            Decal[] toblit;
            if (selectedOption == i) {
                toblit = options[i].selected[0];
            } else {
                toblit = options[i].unselected[0];
            }
            for (int j = 0; j < options[i].text.length(); j++) {
                ret[x][y+j] = toblit[j];
            }
        }
        // TODO: Make beautiful animations
        return ret;
    }

}