package org.escaperun.game.states.loading;

import org.escaperun.game.Keyboard;
import org.escaperun.game.serialization.SaveManager;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.mainmenu.MainMenu;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.awt.*;

public class LoadGame extends GameState {

    private static final int TICKS_PER_MOVEMENT = 10;
    private Option[] options;

    public LoadGame(MainMenu from) {
        String[] saveManager = SaveManager.getProfiles();
        options = new Option[saveManager.length+1];
        for (int i = 0; i < options.length-1; i++) {
            options[i] = new Option(saveManager[i], new Playing(SaveManager.loadSavedGame(saveManager[i])));
        }
        options[options.length-1] = new Option("Return", from);
    }

    private int selectedOption = 0;
    private int ticksSince = 0;

    @Override
    public GameState update(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];

        int nextIdx = selectedOption;

        if (up) nextIdx--;
        if (down) nextIdx++;

        if (nextIdx >= 0 && nextIdx < options.length && ticksSince >= TICKS_PER_MOVEMENT && (up || down)) {
            selectedOption = nextIdx;
            ticksSince = 0;
        }
        ++ticksSince;

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
            int x = GameWindow.ROWS/options.length-2+options.length*i;
            int y = GameWindow.COLUMNS/2-options[i].text.length()/2;
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
