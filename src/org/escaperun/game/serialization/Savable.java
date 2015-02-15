package org.escaperun.game.serialization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Savable {
    public Element save(Document dom);
//    public Object load(Element e);
}
