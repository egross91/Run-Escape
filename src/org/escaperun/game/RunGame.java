package org.escaperun.game;

import org.escaperun.game.view.GameWindow;

public class RunGame implements Runnable {

    public static void main(String[] args) {
        RunGame rg = new RunGame();
        rg.run();
    }


    public static final double SECONDS_PER_TICK = 1/60.0; // goal is 60 fps...each 'frame' should take 1/60th of a second
    private Keyboard keyboard;
    private Game game;
    private GameWindow window;

    public RunGame() {
        keyboard = new Keyboard();
        game = new Game();
        window = new GameWindow(game, keyboard);
    }

    public void run() {
        boolean running = true;
        long last = System.currentTimeMillis(); // time from last tick
        double unprocessed = 0.0; // unprocessed time
        while (running) {
            long current = System.currentTimeMillis();
            long elapsed = current-last; // time between last

            last = current;
            unprocessed += elapsed/1000.0;

            boolean update = false;
            while (unprocessed >= SECONDS_PER_TICK) {
                game.update(keyboard.pressed);
                if (game.isOver()) {
                    window.dispose();
                    return;
                }
                unprocessed -= SECONDS_PER_TICK;
                update = true;
            }
            if (update) {
                window.render();
            } else {
                try {
                    // If we didn't do any work let the CPU catch
                    // up on some cycles... or something
                    Thread.sleep(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    running = false;
                }
            }
        }
    }
}
