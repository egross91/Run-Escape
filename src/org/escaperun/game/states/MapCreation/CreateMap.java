package org.escaperun.game.states.mapcreation;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.StatEnum;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.model.items.InteractiveItem;
import org.escaperun.game.model.items.ObstacleItem;
import org.escaperun.game.model.items.OneShotItem;
import org.escaperun.game.model.items.UsableItem;
import org.escaperun.game.model.tile.*;
import org.escaperun.game.serialization.SaveManager;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.mainmenu.Creation;
import org.escaperun.game.states.mainmenu.Exit;
import org.escaperun.game.view.Decal;

import java.awt.*;
import java.util.HashMap;

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
        boolean up = pressed[Keyboard.UP] || pressed[Keyboard.NUM_UP];
        boolean down = pressed[Keyboard.DOWN] || pressed[Keyboard.NUM_DOWN];
        boolean left = pressed[Keyboard.LEFT] || pressed[Keyboard.NUM_LEFT];
        boolean right = pressed[Keyboard.RIGHT] || pressed[Keyboard.NUM_RIGHT];
        boolean blankTile = pressed[Keyboard.BLANK];
        boolean grass = pressed[Keyboard.GRASS];
        boolean water = pressed[Keyboard.WATER];
        boolean esc = pressed[Keyboard.ESCAPE];

        boolean mountain = pressed[Keyboard.MOUNTAIN];
        boolean heal = pressed[Keyboard.HEAL_DAMAGE];
        boolean takeDamage = pressed[Keyboard.TAKE_DAMAGE];
        boolean levelUp = pressed[Keyboard.LEVEL_UP];
        boolean instDeath = pressed[Keyboard.INST_DEATH];
        boolean oneShot = pressed[Keyboard.ONE_SHOT];
        boolean usableItem = pressed[Keyboard.USABLE_ITEM];
        boolean obstacle = pressed[Keyboard.OBSTACLE];
        boolean interactiveItem = pressed[Keyboard.INTERACTIVE_ITEM];

        Position avatarPos = stage.getAvatar().getPosition();
        int nextX = avatarPos.x;
        int nextY = avatarPos.y;

        if(grass){ stage.map[nextX][nextY].setTerrain(new Grass());}
        if(water) { System.out.println("WATER");stage.map[nextX][nextY].setTerrain(new Water()); }
        if(mountain){ stage.map[nextX][nextY].setTerrain(new Mountain());}

        if(heal){ stage.map[nextX][nextY].setAreaEffect(new HealDamage(2)); }
        if(takeDamage){ stage.map[nextX][nextY].setAreaEffect(new TakeDamage(2)); }
        if(levelUp){ stage.map[nextX][nextY].setAreaEffect(new LevelUp()); }
        if(instDeath){ stage.map[nextX][nextY].setAreaEffect(new InstantDeath()); }

        if(oneShot){ stage.map[nextX][nextY].setItem(new OneShotItem(new Decal('?', Color.BLACK, Color.CYAN),new Statistics(getItemStatistics()))); }
        if(usableItem){ stage.map[nextX][nextY].setItem(new UsableItem(new Decal('?', Color.BLACK,Color.YELLOW ), new Statistics(getItemStatistics()))); }
        if(obstacle){ stage.map[nextX][nextY].setItem(new ObstacleItem(new Decal('B', Color.BLACK, Color.DARK_GRAY))); }
        if(interactiveItem){ stage.map[nextX][nextY].setItem(new InteractiveItem(new Decal('?', Color.RED, Color.BLUE), new Statistics(getItemStatistics()))); }


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
            SaveManager.saveCreatedMap(stage);
            pressed[Keyboard.ESCAPE] = false;
            return true;
        }

        if (ticksSince >= (TICKS_PER_MOVEMENT/stage.getAvatar().getStats().getStat(StatEnum.MOVEMENT))
                && (up || down || left || right))
        {
            if (stage.moveAvatar(new Position(nextX, nextY))) {
                ticksSince = 0;
            }
        }
        ticksSince++;

        return false;
    }
    private HashMap<StatEnum, Integer> getItemStatistics(){
        HashMap<StatEnum, Integer> statsmap = new HashMap<StatEnum, Integer>();

        statsmap.put(StatEnum.STRENGTH, 1);//set the STR contents
        statsmap.put(StatEnum.INTELLECT, 5);//set the INT stat
        statsmap.put(StatEnum.HARDINESS, 5);//set HARD stat
        statsmap.put(StatEnum.MOVEMENT, 5);//set MOV stat
        statsmap.put(StatEnum.EXP, 0);// Give no EXP for this item
        statsmap.put(StatEnum.MAXHP, 50);//set val of MaxHP
        statsmap.put(StatEnum.MAXMP, 50);//set val of MaxMP
        statsmap.put(StatEnum.OFFENSERATE, 3);//set val of OR
        statsmap.put(StatEnum.ARMORRATE, 2);//set val of AR

        return statsmap;
    }

    @Override
    public Decal[][] getRenderable() {
        return stage.getRenderable();
    }
}


