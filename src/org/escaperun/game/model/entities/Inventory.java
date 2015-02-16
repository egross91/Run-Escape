package org.escaperun.game.model.entities;

import org.escaperun.game.model.items.Item;
import org.escaperun.game.model.items.TakeableItem;
import org.escaperun.game.serialization.Savable;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.awt.Color;
import java.util.ArrayList;

public class Inventory implements Savable {

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

        System.out.println("Inv contents: "+getSize());
        if(!(ti.equals(null))) { // check if item is null, pertains to equip/unequip returns if nothing is equipped
            inventoryarr.add(ti);
        }
        System.out.println("Added "+ti.toString());
        System.out.println("Inv contents: "+getSize());
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

    public TakeableItem getItem(int index) {
//        if(index >= getSize()){
//            return null;
//        }
        return inventoryarr.get(index);
    }

    @Override
    public Element save(Document dom) {
        Element inventoryElement = dom.createElement("Inventory");
        inventoryElement.setAttribute("capacity", Integer.toString(capacity));

        for (int i = 0; i < inventoryarr.size(); ++i) {
            Item currentItem = inventoryarr.get(i);
            Element itemElement = currentItem.save(dom);

            inventoryElement.appendChild(itemElement);
        }

        return inventoryElement;
    }

    public Decal[] getInventoryDecal(){
        Decal[] d = new Decal[50];

        for(int q=0; q < getSize(); q++) {
            if (getItem(q).equals(null)) {
                d[q] = new Decal('-', Color.BLACK, Color.WHITE);
            } else {
                d[q] = getItem(q).getDecal();
            }
        }
        return d;
    }
}
