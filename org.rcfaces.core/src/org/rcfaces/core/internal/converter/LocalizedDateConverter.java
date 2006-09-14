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
 * Revision 1.2  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.1  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.internal.converter;

import java.util.Date;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LocalizedDateConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new LocalizedDateConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        UIComponent calendarComponent = findCalendarComponent(component);

        return CalendarTools.parseValue(calendarComponent, value);
    }

    protected UIComponent findCalendarComponent(UIComponent component) {

        for (; component != null; component = component.getParent()) {
            if ((component instanceof ILocalizedAttributesCapability) == false) {
                continue;
            }

            return component;
        }

        throw new FacesException(
                "CalendarConvert can not find ILocalizedAttributesCapability !");

    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }

        // XXX Gerer le cas de plusieurs Dates
        Date date = (Date) value;

        UIComponent calendarComponent = findCalendarComponent(component);

        return CalendarTools.formatDate(calendarComponent, date);
    }
}
