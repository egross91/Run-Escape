package org.escaperun.game.model;

import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.tile.Grass;
import org.escaperun.game.model.tile.Tile;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;

import java.awt.*;
import java.util.Random;

public class Stage {

    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_HEIGHT = 50;
    public static final Random RANDOM = new Random();
    public final Dimension dimensions;
    public final Tile[][] map;
    private Avatar avatar;
    private final Position startPosition;


    public Stage(Avatar avatar) {
        this(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), avatar, new Position(0, 0));
    }

    public Stage(Dimension dim, Avatar avatar, Position start) {
        this.dimensions = dim;
        this.map = new Tile[dim.height][dim.width];
        for (int i = 0; i < dim.height; i++) {
            for (int j = 0; j < dim.width; j++) {
                int rnd = RANDOM.nextInt(3);
                if (rnd == 0) {
                    map[i][j] = new Tile();
                    map[i][j].setTerrain(new Grass());
                } else {
                    map[i][j] = new Tile();
                }
            }
        }
        this.avatar = avatar;
        this.startPosition = start;
    }

    public Stage(Dimension dim, Position start) {
        this.dimensions = dim;
        this.map = new Tile[dim.height][dim.width];
        this.startPosition = start;
    }

    public Stage(Tile[][] map, Dimension dim, Position start) {
        this.map = map;
        this.dimensions = dim;
        this.startPosition = start;
    }

    public boolean moveAvatar(Position next) {
        if (!isMovable(next))
            return false;

        avatar.move(next);
        return true;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setTile() {

    }

    private boolean isMovable(Position pos) {
        if (pos == null)
            return false;

        if (!isValid(pos.x, pos.y))
            return false;

        Tile candidate = map[pos.x][pos.y];
        if (candidate == null || (candidate.getTerrain() != null && candidate.getTerrain().isCollidable()) || (candidate.getItem() != null && candidate.getItem().isCollidable()))
            return false;

        return true;
    }

    private boolean isValid(int x, int y) {
        return (x >= 0 && x < dimensions.height) && (y >= 0 && y < dimensions.width);
    }

    public Decal[][] getRenderable() {

        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        int x = avatar.getPosition().x;
        int y = avatar.getPosition().y;

        if (GameWindow.ROWS % 2 == 0 || GameWindow.COLUMNS % 2 == 0) throw new RuntimeException("THIS IS NOT GOOD");
        int midX = GameWindow.ROWS/2;
        int midY = GameWindow.COLUMNS/2;
        ret[midX][midY] = avatar.getDecal();
        for (int i = -midX; i <= +midX; i++) {
            for (int j = -midY; j <= +midY; j++) {
                if (i == 0 && j == 0) continue; // the avatars pos :'(
                int toX = x+i;
                int toY = y+j;
                if (isValid(toX, toY)) {
                    Tile current = map[toX][toY];
                    if (current == null) continue;
                    ret[midX+i][midY+j] = current.getDecal();
                }
            }
        }
        return ret;
    }
}
