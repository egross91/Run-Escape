package org.escaperun.game.states.playing;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;

public class Playing extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private Stage stage;
    private int ticksSince = 0;

    public Playing(Stage stage) {
        this.stage = stage;
    }

    @Override
    public GameState update(boolean[] pressed) {

        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];
        Position avatarPos = stage.getAvatar().getPosition();
        int nextX = avatarPos.x;
        int nextY = avatarPos.y;
        if (up) nextX--;
        if (down) nextX++;
        if (left) nextY--;
        if (right) nextY++;


        if (ticksSince >= (stage.getAvatar().getOccupation().getMovement()*TICKS_PER_MOVEMENT)
                && (up || down || left || right)) {
            if (stage.moveAvatar(new Position(nextX, nextY))) {
                ticksSince = 0;
            }
        }
        ticksSince++;

        return null;
    }

    @Override
    public Decal[][] getRenderable() {
        return stage.getRenderable();
    }
}
