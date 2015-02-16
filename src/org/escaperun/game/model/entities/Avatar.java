package org.escaperun.game.model.entities;

import org.escaperun.game.model.Position;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.ItemSlot;
import org.escaperun.game.model.items.TakeableItem;
import org.escaperun.game.model.items.UsableItem;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class Avatar extends Entity {

    public Avatar(Occupation occupation){
        super(occupation, 3, new Decal('@', Color.BLACK, occupation.getColor()), new Position(0, 0), new Inventory(), new Equipment());
        /*
        NOTE: For avatar (and other entities), we can have the direction the character moved flash briefly
        as soon as he moves, then go back to its static decal (for iteration 2; probably not feasible for this
        iteration. Ex) If @ moves to the right, he will briefly change to > then back to @ (around 1/4 sec)
         */
        //TODO: For Inventory and Equipment, add basic items that can be given to Avatar upon creation,
        //TODO: such as a wooden sword, 3 health potions, etc.
        //3 is standard number of lives for Avi; can change if need be
        //Avatar is red @ sign Decal -- Can change it need be
    }

    public Avatar(Occupation occupation, Position position, Statistics stats, Inventory inventory, Equipment equipment) {
        super(occupation, position, stats, inventory, equipment);
    }

//    public Avatar(Occupation occupation, int lives, Position position, Inventory inventory, Equipment equipment) {
//        super(occupation, lives, position, inventory, equipment);
//    }

    //Pass that task along to our inventory object.
    public boolean addItemToInventory(TakeableItem ti){
        if((inventory.getCapacity()-inventory.getSize() != 0) && (ti != null))//If our knapsack is not full and we were not passed a null reference!
            inventory.add(ti); //Add item.
        else return false;// We are full; Return operation unsuccessful.
        return true; // It was good, return true (successful operation).
    }

    public void equipItem(EquipableItem equipableItem){
        EquipableItem anyreturned = equipment.equipItem(equipableItem);
        if(anyreturned != null) {
            addItemToInventory(anyreturned);
        }
        stats.updateStats(equipment);
    }

    public void unequipItem(ItemSlot itemSlot){
        //If a different parameter needs to be passed in order to use this method, let me know.
        if(inventory.getCapacity() - inventory.getSize() == 0)
            return; //Don't need to do anything, inventory's full.
        EquipableItem unequipped = equipment.unequipItem(itemSlot);
        stats.updateStats(equipment);
        addItemToInventory(unequipped);
    }

    public void useItem(UsableItem usableItem){
        //TODO: Implement this method.
        // can potentially be merged with equipItem b/c activateable will handle dynamically type check and perform the correct function
        // Not so; We need to differentiate between if it's a UsableItem, or an EquipableItem as it passes itself -Jeff
    }

    public void takeDamage(int dmg){
        if(stats.takeDamage(dmg) && stats.statsmap.get(StatEnum.NUMOFLIVES) != 0){
            move(new Position(0,0)); //Reset position; we died.
            stats.currentstats.putAll(stats.statsmap);//Reset all temporary stats because of death.
            stats.currentstats.put(StatEnum.CURRENTHP, stats.statsmap.get(StatEnum.MAXHP)); //reset HP

        }
    }

    public Equipment getEquipment(){return this.equipment;}


    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Element save(Document dom) {
        Element entityElement = super.save(dom);

        Statistics stats = getStats();
        Element currentHpElement = dom.createElement("CurrentHP");
        currentHpElement.setTextContent(Integer.toString(stats.getCurrentHp()));
        Element currentMpElement = dom.createElement("CurrentMP");
        currentMpElement.setTextContent(Integer.toString(stats.getCurrentMp()));

        entityElement.getElementsByTagName("Statistics").item(0).appendChild(currentHpElement);
        entityElement.getElementsByTagName("Statistics").item(0).appendChild(currentMpElement);

        return entityElement;
    }
}
