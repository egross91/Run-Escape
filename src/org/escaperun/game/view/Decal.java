package org.escaperun.game.view;

import java.awt.*;

public final class Decal {

    public final char ch;
    public final Color background;
    public final Color foreground;

    public Decal(char ch, Color background, Color foreground) {
        this.ch = ch;
        this.background = background;
        this.foreground = foreground;
    }
}
