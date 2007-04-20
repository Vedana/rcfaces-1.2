/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextTypeTools {
    private static final String REVISION = "$Revision$";

    private static final Map TYPES = new HashMap(32);
    static {
        TYPES.put("label", IHtmlWriter.LABEL);
        TYPES.put("emphasis", "em");
        TYPES.put("cite", "cite");
        TYPES.put("definition", "dfn");
        TYPES.put("code", "code");
        TYPES.put("sample", "samp");
        TYPES.put("keyboard", "kbd");
        TYPES.put("variable", "var");
        TYPES.put("abbreviated", "abbr");
        TYPES.put("acronym", "acronym");
        TYPES.put("quote", "blockquote");
        TYPES.put("q", "q");
        TYPES.put("paragraph", "p");
        TYPES.put("preformatted", "pre");
        TYPES.put("ins", "ins");
        TYPES.put("del", "del");
        TYPES.put("header", "h1");
        TYPES.put("header1", "h1");
        TYPES.put("header 1", "h1");
        TYPES.put("header2", "h2");
        TYPES.put("header 2", "h2");
        TYPES.put("header3", "h3");
        TYPES.put("header 3", "h3");
        TYPES.put("header4", "h4");
        TYPES.put("header 4", "h4");
        TYPES.put("header5", "h5");
        TYPES.put("header 5", "h5");
        TYPES.put("header6", "h6");
        TYPES.put("header 6", "h6");

        Collection c = TYPES.values();
        String values[] = (String[]) c.toArray(new String[c.size()]);

        for (int i = 0; i < values.length; i++) {
            TYPES.put(values[i].toLowerCase(), values[i]);
        }
    }

    public static String getType(IHtmlWriter htmlWriter) {
        IHtmlComponentRenderContext renderContext = htmlWriter
                .getHtmlComponentRenderContext();

        TextComponent textComponent = (TextComponent) renderContext
                .getComponent();
        String type = textComponent.getType(renderContext.getFacesContext());

        if (type == null) {
            return null;
        }

        String element = (String) TYPES.get(type.toLowerCase());

        return element;
    }
}
