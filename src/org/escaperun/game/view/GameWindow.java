package org.escaperun.game.view;

import org.escaperun.game.model.Game;

import javax.swing.*;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {

    public static final int ROWS = 50; // TODO
    public static final int COLUMNS = 100; // TODO

    private Game game;
    private ConsolePanel cp;

    public GameWindow(Game game, KeyListener keylistener) {
        this.game = game;
        this.cp = new ConsolePanel(ROWS, COLUMNS);
        addKeyListener(keylistener);
        getContentPane().add(cp);
        pack();
        setVisible(true);
    }

    public void render() throws IllegalArgumentException {
        cp.clear();

        // TODO: update char grid

        cp.repaint();
    }
}
