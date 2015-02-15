package org.escaperun.game.states.mainmenu;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.entities.Occupation;
import org.escaperun.game.serialization.SaveManager;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.loading.LoadGame;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.security.Key;

public class Creation extends GameState {
    public static final int TICKS_PER_MOVEMENT = 10;
    public final static SaveManager saveManager = new SaveManager();

    private static final Option[] OPTIONS = {
            new Option("SUMMONER", new Playing(saveManager.startNewGame(new Avatar(Occupation.SUMMONER)))),
            new Option("SMASHER", new Playing(saveManager.startNewGame(new Avatar(Occupation.SMASHER)))),
            new Option("SNEAK", new Playing(saveManager.startNewGame(new Avatar(Occupation.SNEAK)))),
            new Option("Never Mind", new MainMenu())
    };

    private int selectedOption = 1;
    private int ticksSince = 0;

    @Override
    public GameState update(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];

        int nextIdx = selectedOption;

        if (nextIdx == 3 && up) {  //"Never Mind"
            nextIdx = 1;
        }
        else if (nextIdx < 3 && down){
            nextIdx = 3;
        }
        else if (left && nextIdx > 0) {
            nextIdx--;
        }
        else if (right && nextIdx < 2){
            nextIdx++;
        }
        if (nextIdx >= 0 && nextIdx < OPTIONS.length && ticksSince >= TICKS_PER_MOVEMENT && (up || down || left || right)){
            selectedOption = nextIdx;
            ticksSince = 0;
        }
        ticksSince++;
        boolean enter = pressed[Keyboard.ENTER];
        if (enter){
            GameState next = OPTIONS[selectedOption].nextState;
            pressed[Keyboard.ENTER] = false;
            return next;
        }
        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        int word_length = OPTIONS[0].text.length() + OPTIONS[1].text.length() + OPTIONS[2].text.length();
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

        x = (GameWindow.COLUMNS)/2 - OPTIONS[3].text.length()/2;
        y = GameWindow.ROWS/2 + 1;
        toblit = (selectedOption == 3 ) ? OPTIONS[3].selected[0] : OPTIONS[3].unselected[0];
        for (int i = 0; i < toblit.length; i++)
            ret[y][x+i] = toblit[i];

        return ret;
    }
}
