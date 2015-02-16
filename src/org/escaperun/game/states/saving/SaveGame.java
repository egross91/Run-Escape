package org.escaperun.game.states.saving;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Stage;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.playing.Playing;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.awt.*;

public class SaveGame extends GameState {

    public static final String TYPE_PREFIX = "Name: ";
    public static final int TICKS_PER_FLASH = 25;
    public static final int TYPES_PER_TICK = 10;
    public static final int BACKS_PER_TICK = 8;

    public SaveGame(Playing playing) {
        this.playing = playing;
    }

    private Playing playing;
    private String name = "";
    private boolean flash = true;
    private int flashTick = 0;
    private int typeTick = TYPES_PER_TICK;
    private int backTick = BACKS_PER_TICK;
    private char lastPress = '\u0000';

    @Override
    public GameState update(boolean[] pressed) {
        if (flashTick >= TICKS_PER_FLASH) {
            flashTick = 0;
            flash = !flash;
        }
        flashTick++;


        if (isCharPressed(pressed) && name.length() < 20) {
            char ch = getCharPressed(pressed);
            if (ch != lastPress || (typeTick >= TYPES_PER_TICK)) {
                name += ch;
                lastPress = ch;
                typeTick = 0;
            }
        }
        typeTick++;

        boolean backspace = pressed[Keyboard.BACKSPACE];
        if (backspace && name.length() > 0 && backTick > BACKS_PER_TICK) {
            name = name.substring(0, name.length()-1);
            backTick = 0;
        }
        backTick++;

        boolean enter = pressed[Keyboard.ENTER];
        if (enter && name.length() > 0) {
            Creation.saveManager.saveCurrentGame(playing.getStage(), playing.getStage().getAvatar(),name);
            return playing;
        }


        return null;
    }

    private boolean isCharPressed(boolean[] pressed) {
        for (char ch = 'A'; ch <= 'z'; ch++) {
            if (pressed[(int) ch]) {
                return true;
            }
        }
        return false;
    }

    private char getCharPressed(boolean[] pressed) {
        for (char ch = 'A'; ch <= 'z'; ch++) {
            if (pressed[(int) ch]) {
                return ch;
            }
        }
        return '\u0000'; // shouldnt happen
    }

    @Override
    public Decal[][] getRenderable() {

        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        int typeRow = GameWindow.ROWS/2-4;
        int typeCol = GameWindow.COLUMNS/2-TYPE_PREFIX.length()/2;
        ret[typeRow] = new Decal[GameWindow.COLUMNS];
        for (int i = 0; i < TYPE_PREFIX.length(); i++) {
            ret[typeRow][typeCol+i] = new Decal(TYPE_PREFIX.charAt(i), Color.BLACK, Color.WHITE);
        }
        int startColOther = typeCol+TYPE_PREFIX.length();
        for (int i = 0; i < name.length(); i++) {
            ret[typeRow][startColOther+i] = new Decal(name.charAt(i), Color.BLACK, Color.WHITE);
        }
        int startColFlash = typeCol+TYPE_PREFIX.length()+name.length();
        ret[typeRow][startColFlash] = new Decal('\u0000', flash ? Color.WHITE : Color.BLACK, Color.WHITE);
        return ret;
    }
}
