package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.ItemSlot;

import java.util.*;

public class Statistics{

    protected Map<StatEnum, Integer> statsmap = new HashMap<StatEnum, Integer>();
    protected Map<StatEnum, Integer> currentstats = new HashMap<StatEnum, Integer>(); //ONLY USED FOR AVATAR/ENTITY FOR TEMPORARY STATS. Do not use for Items; instead, use statsmap var.

    //Constructor for Entity-related Statistics
    public Statistics(Occupation occupation, int numoflives) {
        this.initializeSM();//Initialize values of all possibilities.

        statsmap.put(StatEnum.STRENGTH, occupation.getStrength());//get initial STR stat from whatever occupation was
        statsmap.put(StatEnum.AGILITY, occupation.getAgility());//get initial AGI stat from whatever occupation was
        statsmap.put(StatEnum.INTELLECT, occupation.getIntelligence());//get initial INT stat from whatever occupation was
        statsmap.put(StatEnum.HARDINESS, occupation.getHardiness());//get initial HARD stat from whatever occupation was
        statsmap.put(StatEnum.MOVEMENT, occupation.getMovement());//get initial MOV stat from whatever occupation was
        statsmap.put(StatEnum.NUMOFLIVES, numoflives);//NumberOfLives is arg passed in (usu. 3 for avi, 1 for else)
        getLevel();//Derived level initially from started EXP (0).
        getOffensiveRate();//Calculate OR from the method provided initially.
        getDefensiveRate();//Calculate DR from the method provided initially.
        getArmorRate();//Calculate AR from the method provided initially.
        getMaxHP(); //Calculate HP from the method provided initially.

        currentstats.putAll(statsmap); //Initialize currentstats with a "copy" of our base stats.
    }

    //Constructor for weapon/armor-related Statistics... Utilizes a Map in order to find what stats it has.
    public Statistics(Map<StatEnum, Integer> itemstats) {
        this.initializeSM();//Initialize values of all possibilities.

        statsmap.putAll(itemstats); //Move those values from the item's generated Map to its Statistics object.
    }

    //Initializes the Map "statsmap" to have all zero values for the possible Enumeration types. Used in constructor methods.
    private void initializeSM(){
        statsmap.put(StatEnum.STRENGTH, 0);//get initial STR stat
        statsmap.put(StatEnum.AGILITY, 0);//get initial AGI stat
        statsmap.put(StatEnum.INTELLECT, 0);//get initial INT stat
        statsmap.put(StatEnum.HARDINESS, 0);//get initial HARD stat
        statsmap.put(StatEnum.MOVEMENT, 0);//get initial MOV stat
        statsmap.put(StatEnum.NUMOFLIVES, 0);//NumberOfLives
        statsmap.put(StatEnum.LEVEL, 0);//Derived level initially from started EXP (0).
        statsmap.put(StatEnum.EXP, 0);//EXP always starts at level zero (or one if we choose to do it that way).
//        statsmap.put(StatEnum.TEMPSTR, 0);//Temporary STR = 0 at start.
//        statsmap.put(StatEnum.TEMPAGI, 0);//Temporary AGI = 0 at start.
//        statsmap.put(StatEnum.TEMPINT, 0);//Temporary INT = 0 at start.
//        statsmap.put(StatEnum.TEMPMOV, 0);//Temporary MOV = 0 at start.
//        statsmap.put(StatEnum.TEMPHAR,0);//Temporary HAR = 0 at start.
        statsmap.put(StatEnum.MAXHP, 0);//store initial val of MaxHP
        statsmap.put(StatEnum.MAXMP, 0);//store initial val of MaxMP
        statsmap.put(StatEnum.CURRENTHP, 0);//store initial val of CurrentHP
        statsmap.put(StatEnum.CURRENTMP, 0);//store initial val of CurrentMP
        statsmap.put(StatEnum.OFFENSERATE, 0);//store initial val of OR
        statsmap.put(StatEnum.DEFENSERATE, 0);//store initial val of DR
        statsmap.put(StatEnum.ARMORRATE, 0);//store initial val of AR
    }

    protected void updateStats(Equipment equipment){
        currentstats.putAll(statsmap); //Reset our currentstats object to have default values.
        Collection<EquipableItem> equipitems = equipment.getEquipment().values();
        Iterator<EquipableItem> iterator = equipitems.iterator();
        while(iterator.hasNext()){
            addStats(iterator.next().getStats());
        }
    }

