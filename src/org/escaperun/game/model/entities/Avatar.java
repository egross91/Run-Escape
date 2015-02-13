package org.escaperun.game.model.entities;

import org.escaperun.game.model.Position;
import org.escaperun.game.model.items.EquipableItem;
import org.escaperun.game.model.items.TakeableItem;
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

    public void equipItem(EquipableItem equipableItem){
        //TODO: Implement this method.
    }

    public void unequipItem(EquipableItem equipableItem){
        //TODO: Implement this method.
    }

}
