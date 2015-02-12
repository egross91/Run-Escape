package org.escaperun.game.model.entities;

import java.util.*;

/**
 * Created by Jeff on 2015/02/11 (011), 06:30.
 */
public class Statistics{

    protected Map<StatEnum, Integer> statsmap = new HashMap<StatEnum, Integer>();

    //Constructor for Entity-related Statistics
    public Statistics(Occupation occupation, int numoflives) {
        statsmap.put(StatEnum.STRENGTH, occupation.getStrength());//get initial STR stat from whatever occupation was
        statsmap.put(StatEnum.AGILITY, occupation.getAgility());//get initial AGI stat from whatever occupation was
        statsmap.put(StatEnum.INTELLECT, occupation.getIntelligence());//get initial INT stat from whatever occupation was
        statsmap.put(StatEnum.HARDINESS, occupation.getHardiness());//get initial HARD stat from whatever occupation was
        statsmap.put(StatEnum.MOVEMENT, occupation.getMovement());//get initial MOV stat from whatever occupation was
        statsmap.put(StatEnum.EXP, 0);//EXP always starts at level zero.
        statsmap.put(StatEnum.NUMOFLIVES, numoflives);//NumberOfLives is arg passed in (usu. 3 for avi, 1 for else)
        statsmap.put(StatEnum.TEMPSTR, 0);//Temporary STR = 0 at start.
        statsmap.put(StatEnum.TEMPAGI, 0);//Temporary AGI = 0 at start.
        statsmap.put(StatEnum.TEMPINT, 0);//Temporary INT = 0 at start.
        statsmap.put(StatEnum.TEMPMOV, 0);//Temporary MOV = 0 at start.
        statsmap.put(StatEnum.LEVEL, this.getLevel());//Derived level initially from started EXP (0).
    }

    //Constructor for weapon/armor-related Statistics
    public Statistics() {
        //TODO: Write this constructor method
    }


    protected void takeDamage(int damage){
        //TODO: Write takeDamage method
    }

    protected void healDamage(int damage){
        //TODO: Write healDamage method
    }

    protected int getLevel() {
        return statsmap.get(StatEnum.EXP)/10;//Simple EXP formula for now; will model it later.
    }

    protected int getHP(){
        //TODO: Make HP formula
        return 0;
    }

    protected int getMP(){
        //TODO: Make MP formula
        return 0;
    }

    protected int getOffensiveRate(){
        //TODO: Make OffensiveRate Formula
        return 0;
    }

    protected int getDefensiveRate(){
        //TODO: Make DefensiveRate Formula
        return 0;
    }

    protected int getArmorRate(){
        //TODO: Make ArmorRate Formula
        return 0;
    }

    protected void changeStat(StatEnum se, int valueofchange)
    {
        //TODO: Change formulas
    }
}

