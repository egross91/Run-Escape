package org.escaperun.game;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    public static final int UP = (int)'w';
    public static final int DOWN = (int)'s';
    public static final int LEFT = (int)'a';
    public static final int RIGHT = (int)'d';
    public static final int BLANK = (int)'b';
    public static final int GRASS = (int)'g';
    public static final int INV = (int)'i';
    public static final int WATER = (int)'h';

    //numpad
    public static final int NUM_DOWN = (int)'2';
    public static final int NUM_UP = (int)'8';
    public static final int NUM_LEFT = (int)'4';
    public static final int NUM_RIGHT = (int)'6';
    public static final int NUM_UPLEFT = (int)'7';
    public static final int NUM_UPRIGHT = (int)'9';
    public static final int NUM_DOWNRIGHT = (int)'3';
    public static final int NUM_DOWNLEFT = (int)'1';

    public static final int ENTER = 10;
    public static final int ESCAPE = 27;
    public static final int BACKSPACE = 8;

    public final boolean[] pressed = new boolean[65536]; // There are 2^16 = 65536 possible chars.

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[Character.toLowerCase(e.getKeyChar())] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
       pressed[Character.toLowerCase(e.getKeyChar())] = true;
    }
}
