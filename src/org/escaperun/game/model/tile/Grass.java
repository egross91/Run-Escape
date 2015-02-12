package org.escaperun.game.model.tile;

import org.escaperun.game.view.Decal;

import java.awt.*;

/**
 * Created by Matthew LoGalbo on 2/11/2015.
 */
public class Grass extends Terrain {
    public Grass() {
        super(new Decal[][]{{new Decal('\u2EBE', Color.GRAY, Color.GREEN)}}, false);    //TODO: Check grass unicode and color are correct.
    }
}
