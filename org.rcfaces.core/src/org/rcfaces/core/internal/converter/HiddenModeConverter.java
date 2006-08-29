/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.3  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.1  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IVisibilityCapability;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HiddenModeConverter implements Converter {
    private static final String REVISION = "$Revision$";

    private static final Integer DEFAULT_HIDDEN_MODE = new Integer(
            IVisibilityCapability.DEFAULT_HIDDEN_MODE);

    private static final String IGNORE_HIDDEN_MODE_NAME = "ignore";

    private static final String SERVER_HIDDEN_MODE_NAME = "server";

    private static final String PHANTOM_HIDDEN_MODE_NAME = "phantom";

    private static final String DEFAULT_HIDDEN_MODE_NAME = "default";

    private static final String CLIENT_HIDDEN_MODE_NAME = "client";

    public static final Converter SINGLETON = new HiddenModeConverter();

    private static Map HIDDEN_MODES = new HashMap(5);
    static {
        HIDDEN_MODES.put(IGNORE_HIDDEN_MODE_NAME, new Integer(
                IVisibilityCapability.IGNORE_HIDDEN_MODE));
        HIDDEN_MODES.put(SERVER_HIDDEN_MODE_NAME, new Integer(
                IVisibilityCapability.SERVER_HIDDEN_MODE));
        HIDDEN_MODES.put(PHANTOM_HIDDEN_MODE_NAME, new Integer(
                IVisibilityCapability.PHANTOM_HIDDEN_MODE));
        HIDDEN_MODES.put(DEFAULT_HIDDEN_MODE_NAME, new Integer(
                IVisibilityCapability.DEFAULT_HIDDEN_MODE));
        HIDDEN_MODES.put(CLIENT_HIDDEN_MODE_NAME, new Integer(
                IVisibilityCapability.CLIENT_HIDDEN_MODE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1) {
            return DEFAULT_HIDDEN_MODE;
        }

        value = value.toLowerCase();

        Integer i = (Integer) HIDDEN_MODES.get(value);
        if (i != null) {
            return i;
        }

        if ("default".equalsIgnoreCase(value)) {
            return DEFAULT_HIDDEN_MODE;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a hidden-mode type !");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        if (value == null) {
            return DEFAULT_HIDDEN_MODE_NAME;
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = HIDDEN_MODES.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue()) == false) {
                continue;
            }

            return (String) entry.getKey();
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a hidden-mode type !");
    }

    public static final String getName(int hiddenMode) {
        switch (hiddenMode) {
        case IVisibilityCapability.IGNORE_HIDDEN_MODE:
            return IGNORE_HIDDEN_MODE_NAME;

        case IVisibilityCapability.PHANTOM_HIDDEN_MODE:
            return PHANTOM_HIDDEN_MODE_NAME;

        case IVisibilityCapability.SERVER_HIDDEN_MODE:
            return SERVER_HIDDEN_MODE_NAME;
        }

        return null;
    }
}
