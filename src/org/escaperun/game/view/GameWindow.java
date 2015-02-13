package org.escaperun.game.view;

import org.escaperun.game.Game;
import org.escaperun.game.RunGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {

    public static final int ROWS = 50; // TODO
    public static final int COLUMNS = 100; // TODO

    private Game game;
    private ConsolePanel cp;

    public GameWindow(Game game, KeyListener keylistener) {
        this.game = game;
        this.cp = new ConsolePanel(ROWS, COLUMNS);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(keylistener);
        getContentPane().add(cp);
        pack();
        setVisible(true);
    }

    public void render() throws IllegalArgumentException {
        cp.clear();

        Decal[][] toRender = game.getRenderable();
        if (toRender != null) {
            for (int i = 0; i < Math.min(toRender.length, ROWS); i++) {
                for (int j = 0; j < Math.min(toRender[i].length, COLUMNS); j++) {
                    if (toRender[i][j] != null) {
                        Color back = toRender[i][j].background;
                        Color front = toRender[i][j].foreground;
                        char ch = toRender[i][j].ch;
                        cp.setBackgroundColor(back, i, j);
                        cp.setForegroundColor(front, i, j);
                        cp.setChar(ch, i, j);
                    }
                }
            }
        }
        cp.repaint();
    }
}
