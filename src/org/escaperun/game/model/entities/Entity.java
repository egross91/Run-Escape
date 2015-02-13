package org.escaperun.game.model.entities;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.ItemSlot;
import org.escaperun.game.model.items.UsableItem;
import org.escaperun.game.view.Decal;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public abstract class Entity implements Drawable{

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

    //Return our position.
    public Position getPosition(){ return this.position; }

    //Used only by our "move()" method, to reset the current position to the new position.
    private void setPosition(Position oldposition){
        this.position = new Position(oldposition.getX(), oldposition.getY());
    }

    public Decal getDecal(){ return this.decal; }

    public void move(Position position){
        this.setPosition(position);
    }

}
