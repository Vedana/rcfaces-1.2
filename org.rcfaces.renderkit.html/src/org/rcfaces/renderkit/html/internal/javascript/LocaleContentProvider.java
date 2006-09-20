/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.3  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/08/28 16:03:54  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.3  2006/06/28 17:48:27  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.2  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.1  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.util.FilteredContentProvider;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.renderkit.html.internal.AbstractCalendarRenderer;
import org.rcfaces.renderkit.html.internal.codec.JavascriptCodec;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LocaleContentProvider extends FilteredContentProvider {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(LocaleContentProvider.class);

    private static final String JAVASCRIPT_CHARSET = "UTF-8";

    private static final String LOCALE_CLASS_PATTERN = "f_locale_";

    private final String bundleName;

    public LocaleContentProvider() {
        ApplicationFactory factory = (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);

        String bundleName = factory.getApplication().getMessageBundle();
        if (bundleName == null) {
            bundleName = FacesMessage.FACES_MESSAGES;
        }

        this.bundleName = bundleName;
    }

    public IContent getContent(Object contentReference, Locale locale) {

        if (locale == null) {
            throw new NullPointerException("Locale parameter can not be null !");
        }

        String surl = contentReference.toString();
        int idx = surl.lastIndexOf(LOCALE_CLASS_PATTERN);
        if (idx >= 0) {
            idx += LOCALE_CLASS_PATTERN.length();
            int idx2 = surl.indexOf('.', idx);

            surl = surl.substring(0, idx) + "xx" + surl.substring(idx2);
            try {
                contentReference = new URL(surl);

            } catch (MalformedURLException ex) {
                LOG.error("Can not reformat url to '" + surl + "' !", ex);

                return null;
            }
        }

        return new FilteredURLContent((URL) contentReference, locale);
    }

    public boolean searchLocale(Object contentReference, Locale locale,
            Locale[] foundLocale) {
        String surl = contentReference.toString();
        int idx = surl.lastIndexOf(LOCALE_CLASS_PATTERN);
        if (idx < 0) {
            return false;
        }

        return true;
    }

    protected String getCharset() {
        return JAVASCRIPT_CHARSET;
    }

    protected String updateBuffer(String buffer, URL url, Locale locale) {

        if (locale == null || buffer.indexOf("$$$MONTH_SHORT_NAMES$$$") < 0) {
            return super.updateBuffer(buffer, url, locale);
        }

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);

        String months[] = dateFormatSymbols.getMonths();
        String shortMonths[] = dateFormatSymbols.getShortMonths();

        StringAppender sbShort = new StringAppender(256);
        StringAppender sbMed = new StringAppender(256);
        StringAppender sbLong = new StringAppender(256);
        for (int i = 0; i < 12; i++) {
            int idx = Calendar.JANUARY + i;
            if (i > 0) {
                sbShort.append(',');
                sbMed.append(',');
                sbLong.append(',');
            }

            String shortMonth = shortMonths[idx];
            if (shortMonth == null) {
                shortMonth = months[idx].substring(0, 3);
            } else if (shortMonth.endsWith(".")) {
                shortMonth = shortMonth.substring(0, shortMonth.length() - 1);
            }

            sbShort.append('"');
            sbShort.append(Character.toUpperCase(shortMonth.charAt(0)));
            sbShort.append('"');

            sbMed.append('"');
            sbMed.append(shortMonth);
            sbMed.append('"');

            sbLong.append('"');
            sbLong.append(months[idx]);
            sbLong.append('"');
        }

        buffer = replace(buffer, "$$$MONTH_SHORT_NAMES$$$", sbShort.toString());
        buffer = replace(buffer, "$$$MONTH_MED_NAMES$$$", sbMed.toString());
        buffer = replace(buffer, "$$$MONTH_LONG_NAMES$$$", sbLong.toString());

        String days[] = dateFormatSymbols.getWeekdays();
        String shortDays[] = dateFormatSymbols.getShortWeekdays();

        sbShort.setLength(0);
        sbMed.setLength(0);
        sbLong.setLength(0);
        for (int i = 0; i < 7; i++) {
            int idx = Calendar.SUNDAY + i;

            if (i > 0) {
                sbShort.append(',');
                sbMed.append(',');
                sbLong.append(',');
            }

            String shortDay = shortDays[idx];
            if (shortDay == null) {
                shortDay = days[idx].substring(0, 3);
            } else if (shortDay.endsWith(".")) {
                shortDay = shortDay.substring(0, shortDay.length() - 1);
            }

            sbShort.append('"');
            sbShort.append(Character.toUpperCase(shortDay.charAt(0)));
            sbShort.append('"');

            sbMed.append('"');
            sbMed.append(shortDay);
            sbMed.append('"');

            sbLong.append('"');
            sbLong.append(days[idx]);
            sbLong.append('"');
        }

        buffer = replace(buffer, "$$$DAY_SHORT_NAMES$$$", sbShort.toString());
        buffer = replace(buffer, "$$$DAY_MED_NAMES$$$", sbMed.toString());
        buffer = replace(buffer, "$$$DAY_LONG_NAMES$$$", sbLong.toString());

        Calendar calendar = Calendar.getInstance(locale);
        int firstDayOfWeek = calendar.getFirstDayOfWeek() - Calendar.SUNDAY;

        buffer = replace(buffer, "$$$FIRST_DAY_OF_WEEK$$$", String
                .valueOf(firstDayOfWeek));

        StringAppender datePatterns = new StringAppender(64);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
                locale);
        if (dateFormat instanceof SimpleDateFormat) {
            String shortPattern = ((SimpleDateFormat) dateFormat).toPattern();

            if (shortPattern != null && shortPattern.length() > 0) {
                datePatterns.append('"');
                datePatterns.append(shortPattern);
                datePatterns.append('"');
            } else {
                datePatterns.append("null");
            }
        } else {
            datePatterns.append("null");
        }

        datePatterns.append(',');

        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        if (dateFormat instanceof SimpleDateFormat) {
            String mediumPattern = ((SimpleDateFormat) dateFormat).toPattern();

            if (mediumPattern != null && mediumPattern.length() > 0) {
                datePatterns.append('"');
                datePatterns.append(mediumPattern);
                datePatterns.append('"');
            } else {
                datePatterns.append("null");
            }
        } else {
            datePatterns.append("null");
        }

        datePatterns.append(',');

        dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
        if (dateFormat instanceof SimpleDateFormat) {
            String longPattern = ((SimpleDateFormat) dateFormat).toPattern();
            if (longPattern != null && longPattern.length() > 0) {
                datePatterns.append('"');
                datePatterns.append(longPattern);
                datePatterns.append('"');
            } else {
                datePatterns.append("null");
            }
        } else {
            datePatterns.append("null");
        }

        buffer = replace(buffer, "$$$DATE_FORMATS$$$", datePatterns.toString());

        String twoDigitYearStart = "null";
        if (dateFormat instanceof SimpleDateFormat) {
            Date d = ((SimpleDateFormat) dateFormat).get2DigitYearStart();

            if (d != null) {
                StringAppender sb = new StringAppender(128);
                sb.append('\"');
                AbstractCalendarRenderer.appendDate(calendar, d, sb, true);
                sb.append('\"');

                twoDigitYearStart = sb.toString();
            }
        }

        buffer = replace(buffer, "$$$TWO_DIGIT_YEAR_START$$$",
                twoDigitYearStart);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName,
                locale, getCurrentLoader(this));

        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.component.UIInput.CONVERSION");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.component.UIInput.REQUIRED");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.component.UISelectOne.INVALID");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.component.UISelectMany.INVALID");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.NOT_IN_RANGE");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.DoubleRangeValidator.MAXIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.DoubleRangeValidator.MINIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.DoubleRangeValidator.TYPE");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.LengthValidator.MAXIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.LengthValidator.MINIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.LongRangeValidator.MAXIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.LongRangeValidator.MINIMUM");
        buffer = replaceResource(buffer, resourceBundle,
                "javax.faces.validator.LongRangeValidator.TYPE");

        return super.updateBuffer(buffer, url, locale);
    }

    private String replaceResource(String buffer,
            ResourceBundle resourceBundle, String resourceName) {

        String localizedMessage = resourceBundle.getString(resourceName);
        if (localizedMessage == null) {
            localizedMessage = "?" + resourceName + "?";
        }

        if (localizedMessage.indexOf('"') >= 0) {
            localizedMessage = JavascriptCodec.encodeJavaScript(
                    localizedMessage, '\"');
        }

        return replace(buffer, "$$$" + resourceName + "$$$", localizedMessage);
    }

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            return loader;
        }

        return fallbackClass.getClass().getClassLoader();
    }
}
