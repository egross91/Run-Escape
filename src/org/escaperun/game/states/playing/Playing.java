package org.escaperun.game.states.playing;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.mainmenu.Exit;
import org.escaperun.game.states.pause.Pausing;
import org.escaperun.game.view.Decal;

import java.awt.*;

public class Playing extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private Stage stage;
    private int ticksSince = 0;

    private int invTicks = 500;
    private boolean invOpen = false;

    public Playing(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() { return stage; }

    @Override
    public GameState update(boolean[] pressed) {
        if (pressed[Keyboard.ESCAPE]) {
            return new Pausing(this);
        }

        if (pressed[Keyboard.INV]) {
            if (!invOpen && (invTicks >= 12)) {
                invOpen = true;
                invTicks = 0;
            } else if (invOpen && (invTicks >= 12)) {
                invOpen = false;
                invTicks = 0;
            }
        }

        if (!invOpen && handleMovement(pressed)) {

        }

        invTicks++;
        return null;
    }


    private boolean handleMovement(boolean[] pressed) {
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

        return true;
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
        //invCounter must stay in this method!
        int statusRow, row, column, testRows, invCounter = 0;

        Decal[][] gottenStage = stage.getRenderable();

        row = gottenStage.length;
        column = gottenStage[0].length;
        testRows = row;

        row = row + row%5;
        statusRow = row/5;

        Decal[][] view = new Decal[statusRow + testRows][column];  // new 2D array with space for both viewports

        String stats = "";
        String eq = "Equipment";
        String inv = "Inventory";
        int col0, col1, col2, icolBarrier, icolEquips, icolLeft, irowEq, irowInv,tempcol, irowBarrier = 0;
        int workcolumn = column - column%4;
        irowEq = 2;
        irowInv = 9;
        icolLeft = 2;
        irowBarrier = 6;
        tempcol = column +column%3;
        icolBarrier = 2 * (tempcol/3);
        icolEquips =((tempcol / 3) -1 );

        col0 = workcolumn/4;
        col1 = 2*col0;
        col2 = 3*col0;

        if(invOpen){
         //here is where we would display an inventory, IF WE HAD ANY
         //but I digress
         //the rest of the border is '-'
        Decal[] equi = stage.getAvatar().getEquipment().getEquipDecals();
        Decal[] inventory = stage.getAvatar().getInventory().getInventoryDecal();

         for(int xx = 0; xx < (testRows - statusRow); xx++){
            for(int yy = 0; yy < column; yy++){
                //collumn Barrier
                if(yy == icolBarrier){
                    view[xx][yy] = new Decal('|', Color.BLACK, Color.WHITE);
                }
                // row barrier
                if(xx == irowBarrier && yy < icolBarrier){
                    view[xx][yy] = new Decal('_', Color.BLACK, Color.WHITE);
                }
                //Print "Equipment"
                if(xx == irowEq && yy == icolLeft){
                    for(int i = 0; i < eq.length(); i++){
                        view[xx][yy] = new Decal(eq.charAt(i), Color.BLACK , Color.WHITE);
                        yy++;
                    }
                }
                //Print Actual Equips
                if((xx == (irowEq + 2) && yy == icolEquips)){
                    for(int i = 0; i < equi.length; i++){
                        view[xx][yy] = equi[i];
                        yy++;
                    }
                }
                //Print "Inventory"
                if(xx == irowInv && yy == icolLeft){
                    for(int i = 0; i < inv.length(); i++){
                        view[xx][yy] = new Decal(inv.charAt(i), Color.BLACK, Color.WHITE);
                        yy++;
                    }
                }

                //Print Actual Inv may change icolLeft
                if((xx >= irowInv + 2) && (yy== icolLeft) && (invCounter < 50)){
                    for(int i = 0; (i < 10); i++){
                        view[xx][yy] = inventory[invCounter];
                        invCounter++;
                        yy++;
                    }
                }


            }
         }

//         Decal inventory_edge_horizontal = new Decal('-', Color.BLACK, Color.WHITE);
//         Decal inventory_edge_vertical = new Decal('|', Color.BLACK, Color.WHITE);
//         for(int x = (testRows-statusRow); x < testRows; ++x){
//             for(int y = 0; y<column; ++y){
//                 if ((x == testRows-statusRow) || (x== testRows -5)) {
//                     view[x][y] = inventory_edge_horizontal;
//                 }
//                 else if ((y == 0) || (y == column-1)) {
//                     view[x][y] = inventory_edge_vertical;
//                 }
//                 else
//                     view[x][y] = new Decal('I', Color.BLACK, Color.GREEN);
//                }
//            }
//         //the edges of the window are gonna be '+'
//         Decal inventory_corner = new Decal('+', Color.BLACK, Color.WHITE);
//         view[testRows-statusRow][0] = inventory_corner;
//         view[testRows-statusRow][column-1] = inventory_corner;
//         view[testRows-5][0] = inventory_corner;
//         view[testRows-5][column-1] = inventory_corner;
//
         //the game window is cutoff on mine screen, so the bottom rows may need to be adjusted
        }


        //else show status viewport
        else{
            //this displays the map
            for(int xx = 0; xx < (testRows - statusRow); xx++){
                for(int yy = 0; yy < column; yy++){
                    view[xx][yy] = gottenStage[xx][yy];
                }
            }
        }

        for(int x3 = (testRows-statusRow); x3 < testRows; x3++){
            for(int y3 = 0; y3<column; y3++){
                if(x3 == (testRows-statusRow)){
                    if(y3 == (col0 - 5)){ //print Level
                        stats = stage.getAvatar().getStats().leveltoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col1 - 6)){ //print health
                        stats = stage.getAvatar().getStats().healthtoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col2 - 8)){ // print strength
                        stats = stage.getAvatar().getStats().strengthtoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else{
                        view[x3][y3] = null;
                    }
                }
                else if(x3 == (testRows - statusRow + 1)){
                    if(y3 == (col0 - 5)){ //print Lives
                        stats = stage.getAvatar().getStats().livestoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col1 - 4)){ //print Mana
                        stats = stage.getAvatar().getStats().manatoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col2 - 7)){ // print agility
                        stats = stage.getAvatar().getStats().agilitytoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else{
                        view[x3][y3] = null;
                    }
                }
                else if(x3 == (testRows - statusRow + 2)){
                    if(y3 == (col0 - 3)){ //print EXP
                        stats = stage.getAvatar().getStats().exptoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col1 - 7)){ //print Offense
                        stats = stage.getAvatar().getStats().offensetoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col2 - 9)){ // print Intellect
                        stats = stage.getAvatar().getStats().intellecttoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else{
                        view[x3][y3] = null;
                    }
                }
                else if(x3 == (testRows - statusRow + 3)){
                    if(y3 == (col1 - 7)){ //print Defense
                        stats = stage.getAvatar().getStats().defensetoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col2 - 9)){ // print hardiness
                        stats = stage.getAvatar().getStats().hardinesstoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else{
                        view[x3][y3] = null;
                    }
                }
                else if(x3 == (testRows - statusRow + 4)){
                    if(y3 == (col1 - 6)){ //print Armour
                        stats = stage.getAvatar().getStats().armourtoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else if(y3 == (col2 - 8)){ // print Movement
                        stats = stage.getAvatar().getStats().movementtoString();
                        for(int i = 0; i <stats.length(); i++){
                            view[x3][y3] = new Decal(stats.charAt(i), Color.BLACK, Color.WHITE);
                            y3++;
                        }
                    }
                    else{
                        view[x3][y3] = null;
                    }
                }
                else {
                    view[x3][y3] = null;
                }
                //view[x3][y3] = new Decal('%', Color.LIGHT_GRAY, Color.blue);
            }
        }

        return view;
    }

}