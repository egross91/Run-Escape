package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.serialization.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

public class Statistics implements Savable {

    protected Map<StatEnum, Integer> statsmap = new HashMap<StatEnum, Integer>();
    protected Map<StatEnum, Integer> currentstats = new HashMap<StatEnum, Integer>(); //ONLY USED FOR AVATAR/ENTITY FOR TEMPORARY STATS. Do not use for Items; instead, use statsmap var.
    private Map<StatEnum, Integer> hpmpmap = new HashMap<StatEnum, Integer>(5); //ONLY USED FOR DERIVED STAT BOOSTS

    //Constructor for Entity-related Statistics
    public Statistics(Occupation occupation, int numoflives) {


        initializeSM();//Initialize values of all possibilities.

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
        getMaxMP(); //Calculate MP from the method provided intiially.
        currentstats.putAll(statsmap); //Initialize currentstats with a "copy" of our base stats.

        currentstats.put(StatEnum.CURRENTHP, statsmap.get(StatEnum.MAXHP)); //Current HP = Max HP
        currentstats.put(StatEnum.CURRENTMP, statsmap.get(StatEnum.MAXMP)); //Current MP = Max MP
    }

    //Constructor for weapon/armor-related Statistics... Utilizes a Map in order to find what stats it has.
    public Statistics(Map<StatEnum, Integer> itemstats) {
        this.initializeSM();//Initialize values of all possibilities.

        statsmap.putAll(itemstats); //Move those values from the item's generated Map to its Statistics object.
    }

    public Statistics(){
        this.initializeSM();//Intiailize all things (except currentHP/MP) to Zero.
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
        statsmap.put(StatEnum.MAXHP, 0);//store initial val of MaxHP
        statsmap.put(StatEnum.MAXMP, 0);//store initial val of MaxMP
//        statsmap.put(StatEnum.CURRENTHP, 0);//store initial val of CurrentHP // Don't want current values to plague currentstats in updateStats() (e.g., rewrite to MaxHP or 0 val)
//        statsmap.put(StatEnum.CURRENTMP, 0);//store initial val of CurrentMP // Don't want current values to plague currentstats in updateStats() (e.g., rewrite to MaxHP or 0 val)
        statsmap.put(StatEnum.OFFENSERATE, 0);//store initial val of OR
        statsmap.put(StatEnum.DEFENSERATE, 0);//store initial val of DR
        statsmap.put(StatEnum.ARMORRATE, 0);//store initial val of AR

        hpmpmap.put(StatEnum.MAXHP, 0);
        hpmpmap.put(StatEnum.MAXMP, 0);
        hpmpmap.put(StatEnum.OFFENSERATE, 0);
        hpmpmap.put(StatEnum.DEFENSERATE, 0);
        hpmpmap.put(StatEnum.ARMORRATE, 0);
    }

    public void updateStats(Equipment equipment){
        int before = statsmap.get(StatEnum.LEVEL);
        getLevel();//Derived level

        getOffensiveRate();//Calculate OR from the method provided initially.
        getDefensiveRate();//Calculate DR from the method provided initially.
        getArmorRate();//Calculate AR from the method provided initially.
        getMaxHP(); //Calculate HP from the method provided initially.
        getMaxMP(); //Calculate MP from the method provided intiially.
        currentstats.putAll(statsmap); //Reset our currentstats object to have default values.
        if(before != statsmap.get(StatEnum.LEVEL) && before > 0){
            currentstats.put(StatEnum.CURRENTHP, statsmap.get(StatEnum.MAXHP));
            currentstats.put(StatEnum.CURRENTMP, statsmap.get(StatEnum.MAXMP));
        }
        if (equipment.getEquipment() == null)
            return;
        Collection<EquipableItem> equipitems = equipment.getEquipment().values();
        Iterator<EquipableItem> iterator = equipitems.iterator();
        while(iterator.hasNext()){
            addStats(iterator.next().getStats());
        }
    }

