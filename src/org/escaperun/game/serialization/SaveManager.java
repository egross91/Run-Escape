package org.escaperun.game.serialization;

import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.items.Item;
import org.escaperun.game.model.tile.AreaEffect;
import org.escaperun.game.model.tile.Terrain;
import org.escaperun.game.model.tile.Tile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;

public class SaveManager {
    private final String PROFILE_DIRECTORY = "/profiles/";
    private final String MAPS_DIRECTORY = "/maps/";

    public Stage startNewGame() {
        File stageFile;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document dom;

        try {
            // TODO: Add functionality to read from String[] of map files.
            stageFile = new File(System.getProperty("user.dir") + MAPS_DIRECTORY + "stage1.xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            dom = builder.parse(stageFile.toString());

            dom.getDocumentElement().normalize();
            NodeList nodes = dom.getElementsByTagName("Stage");

            Element dimensions = (Element)nodes.item(0);
            int rows = toInt(dimensions.getAttribute("rows"));
            int cols = toInt(dimensions.getAttribute("cols"));
            int startX = toInt(dimensions.getAttribute("startX"));
            int startY = toInt(dimensions.getAttribute("startY"));

            Tile[][] map = new Tile[rows][cols];
            for (int i = 1; i < nodes.getLength(); ++i) {
                Node node = nodes.item(i); //

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element)node; // <Tile>
                    int x = getCoord(ele, "x");
                    int y = getCoord(ele, "y");

                    Tile tile = getTileProperties(ele);
                }
            }

            return new Stage(map, new Dimension(cols, rows), new Position(startX, startY));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getCoord(Element node, String dim) {
        return toInt(node.getElementsByTagName(dim).item(0).getTextContent());
    }

    private Tile getTileProperties(Element node) {
        Terrain terrain = getTerrainProperties(node);
        Item item = getItemProperties(node);
        AreaEffect effect = getAreaEffectProperties(node);

        return new Tile(terrain, item, effect);
    }

    private Terrain getTerrainProperties(Element node) {
        return null;
    }

    private Item getItemProperties(Element node) {
        return null;
    }

    private AreaEffect getAreaEffectProperties(Element node) {
        return null;
    }

    private int toInt(String value) {
        return Integer.parseInt(value);
    }
}
