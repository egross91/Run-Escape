package org.escaperun.game.model.entities;

import org.escaperun.game.model.Drawable;
import org.escaperun.game.model.Position;
import org.escaperun.game.view.Decal;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public abstract class Entity implements Drawable{

    public Entity(Occupation occupation, int numberoflives, Decal[][] decal) {
        this.occupation = occupation; //Get occupation from constructor.
        this.stats = new Statistics(occupation, numberoflives); //3 is "numberoflives", c.f. Statistics.java
        this.decal = decal;
        this.position = new Position();
    }

    protected Statistics stats;
    protected Occupation occupation;
    protected Position position;
    protected Decal[][] decal;

    //Delegate task of takeDamage to our Statistics object
    public void takeDamage(int dmg){
        stats.takeDamage(dmg);
    }

    //Delegate task of healDamage to our Statistics object
    public void healDamage(int healz){
        stats.healDamage(healz);
    }

    //Use StatEnum to find out which stat to change.
    public void changeStat(StatEnum se, int valueofchange) {
        //TODO: Map what value gets changed to what.
        stats.changeStat(se, valueofchange);
    }

    //
    public Position getPosition(){ return this.position; }

    public Decal[][] getDecal(){ return this.decal; }

    abstract public void move();//Implementing move functionality.


}
