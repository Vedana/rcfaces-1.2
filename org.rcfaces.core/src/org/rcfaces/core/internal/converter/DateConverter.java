/*
 * $Id$
 * 
 * $Log$
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

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.tools.CalendarTools;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class DateConverter implements Converter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new DateConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return CalendarTools.parseValue(component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        return CalendarTools.formatDate(component, (Date) value);
    }
}
