package org.escaperun.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    public final boolean[] pressed = new boolean[65536]; // There are 2^16 = 65536 possible chars.

    @Override
    public void keyTyped(KeyEvent e) {
        pressed[e.getKeyChar()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyChar()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) { }
}
