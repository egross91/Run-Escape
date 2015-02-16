package org.escaperun.game;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    // change when we decide to actually deploy game
    // I (josh) can't use numpad (laptop) so these are really convenient for me

    public static final int UP = 'w';
    public static final int DOWN = 's';
    public static final int LEFT = 'a';
    public static final int RIGHT = 'd';
    public static final int BLANK = 'b';
    public static final int GRASS = 'g';
    public static final int INV = 'i';

    public static final int WATER = (int) 'y';
    public static final char MOUNTAIN = 'm';
    public static final char INST_DEATH = 'p';
    public static final char OBSTACLE = 'o';
    public static final char USABLE_ITEM = 'u';
    public static final char ONE_SHOT = 'k';
    public static final char INTERACTIVE_ITEM = 'j';
    public static final char LEVEL_UP = 'l';
    public static final char TAKE_DAMAGE = 't';
    public static final char HEAL_DAMAGE = 'h';
    public static final char ENTER = (char) 10;
    public static final char ESCAPE = (char) 27;
    public static final char BACKSPACE = (char) 8;

    public final boolean[] pressed = new boolean[65536]; // There are 2^16 = 65536 possible chars.
    public final boolean[] pressedActual= new boolean[65536];

    @Override
    public void keyTyped(KeyEvent e) {
        //pressed[Character.toLowerCase(e.getKeyChar())] = true;
       // pressed[e.getKeyCode()] = true;
       // pressedActual[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //pressed[Character.toLowerCase(e.getKeyChar())] = false;
        pressed[(int)e.getKeyChar()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //pressedActual[Character.toLowerCase(e.getKeyChar())] = true;
       // pressedActual[e.getKeyCode()] = true;
        //System.out.println((int) e.getKeyChar());
        pressed[(int)e.getKeyChar()] = true;
    }
}
