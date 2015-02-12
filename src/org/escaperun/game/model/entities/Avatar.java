package org.escaperun.game.model.entities;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public class Avatar extends Entity{

    public Avatar(Occupation occupation){
        //TODO: Add occupation, statistics change based on that, etc.
        this.occupation = occupation;
    }

    @Override
    public void takeDamage(int dmg) {
        //TODO: Add the takeDamage method
    }

    @Override
    public void healDamage(int healz) {
        //TODO: Add the healDamage method
    }

    @Override
    public void changeStat(StatEnum se, int valueofchange) {
        //TODO: Add the changing of status based on various items/etc.
    }
}
