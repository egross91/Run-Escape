package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.ItemSlot;

import java.util.*;

/**
 * Created by Jeff on 2015/02/12 (012), 06:44.
 */
public class Equipment {

    private Map<ItemSlot, EquipableItem> equipment; //Our Map, which maps an ItemSlot category (HELMET, etc.) to the corresponding equipped item (if any)

    public Equipment(){
        equipment = new HashMap<ItemSlot, EquipableItem>(); //Initialize Map in constructor.
    }

    public Equipment(EquipableItem... equipableItems){
        equipment = new HashMap<ItemSlot, EquipableItem>(); //Initialize Map in constructor.

        for(EquipableItem item : equipableItems)
        {
            this.equipItem(item); //For each equipableItem put in our argument, equip it to our entity.
        }
    }

    public EquipableItem equipItem(EquipableItem equipableItem){
        if(!equipment.containsKey(equipableItem.getCategory())) //If we do not have any mapping to that current ItemSlot
        {
            equipment.put(equipableItem.getCategory(), equipableItem); //add that equipment to the spot that has none
            //this.changeStats(equipableItem.getStats()); //Change the stats
        }

        else
        {
            EquipableItem returneditem = this.unequipItem(equipableItem.getCategory()); //Remove the current item that is going to be replaced from our equipment line-up.
            equipment.put(equipableItem.getCategory(), equipableItem); //Equip the new item.
            return returneditem; //Return our swapped equipable item.
        }
        return null; //Return nothing since nothing was swapped from equipment.
    }

    public EquipableItem unequipItem(ItemSlot category){
        return equipment.remove(category); //Map's remove() func returns "removed" item, or null if none.
    }

    //TODO: Add all stat changes due to weapons/armor to Avatar/Entity's statistics.
}
