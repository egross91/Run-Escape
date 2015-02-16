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
import java.security.Key;

public class Playing extends GameState {

    public static final int TICKS_PER_MOVEMENT = 10;

    private Stage stage;
    private int ticksSince = 0;

    private int invTicks = 500;
    private int invMoveTicks = 30;
    private boolean invOpen= false;
    private static boolean[][] invAllowedMoves = new boolean[6][10];
    static{assigninvMoves();}
    private static int ix = 1;
    private static int iy = 0;

    public Playing(Stage stage) {
        this.stage = stage;
    }


    public Stage getStage() { return stage; }

    public static void assigninvMoves(){

        invAllowedMoves[0][0] = true;
        invAllowedMoves[0][1] = true;
        invAllowedMoves[0][2] = true;
        invAllowedMoves[0][3] = true;
        invAllowedMoves[0][4] = true;
        invAllowedMoves[0][5] = false;
        invAllowedMoves[0][6] = false;
        invAllowedMoves[0][7] = false;
        invAllowedMoves[0][8] = false;
        invAllowedMoves[0][9] = false;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                invAllowedMoves[i][j] = true;
            }
        }

    }

    @Override
    public GameState update(boolean[] pressed) {
        if (pressed[Keyboard.ESCAPE]) {
            pressed[Keyboard.ESCAPE] = false;
            return new Pausing(this);
        }

        if (pressed[Keyboard.INV]) {
            if (!invOpen && (invTicks >= 10)) {
                invOpen = true;
                invTicks = 0;
            } else if (invOpen && (invTicks >= 10)) {
                invOpen = false;
                invTicks = 0;
            }
        }


        if (!invOpen) {
            handleMovement(pressed);
        }

        else if(invOpen){
            handleInvMovement(pressed);
        }
        invMoveTicks++;
        invTicks++;
        return null;
    }

    private boolean handleInvMovement(boolean[] pressed){
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];
        boolean enter = pressed[Keyboard.ENTER];

            if (up && invMoveTicks >=45) {
                if ((ix - 1) >= 0 && invAllowedMoves[ix - 1][iy]) {
                    ix = ix - 1;
                    iy = iy;
                    invMoveTicks= 0;
                }
            }
            invMoveTicks++;
            if (down && invMoveTicks >=45) {
                if ((ix + 1) <= 5 && invAllowedMoves[ix + 1][iy]) {
                    ix = ix + 1;
                    iy = iy;
                    invMoveTicks= 0;
                }

            }
            invMoveTicks++;
            if (left && invMoveTicks >=45) {
                if ((iy - 1) >= 0 && invAllowedMoves[ix][iy - 1]) {
                    ix = ix;
                    iy = iy - 1;
                    invMoveTicks = 0;
                }
            }
            invMoveTicks++;
            if (right && invMoveTicks >=45) {
                if ((iy + 1) <= 9 && invAllowedMoves[ix][iy + 1]) {
                    ix = ix;
                    iy = iy + 1;
                    invMoveTicks = 0;
                }
            }
            invMoveTicks++;
            if (enter && invMoveTicks >=45) {
                stage.getAvatar().getInventory().remove((ix-1)*10 + iy).doAction(stage.getAvatar());
                invMoveTicks = 0;
            }
            invMoveTicks++;
        return false;
    }

    private void handleMovement(boolean[] pressed) {
        if(stage.getGameOver())
            return;

        boolean up = pressed[Keyboard.UP] || pressed[Keyboard.NUM_UP];
        boolean down = pressed[Keyboard.DOWN] || pressed[Keyboard.NUM_DOWN];
        boolean left = pressed[Keyboard.LEFT] || pressed[Keyboard.NUM_LEFT];
        boolean right = pressed[Keyboard.RIGHT] || pressed[Keyboard.NUM_RIGHT];
        boolean upright = pressed[Keyboard.NUM_UPRIGHT];
        boolean upleft = pressed[Keyboard.NUM_UPLEFT];
        boolean downright = pressed[Keyboard.NUM_DOWNRIGHT];
        boolean downleft = pressed[Keyboard.NUM_DOWNLEFT];


        if (ticksSince >= (stage.getAvatar().getOccupation().getMovement() * TICKS_PER_MOVEMENT)
                && (up || down || left || right || upright || upleft || downright || downleft))
        {
            boolean moved = false;

            if (up)
                moved |= tryMove(-1, 0);
            if (down)
                moved |= tryMove(1, 0);
            if (left)
                moved |= tryMove(0, -1);
            if (right)
                moved |= tryMove(0, 1);
            if (upright)
                moved |= tryMove(-1, 1);
            if (upleft)
                moved |= tryMove(-1,-1);
            if (downleft)
                moved |= tryMove(1,-1);
            if (downright)
                moved |= tryMove(1,1);
            if (moved)
                ticksSince = 0;
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
        //invCounter must stay in this method!
        int statusRow, row, column, testRows, invCounter = 0;

        Decal[][] gottenStage = stage.getRenderable();

        row = gottenStage.length;
        column = gottenStage[0].length;
        testRows = row;
        boolean copy = true;
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

        Decal[] equi = stage.getAvatar().getEquipment().getEquipDecals();
        Decal[] inventory = stage.getAvatar().getInventory().getInventoryDecal();

//            for(int i =0; i < 5; i++){
//                System.out.println(stage.getAvatar().getEquipment().getEquipment().get(i));
//            }

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
                        if(xx == (ix + 4) && (yy == (iy +27))){
                            char fChar = equi[i].ch;
                            Decal focused = new Decal(fChar, Color.BLACK, Color.RED);
                            view[xx][yy] = focused;
                        }else {
                            view[xx][yy] = equi[i];
                            yy++;
                        }
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
                if((xx >= (irowInv + 2)) && (yy>= icolLeft) && (invCounter < 50) && copy){
                    for(int i = 0; i < 10; i++) {
                        if(((ix +10) == xx) && ((iy +2) == yy) && !(inventory[invCounter] == null)){
                            char fChar = inventory[invCounter].ch;
                            Decal focused = new Decal(fChar, Color.BLACK, Color.RED);
                            view[xx][yy] = focused;
                        }else {
                            view[xx][yy] = inventory[invCounter];
                        }
                        invCounter++;
                        yy++;
                    }
                    copy = false;
                }


            }
             copy = true;   // keeps
         }
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