    //Boolean is for return from "isGameOver()" method. If game is over (num of lives is 0), returns true from it. else false
    protected boolean takeDamage(int damage){
        int newHP = (statsmap.get(StatEnum.CURRENTHP) - damage);
        if(newHP > 0){//If we're still alive.
            statsmap.put(StatEnum.CURRENTHP, newHP);
            return false;
        }
        else{
            statsmap.put(StatEnum.CURRENTHP, 0);
            return isGameOver();
        }
    }

    //Method that is (as of now) called only by takeDamage if our currentHP drops to or below zero.
    protected boolean isGameOver(){
        statsmap.put(StatEnum.NUMOFLIVES, statsmap.get(StatEnum.NUMOFLIVES)-1);
        if(statsmap.get(StatEnum.NUMOFLIVES) == 0)
            return true;
        else return false;
    }

    protected void healDamage(int healz){
        if(statsmap.get(StatEnum.CURRENTHP) + healz > statsmap.get(StatEnum.MAXHP))
            statsmap.put(StatEnum.CURRENTHP, statsmap.get(StatEnum.MAXHP));
        else statsmap.put(StatEnum.CURRENTHP, statsmap.get(StatEnum.CURRENTHP)+healz);
    }

    protected void useMana(int mana){
        //TODO: Implement MP-related methods
    }

    protected void addStats(Statistics itemstat) {
        Set<Map.Entry<StatEnum, Integer>> entries = itemstat.statsmap.entrySet();
        Iterator<Map.Entry<StatEnum, Integer>> iterator = entries.iterator();

        while(iterator.hasNext())
        {
            Map.Entry<StatEnum, Integer> entry = iterator.next();
            this.currentstats.put(entry.getKey(), (entry.getValue() + this.currentstats.get(entry.getKey())));
            //Above statement: Put the new value at a certain key got from the entry, the current value found in our statsmap + the new value found in the entry.
        }

    }

    protected void removeEquipStats(Statistics itemstat){
        Set<Map.Entry<StatEnum, Integer>> entries = itemstat.statsmap.entrySet();
        Iterator<Map.Entry<StatEnum, Integer>> iterator = entries.iterator();

        while(iterator.hasNext())
        {
            Map.Entry<StatEnum, Integer> entry = iterator.next();
            statsmap.put(entry.getKey(), (entry.getValue() - statsmap.get(entry.getKey())));
            //Above statement: Put the new value at a certain key got from the entry, the current value found in our statsmap + the new value found in the entry.
        }
    }


    //***************************
    //Jeff lets talk about combining these two methods to updateStats()
    // it will effectively just iterate through all gear and add what is there. we dont need to worry about subtracting ever

    protected int getLevel() {
        statsmap.put(StatEnum.LEVEL, 1 + (statsmap.get(StatEnum.EXP) / 10));//Simple EXP->LVL formula for now; will model it more later;
        //TODO: Put in special functionality if a check determines that calculated level and level stored are different, aka Avatar leveled.
        return statsmap.get(StatEnum.LEVEL); //Return the newly updated (if at all) value of level calculated.
    }

    protected int getOffensiveRate(){
        statsmap.put(StatEnum.OFFENSERATE, (statsmap.get(StatEnum.STRENGTH)+getLevel()));
        //Formula for OR: Strength + TempSTR + (derived) Level
        return statsmap.get(StatEnum.OFFENSERATE);
    }

    protected int getDefensiveRate(){
       statsmap.put(StatEnum.DEFENSERATE, (statsmap.get(StatEnum.AGILITY)+getLevel()));
        //Formula for DR: Agility + TempAGI + (derived) Level
        return statsmap.get(StatEnum.DEFENSERATE);
    }

    protected int getArmorRate(){
        statsmap.put(StatEnum.ARMORRATE, (statsmap.get(StatEnum.HARDINESS)));
        //Formula for AR: Hardiness + TempHAR
        return statsmap.get(StatEnum.ARMORRATE);
    }

    protected int getStat(StatEnum se){
        return statsmap.get(se);//Generic "getter" method for use with any StatEnum.
    }

    protected void setStat(StatEnum se, int valueofchange)
    {
        statsmap.put(se, valueofchange);
    }

}

