/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
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

import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.model.AbstractConverter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardinalityConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new CardinalityConverter();

    private static final String OPTIONAL_CARDINALITY_NAME = "optional";

    private static final String OPTIONAL_CARDINALITY_ABBREV = "?";

    private static final String ZEROMANY_CARDINALITY_NAME = "zeromany";

    private static final String ZEROMANY_CARDINALITY_ABBREV = "*";

    private static final String ONE_CARDINALITY_NAME = "one";

    private static final String ONE_CARDINALITY_ABBREV = "1";

    private static final String ONEMANY_CARDINALITY_NAME = "onemany";

    private static final String ONEMANY_CARDINALITY_ABBREV = "+";

    private static final Integer DEFAULT_CARDINALITY = new Integer(
            ICardinality.DEFAULT_CARDINALITY);

    private static Map CARDINALITIES = new HashMap(5);

    private static Map CARDINALITIES_ABBREV = new HashMap(5);

    static {
        Integer i = new Integer(ICardinality.ONE_CARDINALITY);
        CARDINALITIES.put(ONE_CARDINALITY_NAME, i);
        CARDINALITIES_ABBREV.put(ONE_CARDINALITY_ABBREV, i);

        i = new Integer(ICardinality.ONEMANY_CARDINALITY);
        CARDINALITIES.put(ONEMANY_CARDINALITY_NAME, i);
        CARDINALITIES_ABBREV.put(ONEMANY_CARDINALITY_ABBREV, i);

        i = new Integer(ICardinality.OPTIONAL_CARDINALITY);
        CARDINALITIES.put(OPTIONAL_CARDINALITY_NAME, i);
        CARDINALITIES_ABBREV.put(OPTIONAL_CARDINALITY_ABBREV, i);

        i = new Integer(ICardinality.ZEROMANY_CARDINALITY);
        CARDINALITIES.put(ZEROMANY_CARDINALITY_NAME, i);
        CARDINALITIES_ABBREV.put(ZEROMANY_CARDINALITY_ABBREV, i);
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
            return DEFAULT_CARDINALITY;
        }

        value = value.toLowerCase();

        Integer i = (Integer) CARDINALITIES.get(value);
        if (i != null) {
            return i;
        }

        i = (Integer) CARDINALITIES_ABBREV.get(value);
        if (i != null) {
            return i;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a cardinality type !");
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
            return (String) CARDINALITIES.get(DEFAULT_CARDINALITY);
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = CARDINALITIES.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a cardinality type !");
    }

}
