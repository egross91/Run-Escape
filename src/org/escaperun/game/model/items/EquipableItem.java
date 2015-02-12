package org.escaperun.game.model.items;

import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.entities.Statistics;
import org.escaperun.game.view.Decal;

/**
 * Created by Eric on 2/11/2015.
 */
public class EquipableItem extends TakeableItem {
    //Category which an EquipableItem falls under; Choices are HELMET, WEAPON, BOOTS, GLOVES, ARMOR for now.
    private ItemSlot category = null;

    public EquipableItem() {
        super();
    }

    public EquipableItem(Decal[][] decal, Statistics stats, ItemSlot category) {
        super(decal, stats);
        this.category = category; //This is to identify which "ItemSlot" equip should go to. - Jeff
    }

    public EquipableItem(Decal[][] decal, Statistics stats, ItemSlot category, boolean collidable) {
        super(decal, stats, collidable);
        this.category = category;
    }

    public ItemSlot getCategory(){
        return this.category; //Returns the category (ItemSlot) where the item should go to.
    }

}
