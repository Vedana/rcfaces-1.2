/*
 * $Id$
 * 
 * $Log$
 * Revision 1.5  2006/11/09 19:09:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.6  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.5  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.4  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.3  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.2  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.internal.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.component.AbstractCalendarComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.tools.LocaleTools.LocaleDateTimeFormatNormalizer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CalendarTools {
    private static final String REVISION = "$Revision$";

    private static final Date EMPTY_PERIOD[] = new Date[0];

    private static final Date EMPTY_DATE[] = new Date[0];

    private static final Date EMPTY_PERIODS[][] = new Date[0][0];

    private static final int DEFAULT_CENTURY = 1900;

    private static final Map DATE_KEYWORDS = new HashMap(4);
    static {
        DATE_KEYWORDS.put("now", new IDateKeyword() {
            private static final String REVISION = "$Revision$";

            public Date getDate(DateFormat dateFormat) {
                return new Date();
            }

        });
        DATE_KEYWORDS.put("today", new DayDateKeyword() {
            private static final String REVISION = "$Revision$";

            public Date getDate(DateFormat dateFormat) {
                synchronized (dateFormat) {
                    Calendar calendar = getDayCalendar(dateFormat);

                    return calendar.getTime();
                }
            }
        });

        DATE_KEYWORDS.put("yesterday", new DayDateKeyword() {
            private static final String REVISION = "$Revision$";

            public Date getDate(DateFormat dateFormat) {
                synchronized (dateFormat) {
                    Calendar calendar = getDayCalendar(dateFormat);

                    calendar.add(Calendar.DATE, -1);

                    return calendar.getTime();
                }
            }
        });
        DATE_KEYWORDS.put("tomorrow", new DayDateKeyword() {
            private static final String REVISION = "$Revision$";

            public Date getDate(DateFormat dateFormat) {
                synchronized (dateFormat) {
                    Calendar calendar = getDayCalendar(dateFormat);

                    calendar.add(Calendar.DATE, 1);

                    return calendar.getTime();
                }
            }
        });
    }

    private static final Map DATE_NORMALIZERS;

    static {
        DATE_NORMALIZERS = new HashMap(4);
        DATE_NORMALIZERS.put("SHORT", new LocaleDateTimeFormatNormalizer(
                DateFormat.SHORT));
        DATE_NORMALIZERS.put("MEDIUM", new LocaleDateTimeFormatNormalizer(
                DateFormat.MEDIUM));
        DATE_NORMALIZERS.put("LONG", new LocaleDateTimeFormatNormalizer(
                DateFormat.LONG));
        DATE_NORMALIZERS.put("FULL", new LocaleDateTimeFormatNormalizer(
                DateFormat.FULL));
    }

    public static void setDate(AbstractCalendarComponent component, String date) {
        DateFormat dateFormat = getShortDateFormat(component);

        Date d = parseDate(dateFormat, date);

        component.setValue(d);
    }

    public static void setPeriod(AbstractCalendarComponent component,
            String period) {
        DateFormat dateFormat = getShortDateFormat(component);

        Date ds[] = parsePeriod(dateFormat, period);

        component.setValue(ds);
    }

    private static Date[] parsePeriod(DateFormat dateFormat, String dates) {
        StringTokenizer st = new StringTokenizer(dates, ":");
        int cnt = st.countTokens();
        if (cnt == 0) {
            return EMPTY_PERIOD;
        }

        Date d = parseDate(dateFormat, st.nextToken());
        if (d == null) {
            return EMPTY_PERIOD;
        }

        if (cnt == 1) {
            return new Date[] { d };
        }

        Date d2 = parseDate(dateFormat, st.nextToken());
        if (d2 == null) {
            return EMPTY_PERIOD;
        }

        return new Date[] { d, d2 };
    }

    /*
     * private static Date computeNext(FacesContext facesContext, UIComponent
     * calendarComponent, Date date) { Calendar calendar =
     * getAttributesCalendar(facesContext, calendarComponent);
     * 
     * calendar.setTime(date); calendar.add(Calendar.DATE, 1);
     * 
     * return calendar.getTime(); }
     */

    private static Date[][] parsePeriods(DateFormat dateFormat, String dates) {
        List l = null;
        StringTokenizer st = new StringTokenizer(dates, ",");
        for (; st.hasMoreTokens();) {
            String token = st.nextToken();

            Date ds[] = parsePeriod(dateFormat, token);
            if (ds == null || ds.length < 2) {
                continue;
            }

            if (l == null) {
                l = new ArrayList();
            }

            l.add(ds);
        }

        Date ds[][] = EMPTY_PERIODS;
        if (l != null) {
            ds = (Date[][]) l.toArray(new Date[l.size()][]);
        }

        return ds;
    }

    private static Date[] parseDates(DateFormat dateFormat, String dates) {
        List l = null;
        StringTokenizer st = new StringTokenizer(dates, ",");
        for (; st.hasMoreTokens();) {
            String token = st.nextToken();

            Date ds = parseDate(dateFormat, token);
            if (ds == null) {
                continue;
            }

            if (l == null) {
                l = new ArrayList();
            }

            l.add(ds);
        }

        Date ds[] = EMPTY_DATE;
        if (l != null) {
            ds = (Date[]) l.toArray(new Date[l.size()]);
        }

        return ds;
    }

    /*
     * 
     * public static final Date parseDate(FacesContext facesContext, UIComponent
     * calendarComponent, String date) { }
     */

    private static final Date parseDate(DateFormat dateFormat, String date) {

        IDateKeyword dateKeyword = (IDateKeyword) DATE_KEYWORDS.get(date
                .toLowerCase());
        if (dateKeyword != null) {
            return dateKeyword.getDate(dateFormat);
        }

        try {
            synchronized (dateFormat) {
                return dateFormat.parse(date);
            }

        } catch (ParseException e) {
            throw new FacesException("Can not parse date '" + date + "'.", e);
        }
    }

    public static Calendar getAttributesCalendar(UIComponent component) {
        Locale locale = ContextTools.getAttributesLocale(component);

        return Calendar.getInstance(locale);
    }

    private static interface IDateKeyword {
        Date getDate(DateFormat dateFormat);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class DayDateKeyword implements IDateKeyword {
        private static final String REVISION = "$Revision$";

        protected final Calendar getDayCalendar(DateFormat dateFormat) {

            Calendar calendar = dateFormat.getCalendar();
            calendar.setTime(new Date());

            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);

            return calendar;
        }

    }

    public static Object parseValue(UIComponent component, String value) {

        DateFormat dateFormat = getShortDateFormat(component);

        if (value.indexOf(':') >= 0) {
            return parsePeriods(dateFormat, value);
        }

        if (value.indexOf(',') >= 0) {
            return parseDates(dateFormat, value);
        }

        return parseDate(dateFormat, value);
    }

    public static Date parseDate(UIComponent component, String value) {

        DateFormat dateFormat = getShortDateFormat(component);

        return parseDate(dateFormat, value);
    }

    public static String formatDate(UIComponent calendarComponent, Date date) {
        DateFormat dateFormat = getShortDateFormat(calendarComponent);
        synchronized (dateFormat) {
            return dateFormat.format(date);
        }
    }

    public static Date parseTwoDigitYearDate(UIComponent component, String value) {
        if (value == null || value.length() < 1) {
            return null;
        }

        boolean onlyDigit = true;
        char chs[] = value.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (Character.isDigit(chs[i])) {
                continue;
            }

            onlyDigit = false;
            break;
        }

        if (onlyDigit == false) {
            DateFormat dateFormat = getShortDateFormat(component);

            return parseDate(dateFormat, value);
        }

        // Il n'y a que l'année de specifier !

        Calendar calendar = getAttributesCalendar(component);

        int year = Integer.parseInt(value);
        if (year < 1000) {
            throw new FacesException(
                    "You must specify two digit year attribute with value more than 1000.");
        }

        calendar.set(year, 0, 1);
        return calendar.getTime();
    }

    private static DateFormat getShortDateFormat(UIComponent component) {
        return (DateFormat) LocaleTools.getDefaultFormat(component,
                LocaleTools.DATE_TYPE);
    }

    public static String getDateFormatPattern(Locale locale, int style) {
        return LocaleTools.getFormatPattern(locale, style,
                LocaleTools.DATE_TYPE);
    }

    public static String normalizeFormat(
            IComponentRenderContext componentRenderContext, String format) {
        return LocaleTools.normalizeFormat(componentRenderContext, format,
                LocaleTools.DATE_TYPE, DATE_NORMALIZERS);
    }

    public static String getDefaultPattern(Locale locale) {
        return LocaleTools.getDefaultPattern(locale, LocaleTools.DATE_TYPE);
    }
}
