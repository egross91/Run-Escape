package org.escaperun.game.states.loading;

import org.escaperun.game.Keyboard;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

public class LoadGame extends GameState {
    private static final int TICKS_PER_MOVEMENT = 10;

    private static final Option[] OPTIONS = new Option[]{
            new Option("n00b", new Playing(Creation.saveManager.loadSavedGame("LOL")))
    };

    private int selectedOption = 0;
    private int ticksSince = 0;

    @Override
    public GameState update(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];

        int nextIdx = selectedOption;

        if (ticksSince >= TICKS_PER_MOVEMENT && (up || down)) {
            selectedOption = nextIdx;
        }
        ++ticksSince;

        boolean enter = pressed[Keyboard.ENTER];
        if (enter) {
            GameState next = OPTIONS[selectedOption].nextState;
            pressed[Keyboard.ENTER] = false;
            return next;
        }

        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        // TODO: Make this center.
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        int word_length = OPTIONS[0].text.length();
        int x = (GameWindow.COLUMNS - word_length)/4;
        int y = GameWindow.ROWS/2 - 2;
        Decal[] toblit;
        for (int i = 0; i < OPTIONS.length-1; i++) {
            if (i == selectedOption)
                toblit = OPTIONS[i].selected[0];
            else
                toblit = OPTIONS[i].unselected[0];

            for (int j = 0; j < OPTIONS[i].text.length(); j++)
                ret[y][x+j] = toblit[j];
            x += (GameWindow.COLUMNS - word_length)/4 + OPTIONS[i].text.length();
        }

        y = GameWindow.ROWS/2 + 1;
        toblit = OPTIONS[0].selected[0];
        for (int i = 0; i < toblit.length; i++)
            ret[y][x+i] = toblit[i];

        return ret;
    }
}
