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
        int statusRow, row, column, testRows = 0;

        Decal[][] gottenStage = stage.getRenderable();

        row = gottenStage.length;
        column = gottenStage[0].length;
        testRows = row;

        row = row + row%5;
        statusRow = row/5;

        Decal[][] view = new Decal[statusRow + testRows][column];  // new 2D array with space for both viewports

        if(false){
            for(int x = 0; x < row; x++){

                for(int y = 0; y < column; y++){

                }
            }
        }
        /*else if(stage.avatar.inv open) {
            //here is where we would display an inventory, IF WE HAD ANY
            //my thoughts are that there is going to be redundant code here & in the status view
            //but I digress

            //the edges of the window are gonna be '+'
            Decal inventory_corner = new Decal('+', Color.GREEN, Color.BLACK);
            view[testRows-statusRow][0] = inventory_corner;
            view[testRows-statusRow][column-1] = inventory_corner;
            view[testRows-1][0] = inventory_corner;
            view[testRows-1][column-1] = inventory_corner;

            //the rest of the border is '-'
            Decal inventory_edge = new Decal('-', Color.GREEN, Color.BLACK);
            for(int x = (testRows-statusRow); x < testRows; ++x){
                for(int y = 0; y<column; ++y){
                    if ((x == testRows-statusRow) && (y != 0) && (y != column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else if ((x == testRows-1) && (y != 0) && (y != column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else if ((y == 0) || (y == column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else
                        view[x][y] = new Decal(';', Color.BLACK, Color.GREEN);
                }
            }
        }*/
        //else show status viewport
        else{
            String stats = this.stage.getAvatar().getStats().toString();
            int col0, col1, col2,row0,row1,row2,row3,row4 = 0;
            col0 = column/4;
            col1 = 2*col0;
            col2 = 3*col0;


            for(int xx = 0; xx < (testRows - statusRow); xx++){ //prob gonna display this stuff no matter what
                for(int yy = 0; yy < column; yy++){
                    view[xx][yy] = gottenStage[xx][yy];
                }
            }
            Decal inventory_corner = new Decal('+', Color.GREEN, Color.BLACK);
            view[testRows-statusRow][0] = inventory_corner;
            view[testRows-statusRow][column-1] = inventory_corner;
            view[testRows-1][0] = inventory_corner;
            view[testRows-1][column-1] = inventory_corner;

            //the rest of the border is '-'
            Decal inventory_edge = new Decal('-', Color.GREEN, Color.BLACK);
            for(int x = (testRows-statusRow); x < testRows; ++x){
                for(int y = 0; y<column; ++y){
                    if ((x == testRows-statusRow) && (y != 0) && (y != column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else if ((x == testRows-1) && (y != 0) && (y != column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else if ((y == 0) || (y == column-1)) {
                        view[x][y] = inventory_edge;
                    }
                    else
                        view[x][y] = new Decal(';', Color.BLACK, Color.GREEN);
                }
            }
        }

        return view;
    }

}
