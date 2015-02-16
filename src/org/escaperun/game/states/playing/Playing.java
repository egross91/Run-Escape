package org.escaperun.game.states.playing;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.StatEnum;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.model.items.ItemSlot;
import org.escaperun.game.model.items.TakeableItem;
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

    private static int ix = 1;
    private static int iy = 0;

    private boolean equip = true;
    private int invIdx = 0;
    private static final Position[] INVENTORY_GRID = new Position[50];
    private static final Position[] EQUIPMENT_GRID = new Position[5];

    static {
        int x = 11; int y = 2;
        for (int i = 0; i < 50; i++) {
            INVENTORY_GRID[i] = new Position(x, y);
            y++;
            if (y >= 12) {
                y = 2;
                x++;
            }
        }

        x = 4;
        y = 27;
        for (int i = 0; i < 5; i++) {
            EQUIPMENT_GRID[i] = new Position(x, y);
            y++;
        }
    }

    public Playing(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() { return stage; }

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

    private void handleInvMovement(boolean[] pressed){
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];
        boolean left = pressed[Keyboard.LEFT];
        boolean right = pressed[Keyboard.RIGHT];
        boolean enter = pressed[Keyboard.ENTER];

           if (up && invMoveTicks >=10) {
               if (!equip) {
                   int nextIdx = invIdx - 11;
                   if (nextIdx < 0) {
                       equip = true;
                       invIdx = 0;
                   } else {
                       invIdx = nextIdx;
                   }
                   invMoveTicks = 0;
               }
            }
            if (down && invMoveTicks >=10) {
                if (equip) {
                    equip = false;
                    invIdx = 0;
                } else {
                    int nextIdx = invIdx + 11;
                    if (!(nextIdx >= 50)) {
                        invIdx = nextIdx;
                    }
                }
                invMoveTicks = 0;
            }
            if (left && invMoveTicks >=10) {
                int nextIdx = invIdx-1;
                if (nextIdx >= 0) {
                    invIdx = nextIdx;
                }
                invMoveTicks = 0;
            }
            if (right && invMoveTicks >=10) {

                int nextIdx = invIdx+1;
                if (equip) {
                    if (nextIdx <= 4) {
                        invIdx = nextIdx;
                    }
                } else {
                    if (nextIdx <= 49) {
                        invIdx = nextIdx;
                    }
                }
                invMoveTicks = 0;
            }
            if (enter && invMoveTicks >=10) {
                if (!equip) {
                    if (invIdx < stage.getAvatar().getInventory().getSize()) {
                        TakeableItem remo = stage.getAvatar().getInventory().remove(invIdx);
                        if (remo instanceof EquipableItem)
                            stage.getAvatar().equipItem((EquipableItem) remo);
                    }
                } else {
                    if (invIdx <= 4) {
                        stage.getAvatar().unequipItem(ItemSlot.values()[invIdx]);
                    }
                }


                //TODO
                //stage.getAvatar().getInventory().remove((ix-1)*10 + iy).doAction(stage.getAvatar());
                invMoveTicks = 0;
            }
            invMoveTicks++;
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


        if (ticksSince >= (TICKS_PER_MOVEMENT/stage.getAvatar().getStats().getStat(StatEnum.MOVEMENT))
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
                //column Barrier

                Item focused_item = null;
                if(yy == icolBarrier){
                    view[xx][yy] = new Decal('|', Color.BLACK, Color.WHITE);
                }
                // row barrier
                if(xx == irowBarrier && yy < icolBarrier){
                    view[xx][yy] = new Decal('_', Color.BLACK, Color.WHITE);
                }


                for (int i = 0; i < inventory.length; i++) {
                    Item item = null;
                    char ch = '-';
                    if (inventory[i] != null) {
                        ch = inventory[i].ch;
                        item = stage.getAvatar().getInventory().getItem(i);
                    }
                    if (!equip && i == invIdx) {
                        focused_item = item;
                        view[INVENTORY_GRID[i].x][INVENTORY_GRID[i].y] = new Decal(ch, Color.BLACK, Color.RED);
                    } else {
                        view[INVENTORY_GRID[i].x][INVENTORY_GRID[i].y] = new Decal(ch, Color.BLACK, Color.WHITE);
                    }
                }

                for (int i = 0; i < equi.length; i++) {
                    char ch = '-';
                    if (equi[i] != null) ch = equi[i].ch;
                    if (equip && i == invIdx) {
                        view[EQUIPMENT_GRID[i].x][EQUIPMENT_GRID[i].y] = new Decal(ch, Color.BLACK, Color.RED);
                    } else {
                        view[EQUIPMENT_GRID[i].x][EQUIPMENT_GRID[i].y] = new Decal(ch, Color.BLACK, Color.WHITE);
                    }
                }

                //Print "Inventory"
                if(xx == irowInv && yy == icolLeft){
                    for(int i = 0; i < inv.length(); i++){
                        view[xx][yy] = new Decal(inv.charAt(i), Color.BLACK, Color.WHITE);
                        yy++;
                    }
                }

                //Print "Equipment"
                if(xx == irowEq && yy == icolLeft){
                    for(int i = 0; i < eq.length(); i++){
                        view[xx][yy] = new Decal(eq.charAt(i), Color.BLACK , Color.WHITE);
                        yy++;
                    }
                }

                if ((focused_item != null) && (xx == irowInv-2 && yy == icolBarrier+2)) {
                    //write item details
                    String item_stats = "Item type: " + focused_item.getTypeToString();
                    for(int i = 0; i < item_stats.length(); i++){
                        view[xx][yy] = new Decal(item_stats.charAt(i), Color.BLACK, Color.WHITE);
                        yy++;
                    }
                    System.out.println(item_stats);
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