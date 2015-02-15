package org.escaperun.game.model.entities;

import org.omg.CORBA.Current;

public enum StatEnum {
    NUMOFLIVES,
    STRENGTH,
    AGILITY,
    INTELLECT,
    HARDINESS,
    EXP,
    MOVEMENT,
    TEMPSTR,
    TEMPAGI,
    TEMPINT,
    TEMPHAR,
    TEMPMOV,
    LEVEL,
    MAXHP,
    MAXMP,
    CURRENTHP,
    CURRENTMP,
    OFFENSERATE,
    DEFENSERATE,
    ARMORRATE;
    //TODO: Clean up unnecessary ENUMs.

    public String toString() {
        switch (this) {
            case NUMOFLIVES:
                return "NumOfLives";
            case STRENGTH:
                return "Strength";
            case AGILITY:
                return "Agility";
            case INTELLECT:
                return "Intellect";
            case HARDINESS:
                return "Hardiness";
            case EXP:
                return "Exp";
            case MOVEMENT:
                return "Movement";
            case LEVEL:
                return "Level";
            case MAXHP:
                return "MaxHP";
            case MAXMP:
                return "MaxMP";
            case CURRENTHP:
                return "CurrentHP";
            case CURRENTMP:
                return "CurrentMP";
            case OFFENSERATE:
                return "OffenseRate";
            case DEFENSERATE:
                return "DefenseRate";
            case ARMORRATE:
                return "ArmorRate";
            default:
                return null;
        }
    }
}
