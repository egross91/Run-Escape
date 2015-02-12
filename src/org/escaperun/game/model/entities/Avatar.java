package org.escaperun.game.model.entities;

import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public class Avatar extends Entity{

    public Avatar(Occupation occupation){
        //TODO: Add occupation, statistics change based on that, etc.
        super(occupation, 3, new Decal[][]{{new Decal('@', Color.BLACK, Color.RED)}}); //3 is standard number of lives for Avi; can change if need be
        //Avatar is red @ sign Decal -- Can change it need be
    }

    @Override
    public void move() {

    }

}
