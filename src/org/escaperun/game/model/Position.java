package org.escaperun.game.model;

/**
 * Created by Eric on 2/11/2015.
 */
public final class Position {
    private final int y;
    private final int x;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}
