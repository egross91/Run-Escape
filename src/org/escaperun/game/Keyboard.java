package org.escaperun.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    // change when we decide to actually deploy game
    // I (josh) can't use numpad (laptop) so these are really convenient for me
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';
    public static final char BLANK = 'b';
    public static final char GRASS = 'g';
    public static final char INV = 'i';
    public static final char ENTER = (char) 10;
    public static final char ESCAPE = (char) 27;

    public final boolean[] pressed = new boolean[65536]; // There are 2^16 = 65536 possible chars.

    @Override
    public void keyTyped(KeyEvent e) {
        pressed[Character.toLowerCase(e.getKeyChar())] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[Character.toLowerCase(e.getKeyChar())] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) { }
}
