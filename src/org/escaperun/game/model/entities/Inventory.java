package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.Item;
import org.escaperun.game.model.items.TakeableItem;

import java.util.ArrayList;

public class Inventory {

    private int capacity;
    private ArrayList<TakeableItem> inventoryarr;

    public Inventory(){
        capacity = 50;
        inventoryarr = new ArrayList<TakeableItem>(capacity);
    }

    public Inventory(int capacity){
        this.capacity = capacity;
        inventoryarr = new ArrayList<TakeableItem>(capacity);
    }

    public Inventory(int capacity, TakeableItem... ti) {
        inventoryarr = new ArrayList<TakeableItem>(capacity);
        this.capacity = capacity;

        for(TakeableItem takeableItem : ti) {
            this.add(takeableItem); //Go through all passed TakeableItems and add them to the inventory, one at a time.
        }
    }

    public int getSize(){
        return inventoryarr.size();
    }
    public int getCapacity() {
        return capacity;
    }

    public void add(TakeableItem ti){
        if(!(ti.equals(null))) {            // check if item is null, pertains to equip/unequip returns if nothing is equipped
            inventoryarr.add(ti);
        }
    }

    //Pass UsableItem to avatar for it to use.
    public TakeableItem useItem(int index){
        if(inventoryarr.get(index).getClass().getName().equals( "UsableIem")) //Hacky way of going about things. Probably best to figure out a better way to check for usability.
           return inventoryarr.remove(index);
        else return null;
    }

    //Remove the item that's at that index.
    public TakeableItem remove(int index){
        return inventoryarr.remove(index);
    }

    public TakeableItem getItem(int index) { return inventoryarr.get(index);
    }
}
