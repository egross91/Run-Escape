package org.escaperun.game.model.entities;

import org.escaperun.game.serialization.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public enum Occupation implements Savable {
    //OCCUPATION(Strength, Agility, Intelligence)
    SMASHER(5,0,0,5,1, Color.RED),
    SUMMONER(0,0,5,3,1, Color.BLUE),
    SNEAK(0,5,0,3,1, Color.GREEN);

    private final int strength; // Strength modifier
    private final int intelligence; // Intelligence modifier
    private final int agility; //Agility modifier
    private final int hardiness; //Hardiness modifier
    private final int movement; //Movement modifier
    private final Color color; //Color that changes based on its occupation.


    Occupation(int strength, int agility, int intelligence, int hardiness, int movement, Color color){
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.hardiness = hardiness;
        this.movement = movement;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        switch (this) {
            case SMASHER:
                return "smasher";
            case SUMMONER:
                return "summoner";
            case SNEAK:
                return "sneak";
            default:
                return null;
        }
    }

    @Override
    public Element save(Document dom) {
        Element occupationElement = dom.createElement("Occupation");
        occupationElement.setAttribute("occ", toString());

        return occupationElement;
    }
}