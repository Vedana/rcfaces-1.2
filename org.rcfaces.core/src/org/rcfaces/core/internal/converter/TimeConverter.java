/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.2  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.1  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 */
package org.rcfaces.core.internal.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.tools.TimeTools;
import org.rcfaces.core.model.AbstractConverter;
import org.rcfaces.core.model.Time;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TimeConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new TimeConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null || value.trim().length() < 1) {
            return null;
        }

        return TimeTools.parseValue(context, component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }

        return TimeTools.formatValue(component, (Time) value);
    }
}
