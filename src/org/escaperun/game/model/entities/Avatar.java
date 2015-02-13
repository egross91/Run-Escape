package org.escaperun.game.model.entities;

import org.escaperun.game.model.Position;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.ItemSlot;
import org.escaperun.game.model.items.TakeableItem;
import org.escaperun.game.model.items.UsableItem;
import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public class Avatar extends Entity{

    public Avatar(Occupation occupation){
        super(occupation, 3, new Decal('@', Color.BLACK, Color.RED), new Position(1,1), new Inventory(), new Equipment());
        //TODO: For Inventory and Equipment, add basic items that can be given to Avatar upon creation,
        //TODO: such as a wooden sword, 3 health potions, etc.
        //3 is standard number of lives for Avi; can change if need be
        //Avatar is red @ sign Decal -- Can change it need be
    }

    //Pass that task along to our inventory object.
    public void addItemToInventory(TakeableItem ti){
        if(inventory.getCapacity()-inventory.getSize() != 0)//If our knapsack is not full!
        inventory.add(ti); //Add item.
    }

    public void equipItem(int index){
        //TODO: Implement this method.
        //This if statement checks if requirements of activateable are met. It would then remove from inventory and defer action to onTouch
        //if(this.inventory.getItem(index).isActivatable(this)) this.inventory.remove(index).onTouch(this);

    }

    public void unequipItem(ItemSlot itemSlot){
        if(inventory.getCapacity() - inventory.getSize() == 0)
            return; //Don't need to do anything, inventory's full.
        EquipableItem unequipped = equipment.unequipItem(itemSlot);
        this.stats.removeEquipStats(unequipped.getStats());
        this.addItemToInventory(unequipped);
    }

    public void useItem(UsableItem usableItem){
        //TODO: Implement this method.
        // can potentially be merged with equipItem b/c activateable will handle dynamically type check and perform the correct function
    }
}
