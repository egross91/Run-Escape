package org.escaperun.game.serialization;

import javax.xml.bind.Element;

interface Savable {
    public Element save();
    public Object load(Element e);
}
