package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.TakeableItem;

import java.util.ArrayList;

/**
 * Created by Jeff on 2015/02/12 (012), 06:45.
 */
public class Inventory {
    //TODO: Everything.

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

    public void add(TakeableItem ti){
        inventoryarr.add(ti);
    }

    public void useItem(TakeableItem ti){
        //TODO: Add TakeableItem use method, which includes changing stats for Avatar
    }

    public void remove(TakeableItem ti){
        //TODO: Add TakeableItem removal method
    }
}
