package org.escaperun.game.states.mainmenu;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.entities.Occupation;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.MapCreation.CreateMap;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.loading.LoadGame;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.awt.*;

public class MainMenu extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private static final Option[] OPTIONS = {
        new Option("Create Map", new CreateMap(new Stage(new Dimension(50, 50), new Avatar(Occupation.SNEAK)))),
        new Option("New Game", new Creation()),
        new Option("Load Game", new LoadGame()),
        new Option("Exit", new Exit()),
        new Option("THE OPTION TO END ALL OPTIONS", null),
        new Option("WHATS UP THIS IS A REALLY BIG OPTION BIG", null)
    };

    private int selectedOption = 0;
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
        ticksSince++;
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
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        for (int i = 0; i < OPTIONS.length; i++) {
            int x = GameWindow.ROWS/OPTIONS.length-2+OPTIONS.length*i;
            int y = GameWindow.COLUMNS/2-OPTIONS[i].text.length()/2;
            Decal[] toblit;
            if (selectedOption == i) {
                toblit = OPTIONS[i].selected[0];
            } else {
                toblit = OPTIONS[i].unselected[0];
            }
            for (int j = 0; j < OPTIONS[i].text.length(); j++) {
                ret[x][y+j] = toblit[j];
            }
        }
        // TODO: Make beautiful animations
        return ret;
    }
}