   protected boolean takeDamage(int damage){
        int newHP = (currentstats.get(StatEnum.CURRENTHP) - damage);
        if(newHP > 0){//If we're still alive.
            currentstats.put(StatEnum.CURRENTHP, newHP);
            return false;
        }
        else{
            currentstats.put(StatEnum.CURRENTHP, 0);
            statsmap.put(StatEnum.NUMOFLIVES, statsmap.get(StatEnum.NUMOFLIVES)-1);
            currentstats.put(StatEnum.NUMOFLIVES, statsmap.get(StatEnum.NUMOFLIVES));
            System.out.println("WE ARE DEAD!!!");
            isGameOver();

            System.out.println("Num of lives left: " + statsmap.get(StatEnum.NUMOFLIVES));
            return true;
        }
    }

    //Method that is (as of now) called only by takeDamage if our currentHP drops to or below zero.
    public boolean isGameOver(){
        if(statsmap.get(StatEnum.NUMOFLIVES) == 0) {
            return true;
        }
        else return false;
    }

    protected void healDamage(int healz){
        if(currentstats.get(StatEnum.CURRENTHP) + healz > currentstats.get(StatEnum.MAXHP))
            currentstats.put(StatEnum.CURRENTHP, currentstats.get(StatEnum.MAXHP));
        else currentstats.put(StatEnum.CURRENTHP, currentstats.get(StatEnum.CURRENTHP)+healz);
    }

    protected void useMana(int mana){
        //TODO: Implement MP-related methods
    }

    protected void addStats(Statistics itemstat) {
        Set<Map.Entry<StatEnum, Integer>> entries = itemstat.statsmap.entrySet();
        //System.out.println(entries);
        Iterator<Map.Entry<StatEnum, Integer>> iterator = entries.iterator();

        while(iterator.hasNext())
        {
            Map.Entry<StatEnum, Integer> entry = iterator.next();
            currentstats.put(entry.getKey(), (entry.getValue() + currentstats.get(entry.getKey())));
            //Above statement: Put the new value at a certain key got from the entry, the current value found in our CURRENT STATS + the new value found in the entry.
            //This one is for equipments only, since those are temporary.
        }
    }

    protected void addBaseStats(Statistics itemstat){
        Set<Map.Entry<StatEnum, Integer>> entries = itemstat.statsmap.entrySet();
        Iterator<Map.Entry<StatEnum, Integer>> iterator = entries.iterator();

        while(iterator.hasNext())
        {
            Map.Entry<StatEnum, Integer> entry = iterator.next();
            statsmap.put(entry.getKey(), (entry.getValue() + statsmap.get(entry.getKey())));
            //Above statement: Put the new value at a certain key got from the entry, the current value found in our statsmap + the new value found in the entry.
            if(entry.getValue() != 0)
                System.out.println(entry.getKey()+" was boosted by "+entry.getValue()+"!");
            if(entry.getKey() == StatEnum.MAXHP || entry.getKey() == StatEnum.MAXMP || entry.getKey() == StatEnum.ARMORRATE || entry.getKey()
                 == StatEnum.DEFENSERATE || entry.getKey() == StatEnum.OFFENSERATE)
                hpmpmap.put(entry.getKey(), (entry.getValue() + hpmpmap.get(entry.getKey()))); //Put derived stat boosts in special Map.
        }

    }

    protected int getLevel() {
        statsmap.put(StatEnum.LEVEL, 1 + (statsmap.get(StatEnum.EXP) / 10));//Simple EXP->LVL formula for now; will model it more later;

        return statsmap.get(StatEnum.LEVEL); //Return the newly updated (if at all) value of level calculated.
    }

    protected int getMaxHP(){
        statsmap.put(StatEnum.MAXHP, getLevel() + statsmap.get(StatEnum.HARDINESS) + hpmpmap.get(StatEnum.MAXHP));
        //Formula for MaxHP: Hardiness + Level
        return statsmap.get(StatEnum.MAXHP);
    }

    protected int getMaxMP(){
        statsmap.put(StatEnum.MAXMP, getLevel() + statsmap.get(StatEnum.INTELLECT) + hpmpmap.get(StatEnum.MAXMP));
        //Formula for MaxHP: Intellect + Level
        return statsmap.get(StatEnum.MAXMP);
    }

    protected int getOffensiveRate(){
        statsmap.put(StatEnum.OFFENSERATE, (statsmap.get(StatEnum.STRENGTH)+getLevel()+hpmpmap.get(StatEnum.OFFENSERATE)));
        //Formula for OR: Strength + TempSTR + (derived) Level
        return statsmap.get(StatEnum.OFFENSERATE);
    }

