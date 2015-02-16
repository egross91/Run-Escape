package org.escaperun.game.view;

import org.escaperun.game.serialization.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public final class Decal implements Savable {

    public final char ch;
    public final Color background;
    public final Color foreground;

    public Decal(char ch, Color background, Color foreground) {
        this.ch = ch;
        this.background = background;
        this.foreground = foreground;
    }

    @Override
    public Element save(Document dom) {
        Element ele = dom.createElement("Decal");
        ele.setAttribute("char", String.valueOf(ch));
        ele.setAttribute("background", String.valueOf(background.getRGB()));
        ele.setAttribute("foreground", String.valueOf(foreground.getRGB()));
        return ele;
    }
}
