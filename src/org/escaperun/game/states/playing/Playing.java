package org.escaperun.game.states.playing;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.states.GameState;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class Playing extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private Stage stage;
    private int ticksSince = 0;

    public Playing(Stage stage) {
        this.stage = stage;
    }

    @Override
    public GameState update(boolean[] pressed) {
        handleMovement(pressed);
        return null;
    }

    private void handleMovement(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];


        if (ticksSince >= (stage.getAvatar().getOccupation().getMovement()*TICKS_PER_MOVEMENT)
                && (up || down || left || right)) {
            boolean moved = false;

            if (up) {
                moved |= tryMove(-1, 0);
            }
            if (down) {
                moved |= tryMove(1, 0);
            }
            if (left) {
                moved |= tryMove(0, -1);
            }
            if (right) {
                moved |= tryMove(0, 1);
            }

            if (moved) {
                ticksSince = 0;
            }
        }
        ticksSince++;
    }

    private boolean tryMove(int dx, int dy) {
        int nextX = stage.getAvatar().getPosition().x + dx;
        int nextY = stage.getAvatar().getPosition().y + dy;
        return stage.moveAvatar(new Position(nextX, nextY));
    }

    @Override
    public Decal[][] getRenderable() {
        Decal[][] view = createView();

        return view;
    }


    private Decal[][] createView(){
        int statusRow, row, collumn, testRows = 0;

        Decal[][] gottenStage = stage.getRenderable();

        row = gottenStage.length;
        collumn = gottenStage[0].length;
        testRows = row;

        row = row + row%5;
        statusRow = row/5;

        Decal[][] view = new Decal[statusRow + testRows][collumn];  // new 2D array with space for both viewports

        //if(stage.avatar.inv open)
        if(false){
            for(int x = 0; x < row; x++){

                for(int y = 0; y < collumn; y++){

                }
            }
        }
        //else show status viewport
        else{
            String stats = this.stage.getAvatar().getStats().toString();
            int col0, col1, col2,row0,row1,row2,row3,row4 = 0;
            col0 = collumn/4;
            col1 = 2*col0;
            col2 = 3*col0;


            for(int xx = 0; xx < (testRows - statusRow); xx++){
                for(int yy = 0; yy < collumn; yy++){
                    view[xx][yy] = gottenStage[xx][yy];
                }
            }
            for(int x3 = (testRows-statusRow); x3 < testRows; x3++){
                for(int y3 = 0; y3<collumn; y3++){
                    view[x3][y3] = new Decal('&', Color.RED, Color.BLACK);
                }
            }
        }

        return view;
    }

}
