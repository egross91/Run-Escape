package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.model.items.ItemSlot;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.util.*;

public class Equipment implements Savable {

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

    public Equipment(HashMap<ItemSlot, EquipableItem> equipment) {
        this.equipment = equipment;
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

    protected Map<ItemSlot, EquipableItem> getEquipment(){
        return equipment;
    }

    public Decal[] getEquipDecals(){
        Decal[] decals = new Decal[5];
        //ORDER: HELMET, ARMOR, GLOVES, BOOTS, WEAPON (for what the ArrayList will return)
        if (decals[0] == null) {
            decals[0] = new Decal('-', Color.BLACK,Color.BLACK);
        }else{
            decals[0] = equipment.get(ItemSlot.HELMET).getDecal();
        }
        if(decals[1] == null){
            decals[1] = new Decal('-', Color.BLACK,Color.BLACK);
        }else{
            decals[1] = equipment.get(ItemSlot.ARMOR).getDecal();
        }
        if(decals[2] == null){
            decals[2] = new Decal('-', Color.BLACK,Color.BLACK);
        }else{
            decals[2] = equipment.get(ItemSlot.GLOVES).getDecal();
        }
        if(decals[3] == null){
            decals[3] = new Decal('-', Color.BLACK,Color.BLACK);
        }else{
            decals[3] = equipment.get(ItemSlot.BOOTS).getDecal();
        }
        if(decals[4] == null){
            decals[4] = new Decal('-', Color.BLACK,Color.BLACK);
        }else{
            decals[4] = equipment.get(ItemSlot.WEAPON).getDecal();
        }

        return decals;
    }

    @Override
    public Element save(Document dom) {
        Element equipmentElement = dom.createElement("Equipment");
        if (equipment == null) {
            throw new RuntimeException("PROBLEM");
            //return equipmentElement;
        }
        for (Map.Entry<ItemSlot, EquipableItem> current : equipment.entrySet()) {
            ItemSlot slotEnum = current.getKey();
            Item currentItem = current.getValue();
            Element itemElement = currentItem.save(dom);

            itemElement.setAttribute("itemslot", Integer.toString(slotEnum.getItemSlot()));
            equipmentElement.appendChild(itemElement);
        }

        return equipmentElement;
    }
}
