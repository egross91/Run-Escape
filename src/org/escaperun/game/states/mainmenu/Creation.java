package org.escaperun.game.states.mainmenu;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.entities.Occupation;
import org.escaperun.game.serialization.SaveManager;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

public class Creation extends GameState {
    public static final int TICKS_PER_MOVEMENT = 10;

    private final Option[] options;

    private int selectedOption = 1;
    private int ticksSince = 0;

    public Creation(MainMenu from) {
        options = new Option[4];
        options[0] = new Option("SUMMONER", new Playing(SaveManager.startNewGame(new Avatar(Occupation.SUMMONER))));
        options[1] = new Option("SMASHER", new Playing(SaveManager.startNewGame(new Avatar(Occupation.SMASHER))));
        options[2] = new Option("SNEAK", new Playing(SaveManager.startNewGame(new Avatar(Occupation.SNEAK))));
        options[3] = new Option("Never Mind", from);
    }

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
        if (nextIdx >= 0 && nextIdx < options.length && ticksSince >= TICKS_PER_MOVEMENT && (up || down || left || right)){
            selectedOption = nextIdx;
            ticksSince = 0;
        }
        ticksSince++;
        boolean enter = pressed[Keyboard.ENTER];
        if (enter){
            GameState next = options[selectedOption].nextState;
            pressed[Keyboard.ENTER] = false;
            return next;
        }
        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        int word_length = options[0].text.length() + options[1].text.length() + options[2].text.length();
        int x = (GameWindow.COLUMNS - word_length)/4;
        int y = GameWindow.ROWS/2 - 2;
        Decal[] toblit;
        for (int i = 0; i < options.length-1; i++) {
            if (i == selectedOption)
                toblit = options[i].selected[0];
            else
                toblit = options[i].unselected[0];

            for (int j = 0; j < options[i].text.length(); j++)
                ret[y][x+j] = toblit[j];
            x += (GameWindow.COLUMNS - word_length)/4 + options[i].text.length();
        }

        x = (GameWindow.COLUMNS)/2 - options[3].text.length()/2;
        y = GameWindow.ROWS/2 + 1;
        toblit = (selectedOption == 3 ) ? options[3].selected[0] : options[3].unselected[0];
        for (int i = 0; i < toblit.length; i++)
            ret[y][x+i] = toblit[i];

        return ret;
    }
}