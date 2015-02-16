package org.escaperun.game.states.mapcreation;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.tile.Grass;
import org.escaperun.game.model.tile.Water;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.mainmenu.Exit;
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
        if (!exitGame(pressed))
            return null;
        return new Exit();
    }

    private boolean exitGame(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];
        boolean blankTile = pressed[Keyboard.BLANK];
        boolean grass = pressed[Keyboard.GRASS];
        boolean water = pressed[Keyboard.WATER];
        boolean esc = pressed[Keyboard.ESCAPE];
        Position avatarPos = stage.getAvatar().getPosition();
        int nextX = avatarPos.x;
        int nextY = avatarPos.y;
        if(grass){
            stage.map[nextX][nextY].setTerrain(new Grass());
        }
        if(water) {
            stage.map[nextX][nextY].setTerrain(new Water());
        }
        if(blankTile){
            stage.map[nextX][nextY].setTerrain(null);
            stage.map[nextX][nextY + 1].setTerrain(null);
            stage.map[nextX][nextY - 1].setTerrain(null);
            stage.map[nextX + 1][nextY].setTerrain(null);
            stage.map[nextX - 1][nextY].setTerrain(null);

        }
        if (up) nextX--;
        if (down) nextX++;
        if (left) nextY--;
        if (right) nextY++;

        if(esc){
            Creation.saveManager.saveCurrentGame(stage, stage.getAvatar(), "Adam");
            pressed[Keyboard.ESCAPE] = false;
            return true;
        }


        if (ticksSince >= (stage.getAvatar().getOccupation().getMovement()*TICKS_PER_MOVEMENT)
                && (up || down || left || right)) {
            if (stage.moveAvatar(new Position(nextX, nextY))) {
                ticksSince = 0;
            }
        }
        ticksSince++;

        return false;
    }

    @Override
    public Decal[][] getRenderable() {
        return stage.getRenderable();
    }
}


