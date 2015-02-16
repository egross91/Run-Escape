package org.escaperun.game.model.entities;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.OneShotItem;
import org.escaperun.game.model.items.TakeableItem;
import org.escaperun.game.model.items.UsableItem;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Entity implements Drawable, Savable {

    public Entity(Occupation occupation, int numberoflives, Decal decal, Position position, Inventory inventory, Equipment equipment) {
        this.occupation = occupation; //Get occupation from constructor.
        this.stats = new Statistics(occupation, numberoflives); //3 is "numberoflives", c.f. Statistics.java
        this.decal = decal;
        this.position = position;
        this.inventory = inventory;
        this.equipment = equipment;
    }

    protected Statistics stats;
    protected Occupation occupation;
    protected Position position;
    protected Decal decal;
    protected Inventory inventory;
    protected Equipment equipment;

    //Delegate task of takeDamage to our Statistics object
    public void takeDamage(int dmg){
       if(stats.takeDamage(dmg))
           return;
           /*
           TODO: Have some condition that is based on whether or not we have a "Game Over" condition. (c.f. Statistics.java)...
           As of now, nothing is to come about from this boolean value being returned.We are only using its functionality.
           */
    }

    //Delegate task of healDamage to our Statistics object
    public void healDamage(int healz){
        stats.healDamage(healz);
    }

    //Use StatEnum to find out which stat to change.
    public void changeStat(StatEnum se, int valueofchange) {
        stats.setStat(se, valueofchange);
    }

    public Occupation getOccupation() {
        return occupation;
    }

    //Return our position.
    public Position getPosition(){ return this.position; }

    public void levelUp() {
        stats.setStat(StatEnum.EXP, stats.statsmap.get(StatEnum.EXP) + 10);//Auto-level since we only need 10 exp per level.
        stats.setStat(StatEnum.STRENGTH, stats.statsmap.get(StatEnum.STRENGTH)+stats.statsmap.get(StatEnum.LEVEL));//BOOST THIS STAT ON LVL UP
        stats.setStat(StatEnum.INTELLECT, stats.statsmap.get(StatEnum.INTELLECT)+stats.statsmap.get(StatEnum.LEVEL));//BOOST THIS STAT ON LVL UP
        stats.setStat(StatEnum.AGILITY, stats.statsmap.get(StatEnum.AGILITY)+stats.statsmap.get(StatEnum.LEVEL));//BOOST THIS STAT ON LVL UP
        stats.setStat(StatEnum.HARDINESS, stats.statsmap.get(StatEnum.HARDINESS)+stats.statsmap.get(StatEnum.LEVEL));//BOOST THIS STAT ON LVL UP
        stats.setStat(StatEnum.MOVEMENT, stats.statsmap.get(StatEnum.MOVEMENT)+1);//BOOST THIS STAT ON LVL UP
        stats.updateStats(equipment);//Update stats.
    }

    //Used only by our "move()" method, to reset the current position to the new position.
    private void setPosition(Position oldposition){
        this.position = oldposition;
    }

    public Decal getDecal(){ return this.decal; }

    public void move(Position position){
        this.setPosition(position);
    }

    public void equipItem(EquipableItem equipableItem) {
        return; // Only want functionality for Avatar (to work with interface Activatable.)
    }

    public boolean addItemToInventory(TakeableItem ti){
        return false; //same as above.
    }

    public void useItem(UsableItem usableItem){
        return; //Same as above.
    }

    public Statistics getStats(){
        return this.stats;
    }

    @Override
    public Element save(Document dom) {
        Element entityElement = dom.createElement("Entity");
        entityElement.setAttribute("x", Integer.toString(position.x));
        entityElement.setAttribute("y", Integer.toString(position.y));

        dom.appendChild(stats.save(dom));
        dom.appendChild(occupation.save(dom));
        dom.appendChild(inventory.save(dom));
        dom.appendChild(equipment.save(dom));

        return entityElement;
    }
    public void getHitWithItem(OneShotItem osi){
        stats.addBaseStats(osi.getStats());
        stats.updateStats(equipment);
    }

    public Inventory getInventory(){return this.inventory;}
}
