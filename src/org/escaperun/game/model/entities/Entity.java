package org.escaperun.game.model.entities;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public abstract class Entity {

    protected Statistics stats;
    protected Occupation occupation;

    public abstract void takeDamage(int dmg);

    public abstract void healDamage(int healz);

    //Use StatEnum to find out which stat to change.
    public abstract void changeStat(StatEnum se, int valueofchange);
}
