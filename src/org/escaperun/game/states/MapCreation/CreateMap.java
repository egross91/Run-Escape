package org.escaperun.game.states.MapCreation;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.tile.Grass;
import org.escaperun.game.model.tile.Tile;
import org.escaperun.game.model.tile.Water;
import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;

public class CreateMap extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private Stage stage;
    private int ticksSince = 0;

    public CreateMap(Stage stage) {
        this.stage = stage;
    }

    @Override
    public GameState update(boolean[] pressed) {

        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];
        boolean blankTile = pressed[Keyboard.BLANK];
        boolean grass = pressed[Keyboard.GRASS];
        Position avatarPos = stage.getAvatar().getPosition();
        int nextX = avatarPos.x;
        int nextY = avatarPos.y;
        if(grass){
            stage.map[nextX][nextY] = new Tile(new Grass(), null, null);
        }
        if(blankTile){
            stage.map[nextX][nextY] = new Tile();
        }
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


