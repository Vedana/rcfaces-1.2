/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.2  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
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

import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HorizontalTextPositionConverter implements Converter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new HorizontalTextPositionConverter();

    protected static final String RIGHT_POSITION_NAME = "right";

    protected static final String LEFT_POSITION_NAME = "left";

    private static final Integer DEFAULT_POSITION = new Integer(
            IHorizontalTextPositionCapability.DEFAULT_POSITION);

    private static Map HORIZONTAL_TEXT_POSITIONS = new HashMap(5);
    static {
        Integer i = new Integer(IHorizontalTextPositionCapability.LEFT_POSITION);
        HORIZONTAL_TEXT_POSITIONS.put(LEFT_POSITION_NAME, i);

        i = new Integer(IHorizontalTextPositionCapability.RIGHT_POSITION);
        HORIZONTAL_TEXT_POSITIONS.put(RIGHT_POSITION_NAME, i);
    }

    protected Map getTextPositions() {
        return HORIZONTAL_TEXT_POSITIONS;
    }

    protected Integer getDefaultPosition() {
        return DEFAULT_POSITION;
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
            return getDefaultPosition();
        }

        value = value.toLowerCase();

        Integer i = (Integer) getTextPositions().get(value);
        if (i != null) {
            return i;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a text-position type !");
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
            return (String) getTextPositions().get(getDefaultPosition());
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = getTextPositions().entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a text-position type !");
    }

}
