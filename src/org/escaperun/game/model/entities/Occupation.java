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

    private String getColorString() {
        if (color == Color.RED) {
            return "red";
        }
        else if (color == Color.BLUE) {
            return "blue";
        }
        else {
            return "green";
        }
    }

    @Override
    public Element save(Document dom) {
        Element occupationElement = dom.createElement("Occupation");
        occupationElement.setAttribute("strength", Integer.toString(strength));
        occupationElement.setAttribute("intelligence", Integer.toString(intelligence));
        occupationElement.setAttribute("agility", Integer.toString(agility));
        occupationElement.setAttribute("hardiness", Integer.toString(hardiness));
        occupationElement.setAttribute("movement", Integer.toString(movement));
        occupationElement.setAttribute("color", getColorString());

        return occupationElement;
    }
}