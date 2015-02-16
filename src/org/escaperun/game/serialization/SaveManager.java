package org.escaperun.game.serialization;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.escaperun.game.model.Position;
import org.escaperun.game.model.Stage;
import org.escaperun.game.model.entities.*;
import org.escaperun.game.model.items.*;
import org.escaperun.game.model.tile.*;
import org.escaperun.game.view.Decal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SaveManager {
    private static final String PROFILE_DIRECTORY = "/profiles/";
    private static final String MAPS_DIRECTORY = "/maps/";
    private static final String SAVE_DIRECTORY = System.getProperty("user.dir") + PROFILE_DIRECTORY;
    private static final File SAVE_FILE_DIRECTORY = new File(SAVE_DIRECTORY);

    public SaveManager() {
        makeDirectory(SAVE_FILE_DIRECTORY);
    }

    // TODO: Hook into this and allow for the 'stage' to be saved.
    public static boolean saveCurrentGame(Stage stage, Avatar avatar, String playerName) {
//        if (!saveAvatar(avatar, playerName))
//            return false;

        saveAvatar(avatar, playerName);

        Dimension dimensions = stage.dimensions;
        Position start = stage.start;
        Tile[][] savables = stage.map;
        Document xmlDom = new DocumentImpl();
        Element root = xmlDom.createElement("Stage");
        root.setAttribute("cols", Integer.toString((int)dimensions.getWidth()));
        root.setAttribute("rows", Integer.toString((int)dimensions.getHeight()));
        root.setAttribute("startX", Integer.toString(start.x));
        root.setAttribute("startY", Integer.toString(start.y));

        for (int r = 0; r < dimensions.getHeight(); ++r) {
            for (int c = 0; c < dimensions.getWidth(); ++c) {
                Tile tile = savables[r][c];
                Element tileElement = tile.save(xmlDom);
                tileElement.setAttribute("x", Integer.toString(r));
                tileElement.setAttribute("y", Integer.toString(c));

                root.appendChild(tileElement);
            }
        }

        xmlDom.appendChild(root);
        return saveToFile(playerName, xmlDom, "savedStage");
    }

    public static Stage loadSavedGame(String playerName) {
        Avatar avatar = loadPlayerAvatar(playerName);
        return loadStage(SAVE_FILE_DIRECTORY + "/" + playerName + "/savedStage.xml", avatar);
    }

    public static Stage startNewGame(Avatar avatar) {
        return loadStage(System.getProperty("user.dir") + MAPS_DIRECTORY + "stage1.xml", avatar);
    }

    private static boolean saveAvatar(Avatar avatar, String playerName) {
        try {
            String directory = SAVE_FILE_DIRECTORY + "/" + playerName + "/";
            makeDirectory(new File(directory));
            File avatarFile = new File(directory + "avatar.xml");
            avatarFile.createNewFile();

            Document xmlDom = new DocumentImpl();
            Element root = xmlDom.createElement("Avatar");
            root.setAttribute("x", Integer.toString(avatar.getPosition().x));
            root.setAttribute("y", Integer.toString(avatar.getPosition().y));

            root.appendChild(avatar.getOccupation().save(xmlDom));
            root.appendChild(avatar.getStats().save(xmlDom));
            root.appendChild(avatar.getEquipment().save(xmlDom));
            root.appendChild(avatar.getInventory().save(xmlDom));

            xmlDom.appendChild(root);
            return saveToFile(playerName, xmlDom, "avatar");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Avatar loadPlayerAvatar(String playerName) {
        try {
            File avatarFile = new File(SAVE_FILE_DIRECTORY + "/" + playerName + "/avatar.xml");
            if (!avatarFile.exists()) {
                avatarFile.createNewFile();
            }
            Document dom = getDom(avatarFile);
            try {
                dom.getDocumentElement().normalize();
            } catch (Exception e) {
                return new Avatar(Occupation.SMASHER);
            }

            Element lastPosition = (Element)dom.getElementsByTagName("Avatar").item(0);
            int x = toInt(lastPosition.getAttribute("x"));
            int y = toInt(lastPosition.getAttribute("y"));
            Occupation occupation = getOccupationProperties((Element) dom.getElementsByTagName("Occupation").item(0));
            Equipment equipment = getEquipmentProperties((Element)dom.getElementsByTagName("Equipment").item(0));
            Statistics stats = new Statistics(getAvatarStatisticsProperties(dom.getDocumentElement()));
            stats.updateStats(equipment);
            Inventory inventory = getInventoryProperties((Element)dom.getElementsByTagName("Inventory").item(0));

            return new Avatar(occupation, new Position(x, y), stats, inventory, equipment); // Placeholder.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Stage loadStage(String filePath, Avatar avatar) {
        try {
            // TODO: Add functionality to read from String[] of map files.
            File stageFile = new File(filePath);
            makeDirectory(stageFile);
            Document dom = getDom(stageFile);

            dom.getDocumentElement().normalize();
            NodeList nodes = dom.getElementsByTagName("Stage");

            Element dimensions = (Element)nodes.item(0);
            int rows = toInt(dimensions.getAttribute("rows"));
            int cols = toInt(dimensions.getAttribute("cols"));
            int startX = toInt(dimensions.getAttribute("startX"));
            int startY = toInt(dimensions.getAttribute("startY"));

            nodes = dom.getElementsByTagName("Tile");

            Tile[][] map = new Tile[rows][cols];
            for (int i = 0; i < nodes.getLength(); ++i) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node; // <Tile>
                    int x = getCoord(element, "x");
                    int y = getCoord(element, "y");

                    Tile tile = getTileProperties(element);
                    map[x][y] = tile;
                }
            }

            return new Stage(map, new Dimension(cols, rows), new Position(startX, startY), avatar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean saveToFile(String playerName, Document dom, String fileName) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            makeDirectory(new File(SAVE_FILE_DIRECTORY + "/" + playerName + "/"));
            File saveFile = new File(SAVE_FILE_DIRECTORY + "/" + playerName + "/" + fileName + ".xml");

            DOMSource source = new DOMSource(dom);
            StreamResult result = new StreamResult(saveFile);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Document getDom(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            try {
                return builder.parse(file);
            } catch (Exception e) {
                return builder.newDocument();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Tile getTileProperties(Element node) {
        Terrain terrain = getTerrainProperties(node);
        Item item = getItemProperties(node);
        AreaEffect effect = getAreaEffectProperties(node);

        return new Tile(terrain, item, effect);
    }

    private static Terrain getTerrainProperties(Element node) {
        Element terrain = (Element)node.getElementsByTagName("Terrain").item(0);
        if (terrain == null)
            return null;

        String type = getType(terrain).toUpperCase();

        if (type.equals("GRASS"))
            return new Grass();
        else if (type.equals("WATER"))
            return new Water();
        else if (type.equals("MOUNTAIN"))
            return new Mountain();

        return null;
    }

    private static Item getItemProperties(Element node) {
        Element item = (Element)node.getElementsByTagName("Item").item(0);
        if (item == null)
            return null;

        try {
            String type = getType(item).toUpperCase();
            Statistics stats = new Statistics(getStatisticsProperties(item));

            // Making the assumption that we will never instantiate a TakeableItem.
            if (type.equals("EQUIPABLE")) {
                ItemSlot slot = getItemSlot(item);
                return new EquipableItem(stats, slot);
            }
            else if (type.equals("USABLE")) {
                // TODO: Figure out the decal situation.
                return new UsableItem();
            }
            else if (type.equals("OBSTACLE")) {
                return new ObstacleItem();
            }
            else if (type.equals("INTERACTIVE")) {
                // TODO: Figure out the decal situation.
                return new InteractiveItem();
            }
            else if (type.equals("ONESHOT")) {
                return new OneShotItem(new Decal('?', Color.BLACK, Color.CYAN), stats);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        throw new IllegalArgumentException("Item is not of a valid type");
    }

    private static Occupation getOccupationProperties(Element node) {
        if (node == null)
            return null;

        String occ = node.getAttribute("occ").toUpperCase();

        if (occ.equals("SMASHER"))
            return Occupation.SMASHER;
        else if (occ.equals("SUMMONER"))
            return Occupation.SUMMONER;
        else if (occ.equals("SNEAK"))
            return Occupation.SNEAK;

        return null;
    }

    private static Map<StatEnum, Integer> getAvatarStatisticsProperties(Element node) {
        Map<StatEnum, Integer> avatarStats = getStatisticsProperties(node);
        Element statElement = (Element)node.getElementsByTagName("Statistics").item(0);

        avatarStats.put(getStatEnum("CurrentHP"), toInt(statElement.getElementsByTagName("CurrentHP").item(0).getTextContent()));
        avatarStats.put(getStatEnum("CurrentMP"), toInt(statElement.getElementsByTagName("CurrentMP").item(0).getTextContent()));

        return avatarStats;
    }

    private static Map<StatEnum, Integer> getStatisticsProperties(Element node) {
        Element statElement = (Element)node.getElementsByTagName("Statistics").item(0);
        if (statElement == null)
            return new HashMap<StatEnum,Integer>();

        try {
            Map<StatEnum, Integer> stats = new HashMap<StatEnum, Integer>();

            stats.put(getStatEnum("Strength"), toInt(statElement.getElementsByTagName("Strength").item(0).getTextContent()));
            stats.put(getStatEnum("Agility"), toInt(statElement.getElementsByTagName("Agility").item(0).getTextContent()));
            stats.put(getStatEnum("Intellect"), toInt(statElement.getElementsByTagName("Intellect").item(0).getTextContent()));
            stats.put(getStatEnum("Hardiness"), toInt(statElement.getElementsByTagName("Hardiness").item(0).getTextContent()));
            stats.put(getStatEnum("Movement"), toInt(statElement.getElementsByTagName("Movement").item(0).getTextContent()));
            stats.put(getStatEnum("NumOfLives"), toInt(statElement.getElementsByTagName("NumOfLives").item(0).getTextContent()));
            stats.put(getStatEnum("Level"), toInt(statElement.getElementsByTagName("Level").item(0).getTextContent()));
            stats.put(getStatEnum("Exp"), toInt(statElement.getElementsByTagName("Exp").item(0).getTextContent()));
            stats.put(getStatEnum("MaxHP"), toInt(statElement.getElementsByTagName("MaxHP").item(0).getTextContent()));
            stats.put(getStatEnum("MaxMP"), toInt(statElement.getElementsByTagName("MaxMP").item(0).getTextContent()));
            stats.put(getStatEnum("OffenseRate"), toInt(statElement.getElementsByTagName("OffenseRate").item(0).getTextContent()));
            stats.put(getStatEnum("DefenseRate"), toInt(statElement.getElementsByTagName("DefenseRate").item(0).getTextContent()));
            stats.put(getStatEnum("ArmorRate"), toInt(statElement.getElementsByTagName("ArmorRate").item(0).getTextContent()));

            return stats;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<StatEnum, Integer>();
        }
    }

    private static Inventory getInventoryProperties(Element node) {
        if (node == null)
            return null;

        int capacity = toInt(node.getAttribute("capacity"));
        Inventory inventory = new Inventory(capacity);
        NodeList nodes = node.getElementsByTagName("Item");
        for (int i = 0; i < nodes.getLength(); ++i) {
            Item takeable = getItemProperties((Element)nodes.item(i));

            inventory.add((TakeableItem)takeable);
        }

        return inventory;
    }

    private static Equipment getEquipmentProperties(Element node) {
        if (node == null)
            return null;


        NodeList nodes = node.getElementsByTagName("Item");
        HashMap<ItemSlot, EquipableItem> equipment = new HashMap<ItemSlot, EquipableItem>();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element currentItem = (Element)nodes.item(i);
            ItemSlot slot = getItemSlot(currentItem);
            Item item = getItemProperties(currentItem);

            equipment.put(slot, (EquipableItem)item);
        }

        return new Equipment(equipment);
    }

    private static AreaEffect getAreaEffectProperties(Element node) {
        Element element = (Element)node.getElementsByTagName("AreaEffect").item(0);
        if (element == null)
            return null;

        try {
            String type = getType(element).toUpperCase();
            int value = toInt(element.getTextContent());

            if (type.equals("HEALDAMAGE"))
                return new HealDamage(value);
            else if (type.equals("INSTANTDEATH"))
                return new InstantDeath();
            else if (type.equals("LEVELUP"))
                return new LevelUp();
            else if (type.equals("TAKEDAMAGE"))
                return new TakeDamage(value);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        throw new IllegalArgumentException("Invalid AreaEffect value");
    }

    private static ItemSlot getItemSlot(Element node) {
        if (!node.hasAttribute("itemslot"))
            return null;

        try {
            int slot = toInt(node.getAttribute("itemslot"));
            switch (slot) {
                case (0):
                    return ItemSlot.HELMET;
                case (1):
                    return ItemSlot.WEAPON;
                case (2):
                    return ItemSlot.BOOTS;
                case (3):
                    return ItemSlot.GLOVES;
                case (4):
                    return ItemSlot.ARMOR;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        throw new IllegalArgumentException("Invalid ItemSlot value");
    }

    private static StatEnum getStatEnum(String value) {
        value = value.toUpperCase();
        try {
            if (value.equals("STRENGTH"))
                return StatEnum.STRENGTH;
            else if (value.equals("AGILITY"))
                return StatEnum.AGILITY;
            else if (value.equals("INTELLECT"))
                return StatEnum.INTELLECT;
            else if (value.equals("HARDINESS"))
                return StatEnum.HARDINESS;
            else if (value.equals("MOVEMENT"))
                return StatEnum.MOVEMENT;
            else if (value.equals("LEVEL"))
                return StatEnum.LEVEL;
            else if (value.equals("EXP"))
                return StatEnum.EXP;
            else if (value.equals("MAXHP"))
                return StatEnum.MAXHP;
            else if (value.equals("MAXMP"))
                return StatEnum.MAXMP;
            else if (value.equals("OFFENSERATE"))
                return StatEnum.OFFENSERATE;
            else if (value.equals("DEFENSERATE"))
                return StatEnum.DEFENSERATE;
            else if (value.equals("ARMORRATE"))
                return StatEnum.ARMORRATE;
            else if (value.equals("CURRENTMP"))
                return StatEnum.CURRENTMP;
            else if (value.equals("CURRENTHP"))
                return StatEnum.CURRENTHP;
            else if (value.equals("NUMOFLIVES"))
                return StatEnum.NUMOFLIVES;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        throw new IllegalArgumentException("Invalid value for StatEnum");
    }

//    private static Color getColor(String color) {
//        if (color.equals("RED"))
//            return Color.RED;
//        else if (color.equals("BLUE"))
//            return Color.BLUE;
//        else if (color.equals("GREEN"))
//            return Color.GREEN;
//
//        return Color.CYAN;
//    }

    private static String getType(Element node) {
        return node.getAttribute("type");
    }

    private static int getCoord(Element node, String dim) {
        return toInt(node.getAttribute(dim));
    }

    private static int toInt(String value) {
        return Integer.parseInt(value);
    }

    private static void makeDirectory(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
