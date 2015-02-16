package org.escaperun.game;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    // change when we decide to actually deploy game
    // I (josh) can't use numpad (laptop) so these are really convenient for me
    /*
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';
    public static final char BLANK = 'b';
    public static final char GRASS = 'g';
    public static final char INV = 'i';
    public static final char WATER = 'h';
    */

    public static final int UP = KeyEvent.VK_W;
    public static final int DOWN = KeyEvent.VK_S;
    public static final int LEFT = KeyEvent.VK_A;
    public static final int RIGHT = KeyEvent.VK_D;
    public static final int BLANK = KeyEvent.VK_B;
    public static final int GRASS = KeyEvent.VK_G;
    public static final int INV = KeyEvent.VK_I;
    public static final int WATER = KeyEvent.VK_H;

    //numpad
    public static final int NUM_DOWN = KeyEvent.VK_NUMPAD2;
    public static final int NUM_UP = KeyEvent.VK_NUMPAD8;
    public static final int NUM_LEFT = KeyEvent.VK_NUMPAD4;
    public static final int NUM_RIGHT = KeyEvent.VK_NUMPAD6;
    public static final int NUM_UPLEFT = KeyEvent.VK_NUMPAD7;
    public static final int NUM_UPRIGHT = KeyEvent.VK_NUMPAD9;
    public static final int NUM_DOWNRIGHT = KeyEvent.VK_NUMPAD3;
    public static final int NUM_DOWNLEFT = KeyEvent.VK_NUMPAD1;

    /*
    public static final char ENTER = (char) 10;
    public static final char ESCAPE = (char) 27;
    public static final char BACKSPACE = (char) 8;
    */

    public static final int ENTER = KeyEvent.VK_ENTER;
    public static final int ESCAPE = KeyEvent.VK_ESCAPE;
    public static final int BACKSPACE = KeyEvent.VK_BACK_SPACE;

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
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //pressedActual[Character.toLowerCase(e.getKeyChar())] = true;
       // pressedActual[e.getKeyCode()] = true;
        pressed[e.getKeyCode()] = true;
    }
}
