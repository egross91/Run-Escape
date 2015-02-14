package org.escaperun.game.states.mainmenu;

import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.awt.*;

public class Option {

    public final String text;
    public final GameState nextState;
    public final Decal[][] unselected;
    public final Decal[][] selected;

    public Option(String text, GameState nextState) {
        this.text = text;
        this.nextState = nextState;
        unselected = new Decal[1][text.length()];
        for (int i = 0; i < text.length(); i++) {
            unselected[0][i] = new Decal(text.charAt(i), Color.BLACK, Color.WHITE);
        }
        selected = new Decal[1][text.length()];
        for (int i = 0; i < text.length(); i++) {
            selected[0][i] = new Decal(text.charAt(i), Color.LIGHT_GRAY.darker(), Color.BLUE);
        }
    }
}
