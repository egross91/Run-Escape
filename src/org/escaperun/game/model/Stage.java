package org.escaperun.game.model;

import org.escaperun.game.model.entities.Avatar;
import org.escaperun.game.model.entities.Entity;
import org.escaperun.game.model.tile.Tile;
import org.escaperun.game.view.Decal;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Eric on 2/12/2015.
 */
public class Stage {
    private static final Random coordinateGenerator = new Random();
    private static int[] dx = { 1, 0, -1, 0, 0 };
    private static int[] dy = { 0, 1, 0, -1, 0 };
    private static int MAX_DELTA_INDEX = 5;

    private final Position dimensions;
    private Tile[][] tilemap;
    private ArrayList<Entity> entities;

    public Stage() {
        this.dimensions = new Position(1000, 1000);
    }

    public Stage(Position dimensions) {
        this.dimensions = dimensions;
    }

    public Stage(Position dimensions, Tile[][] tiles) {
        this.dimensions = dimensions;
        this.tilemap = tiles;
    }

    public Stage(Position dimensions, Tile[][] tiles, ArrayList<Entity> entities) {
        this.dimensions = dimensions;
        this.tilemap = tiles;
        this.entities = entities;
    }

    public Tile[][] getTilemap() {
        return this.tilemap;
    }

    public void setTilemap(Tile[][] tiles) {
        // Set to null to refrain from having any possible memory leaks.
        this.tilemap = null;
        this.tilemap = tiles;
    }

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public boolean updateAvatarPosition(Position delta, Avatar avatar) {
        Position current = avatar.getPosition();
        Position modified = new Position(current.getX() + delta.getX(), current.getY() + delta.getY());

        // Notifies Game that the user cannot move.
        if (!canMoveToTile(modified))
            return false;

        Tile to = this.tilemap[modified.getY()][modified.getX()];
        to.setDecal(avatar.getDecal());
        avatar.move(modified);
        return true;
    }

    private void updateNpcPositions() {
        // Update the position of the NPC's in the Stage.
        for (Entity e : entities) {
            Position current = e.getPosition();
            Position newPosition = findNextPossibleMove(current);

            e.move(newPosition);
        }
    }

    private Position findNextPossibleMove(Position pos) {
        // Keep checking if it's not a valid move.
        while (true) {
            int idx = coordinateGenerator.nextInt(MAX_DELTA_INDEX);
            Position modified = new Position(pos.getX() + dx[idx], pos.getY() + dy[idx]);

            if (canMoveToTile(modified)) {
                return modified;
            }
        }
    }

    private boolean canMoveToTile(final Position pos) {
        if (pos == null)
            return false;

        // Check x-coordinate.
        if (!(pos.getX() > -1 && pos.getX() < dimensions.getX()))
            return false;

        // Check y-coordinate.
        if (!(pos.getY() > -1 && pos.getY() < dimensions.getY()))
            return false;

        // Check if the candidate Tile has a ObstacleItem, Impassable Terrain, or is null.
        Tile candidate = this.tilemap[pos.getY()][pos.getX()];
        if (candidate == null || candidate.getTerrain().isCollidable() || candidate.getItem().isCollidable())
            return false;

        // The Position is legit.
        return true;
    }

    public Decal[][] getDecals() {
        // TODO: Talk about whether creating an EmptyTile is worth having instead of doing null checks.
//               Not sure how this will be implemented.
        Decal[][] decals = new Decal[this.dimensions.getY()][this.dimensions.getX()];
        for (int r = 0; r < dimensions.getY(); ++r) {
            for (int c = 0; c < dimensions.getX(); ++c) {
                Tile current = this.tilemap[r][c];
                if (current == null)
                    decals[r][c] = null;
                else
                    decals[r][c] = current.getDecal();
            }
        }

        return decals;
    }
}
