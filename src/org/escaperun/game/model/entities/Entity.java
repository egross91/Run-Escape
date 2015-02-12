package org.escaperun.game.model.entities;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public abstract class Entity {

    protected Statistics stats;
    protected Occupation occupation;

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
}
