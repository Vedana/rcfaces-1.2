/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.12  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.11  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.10  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.9  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.8  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.7  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.6  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.5  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.4  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Calendar;
import java.util.Date;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.component.CalendarComponent;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CalendarRenderer extends AbstractCalendarRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CALENDAR;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {

        htmlWriter.startElement("DIV");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        writeCalendarAttributes(htmlWriter);

        htmlWriter.enableJavaScript();

        super.encodeBeforeDecorator(htmlWriter, componentDecorator);
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("DIV");
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();
        if (component instanceof IBorderCapability) {
            IBorderCapability borderCapability = (IBorderCapability) component;

            if (borderCapability.isBorder() == false) {
                cssWriter.writeProperty("border-style", "none");
            }
        }
    }

    protected void writeCalendarAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        super.writeCalendarAttributes(htmlWriter);

        writeCalendarMode(htmlWriter);
    }

    protected void writeCalendarMode(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CalendarComponent calendarComponent = (CalendarComponent) componentRenderContext
                .getComponent();

        int mode = calendarComponent.getMode();
        if (mode != 0) {
            htmlWriter.writeAttribute("v:mode", mode);
        }

        Calendar calendar = CalendarTools
                .getAttributesCalendar(calendarComponent);

        Object value = calendarComponent.getValue();
        String s_value = null;

        StringAppender sb = new StringAppender(12);
        switch (mode) {
        case ICalendarModeCapability.CALENDAR_MODE_DATE:
            if (value instanceof Date) {
                Date d = (Date) value;

                sb.setLength(0);
                appendDate(calendar, d, sb, true);

                s_value = sb.toString();

            } else if (value != null) {
                throw new FacesException("Value for calendarMode " + mode
                        + " must be a Date object.");
            }

            break;

        case ICalendarModeCapability.CALENDAR_MODE_PERIOD:
            if (value instanceof Date[][]) {
                Date ds[][] = (Date[][]) value;

                if (ds.length > 1) {
                    throw new FacesException(
                            "Only one period is accepted for calendarMode '"
                                    + mode + "'.");
                }

                if (ds.length > 0) {
                    s_value = convertDate(calendar, ds[0], true);
                }

            } else if (value != null) {
                throw new FacesException("Value for calendarMode " + mode
                        + " must be an array of Date object.");
            }

            break;

        case ICalendarModeCapability.CALENDAR_MODE_PERIODS:
            if (value instanceof Date[][]) {
                // Date ds[][] = (Date[][]) value;

                // String s = convertDate(calendar, ds);
            }
            break;

        default:
            throw new FacesException("Unknown calendarMode ! (" + mode + ")");
        }

        if (s_value != null) {
            htmlWriter.writeAttribute("v:value", s_value);
        }
    }

}
