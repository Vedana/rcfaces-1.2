/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
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

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class OrderConverter implements Converter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new OrderConverter();

    private static final String ASCENDING_ORDER_NAME = "ascending";

    private static final String DESCENDING_ORDER_NAME = "descending";

    private static final Boolean DEFAULT_ORDER = Boolean.TRUE;

    private static Map ORDER_TYPES = new HashMap(5);
    static {
        ORDER_TYPES.put(ASCENDING_ORDER_NAME, Boolean.TRUE);

        ORDER_TYPES.put(DESCENDING_ORDER_NAME, Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1 || "default".equals(value)) {
            return DEFAULT_ORDER;
        }

        value = value.toLowerCase();

        Boolean b = (Boolean) ORDER_TYPES.get(value);
        if (b != null) {
            return b;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for an order type !");
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
            return (String) ORDER_TYPES.get(DEFAULT_ORDER);
        }

        if ((value instanceof Boolean) == false) {
            throw new IllegalArgumentException("Value must be a Boolean !");
        }

        for (Iterator it = ORDER_TYPES.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for an order type !");
    }

}
