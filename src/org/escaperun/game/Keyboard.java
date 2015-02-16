package org.escaperun.game;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    // change when we decide to actually deploy game
    // I (josh) can't use numpad (laptop) so these are really convenient for me

    public static final int WATER = (int) 'y';
    public static final char MOUNTAIN = 'm';
    public static final char INST_DEATH = 'p';
    public static final char OBSTACLE = 'o';
    public static final char USABLE_ITEM = 'u';
    public static final char ONE_SHOT = 'k';
    public static final char INTERACTIVE_ITEM = 'j';
    public static final char EQUIPABLE_ITEM = 'e';
    public static final char LEVEL_UP = 'l';
    public static final char TAKE_DAMAGE = 't';
    public static final char HEAL_DAMAGE = 'h';
    public static final char ENTER = (char) 10;
    public static final char ESCAPE = (char) 27;
    public static final char BACKSPACE = (char) 8;

    public static final int UP = (int)'w';
    public static final int DOWN = (int)'s';
    public static final int LEFT = (int)'a';
    public static final int RIGHT = (int)'d';
    public static final int BLANK = (int)'b';
    public static final int GRASS = (int)'g';
    public static final int INV = (int)'i';

    //numpad
    public static final int NUM_DOWN = (int)'2';
    public static final int NUM_UP = (int)'8';
    public static final int NUM_LEFT = (int)'4';
    public static final int NUM_RIGHT = (int)'6';
    public static final int NUM_UPLEFT = (int)'7';
    public static final int NUM_UPRIGHT = (int)'9';
    public static final int NUM_DOWNRIGHT = (int)'3';
    public static final int NUM_DOWNLEFT = (int)'1';

    public final boolean[] pressed = new boolean[65536]; // There are 2^16 = 65536 possible chars.

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[Character.toLowerCase(e.getKeyChar())] = false;
        //pressed[(int)e.getKeyChar()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
       pressed[Character.toLowerCase(e.getKeyChar())] = true;
    }
}
