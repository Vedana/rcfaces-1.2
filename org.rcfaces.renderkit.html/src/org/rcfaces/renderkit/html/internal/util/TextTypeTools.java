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
        TYPES.put("label", "LABEL");
        TYPES.put("emphasis", "EM");
        TYPES.put("cite", "CITE");
        TYPES.put("definition", "DFN");
        TYPES.put("code", "CODE");
        TYPES.put("sample", "SAMP");
        TYPES.put("keyboard", "KBD");
        TYPES.put("variable", "VAR");
        TYPES.put("abbreviated", "ABBR");
        TYPES.put("acronym", "ACRONYM");
        TYPES.put("quote", "BLOCKQUOTE");
        TYPES.put("q", "Q");
        TYPES.put("paragraph", "P");
        TYPES.put("preformatted", "PRE");
        TYPES.put("ins", "INS");
        TYPES.put("del", "DEL");
        TYPES.put("header", "H1");
        TYPES.put("header1", "H1");
        TYPES.put("header 1", "H1");
        TYPES.put("header2", "H2");
        TYPES.put("header 2", "H2");
        TYPES.put("header3", "H3");
        TYPES.put("header 3", "H3");
        TYPES.put("header4", "H4");
        TYPES.put("header 4", "H4");
        TYPES.put("header5", "H5");
        TYPES.put("header 5", "H5");
        TYPES.put("header6", "H6");
        TYPES.put("header 6", "H6");

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
