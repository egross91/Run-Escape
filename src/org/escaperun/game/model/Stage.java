package org.escaperun.game.model;

import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.tile.Tile;
import org.escaperun.game.view.Decal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Stage {

    public final Dimension dimensions;
    public final Tile[][] map;
    public final ArrayList<Entity> entities;

    public Stage(Dimension dim) {
        this.dimensions = dim;
        this.map = new Tile[dim.height][dim.width];
        this.entities = new ArrayList<Entity>();
    }

    /*public boolean updateAvatarPosition(Position delta, Avatar avatar) {
        Position current = avatar.getPosition();
        Position modified = new Position(current.x + delta.x, current.y + delta.y);

        // Notifies Game that the user cannot move.
        if (!isMovable(modified))
            return false;

        Tile to = map[modified.y][modified.x];
        to.setDecal(avatar.getDecal());
        avatar.move(modified);
        return true;
    }*/

    private boolean isMovable(Position pos) {
        if (pos == null)
            return false;

        // Check x-coordinate.
        if (!(pos.x > -1 && pos.x < dimensions.height))
            return false;

        // Check y-coordinate.
        if (!(pos.y > -1 && pos.y < dimensions.width))
            return false;

        // Check if the candidate Tile has a ObstacleItem, Impassable Terrain, or is null.
        Tile candidate = map[pos.x][pos.y];
        if (candidate == null || candidate.getTerrain().isCollidable() || candidate.getItem().isCollidable())
            return false;

        // The Position is legit.
        return true;
    }

    public Decal[][] getDecals() {
        Decal[][] decals = new Decal[dimensions.height][dimensions.width];
        for (int r = 0; r < dimensions.height; ++r) {
            for (int c = 0; c < dimensions.width; ++c) {
                Tile current = map[r][c];
                decals[r][c] = current.getDecal();
            }
        }

        return decals;
    }
}
