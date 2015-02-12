package org.escaperun.game.model.entities;

/**
 * Created by Jeff on 2015/02/11 (011).
 */
public enum Occupation {
    //OCCUPATION(Strength, Agility, Intelligence)
    SMASHER(5,0,0,5,1),
    SUMMONER(0,0,5,3,1),
    SEEKER(0,5,0,3,1);

    private final int strength; // Strength modifier
    private final int intelligence; // Intelligence modifier
    private final int agility; //Agility modifier
    private final int hardiness; //Hardiness modifier
    private final int movement; //Movement modifier

    Occupation(int strength, int agility, int intelligence, int hardiness, int movement){
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.hardiness = hardiness;
        this.movement = movement;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAgility() {
        return agility;
    }

    public int getHardiness() {
        return hardiness;
    }

    public int getMovement() {
        return movement;
    }
}