    protected int getDefensiveRate(){
        statsmap.put(StatEnum.DEFENSERATE, (statsmap.get(StatEnum.AGILITY)+getLevel()+hpmpmap.get(StatEnum.DEFENSERATE)));
        //Formula for DR: Agility + TempAGI + (derived) Level
        return statsmap.get(StatEnum.DEFENSERATE);
    }

    protected int getArmorRate(){
        statsmap.put(StatEnum.ARMORRATE, (statsmap.get(StatEnum.HARDINESS))+hpmpmap.get(StatEnum.ARMORRATE));
        //Formula for AR: Hardiness + TempHAR
        return statsmap.get(StatEnum.ARMORRATE);
    }

    public int getCurrentHp() {
        return currentstats.get(StatEnum.CURRENTHP);
    }

    public int getCurrentMp() {
        return currentstats.get(StatEnum.CURRENTMP);
    }

    protected void setStat(StatEnum se, int valueofchange)
    {
        statsmap.put(se, valueofchange);
    }

    public void setCurrStat(StatEnum se, int valueofchange){
        currentstats.put(se, valueofchange);
    }

    public int getStat(StatEnum se){
        return currentstats.get(se);
    }
    public String leveltoString() {
        String stat = "Level: " + currentstats.get(StatEnum.LEVEL);
        return stat;
    }

    public String exptoString() {
        String stat = "Exp:" + currentstats.get(StatEnum.EXP);
        return stat;
    }

    public String livestoString() {
        String stat = "Lives: " + currentstats.get(StatEnum.NUMOFLIVES);
        return stat;
    }

    public String healthtoString() {
        String stat = "Health: " + currentstats.get(StatEnum.CURRENTHP) + "/" + currentstats.get(StatEnum.MAXHP);
        return stat;
    }

    public String manatoString() {
        String stat = "Mana: " + currentstats.get(StatEnum.CURRENTMP) + "/" + currentstats.get(StatEnum.MAXMP);
        return stat;
    }

    public String offensetoString() {
        String stat = "Offense: " + currentstats.get(StatEnum.OFFENSERATE);
        return stat;
    }

    public String defensetoString() {
        String stat = "Defense: " + currentstats.get(StatEnum.DEFENSERATE);
        return stat;
    }

    public String armourtoString() {
        String stat = "Armour: " + currentstats.get(StatEnum.ARMORRATE);
        return stat;
    }

    public String strengthtoString() {
        String stat = "Strength: " + currentstats.get(StatEnum.STRENGTH);
        return stat;
    }

    public String agilitytoString() {
        String stat = "Agility: " + currentstats.get(StatEnum.AGILITY);
        return stat;
    }

    public String intellecttoString() {
        String stat = "Intellect: " + currentstats.get(StatEnum.INTELLECT);
        return stat;
    }

    public String hardinesstoString() {
        String stat = "Hardiness: " + currentstats.get(StatEnum.HARDINESS);
        return stat;
    }

    public String movementtoString() {
        String stat ="Movement: " + currentstats.get(StatEnum.MOVEMENT);
        return stat;
    }

    @Override
    public Element save(Document dom) {
        Element statsElement = dom.createElement("Statistics");
        for (Map.Entry<StatEnum, Integer> stat : statsmap.entrySet()) {
            StatEnum statEnum = stat.getKey();
            Element currentStat = dom.createElement(statEnum.toString());
            currentStat.setTextContent(Integer.toString(stat.getValue()));

            statsElement.appendChild(currentStat);
        }

        for (Map.Entry<StatEnum, Integer> stat : currentstats.entrySet()) {
            StatEnum statEnum = stat.getKey();
            Element currentStat = dom.createElement(statEnum.toString());
            currentStat.setTextContent(Integer.toString(stat.getValue()));

            statsElement.appendChild(currentStat);
        }

        return statsElement;
    }

    public String getItemStats(){
        return offensetoString()+'\n'+armourtoString()+'\n'+
               strengthtoString()+'\n'+intellecttoString()+'\n'+
               agilitytoString()+'\n'+hardinesstoString()+'\n';
    }
}

