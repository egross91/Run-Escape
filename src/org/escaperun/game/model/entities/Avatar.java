package org.escaperun.game.model.entities;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public class Avatar extends Entity{

    public Avatar(Occupation occupation){
        //TODO: Add occupation, statistics change based on that, etc.
        this.occupation = occupation; //Get occupation from constructor.
        this.stats = new Statistics(occupation, 3); //3 is "numberoflives", c.f. Statistics.java
    }

}
