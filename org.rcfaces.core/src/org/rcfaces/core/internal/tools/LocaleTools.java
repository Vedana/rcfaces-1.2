/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LocaleTools {
    private static final String REVISION = "$Revision$";

    public static final int DATE_TYPE = 0;

    public static final int TIME_TYPE = 1;

    public static final int DATE_TIME_TYPE = 2;

    private static final Map dateFormatByLocale = new HashMap(32);

    private static final int DEFAULT_STYLE_BY_TYPE[] = new int[] {
            DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.SHORT };

    private static final Map NORMALIZERS;

    static {
        NORMALIZERS = new HashMap(4);
        NORMALIZERS.put("SHORT", new LocaleDateTimeFormatNormalizer(
                DateFormat.SHORT));
        NORMALIZERS.put("MEDIUM", new LocaleDateTimeFormatNormalizer(
                DateFormat.MEDIUM));
        NORMALIZERS.put("LONG", new LocaleDateTimeFormatNormalizer(
                DateFormat.LONG));
        NORMALIZERS.put("FULL", new LocaleDateTimeFormatNormalizer(
                DateFormat.FULL));
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static interface IDateTimeFormatNormalizer {
        String normalizeFormat(IComponentRenderContext componentRenderContext,
                int type, String format, String param);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class LocaleDateTimeFormatNormalizer implements
            LocaleTools.IDateTimeFormatNormalizer {
        private static final String REVISION = "$Revision$";

        private final int style;

        LocaleDateTimeFormatNormalizer(int style) {
            this.style = style;
        }

        public String normalizeFormat(
                IComponentRenderContext componentRenderContext, int type,
                String format, String param) {

            Locale locale = getLocale(componentRenderContext, param);

            return getDateTimeFormatPattern(locale, style, type);
        }

        protected Locale getLocale(
                IComponentRenderContext componentRenderContext, String param) {
            Locale locale = null;

            if (param != null) {
                locale = (Locale) LocaleConverter.SINGLETON.getAsObject(null,
                        null, param);
                if (locale == null) {
                    throw new FacesException("Invalid locale name '" + param
                            + "'.");
                }
            }

            if (locale == null) {
                locale = componentRenderContext.getRenderContext()
                        .getProcessContext().getUserLocale();
            }

            return locale;
        }
    }

    public static String normalizeFormat(
            IComponentRenderContext componentRenderContext, String format,
            int type) {
        if (format == null || format.length() < 1) {
            return format;
        }

        String param = null;
        if (CalendarTools.NORMALIZE_PARAMETER_SUPPORT) {
            if (format.charAt(0) != '$') {
                return format;
            }

            format = format.substring(1);

            int idx = format.indexOf('(');
            if (idx >= 0) {
                param = format.substring(idx + 1);
                format = format.substring(0, idx);

                idx = param.lastIndexOf(')');
                if (idx < 0 || idx != param.length() - 1) {
                    throw new FacesException("Invalid date format '" + format
                            + "' parentheses are not correctly balanced.");
                }

                param = param.substring(0, idx);
            }
        }

        LocaleTools.IDateTimeFormatNormalizer normalizer = (LocaleTools.IDateTimeFormatNormalizer) NORMALIZERS
                .get(format.toUpperCase());
        if (normalizer == null) {
            return format;
        }

        return normalizer.normalizeFormat(componentRenderContext, type, format,
                param);
    }

    private static final CachedLocale getCachedLocale(Locale locale) {
        synchronized (dateFormatByLocale) {
            CachedLocale cachedLocale = (CachedLocale) dateFormatByLocale
                    .get(locale);
            if (cachedLocale != null) {
                return cachedLocale;
            }

            cachedLocale = new CachedLocale(locale);
            dateFormatByLocale.put(locale, cachedLocale);

            return cachedLocale;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class CachedLocale {
        private final Locale locale;

        private final DateFormat defaultFormats[];

        private String patternsByType[][];

        public CachedLocale(Locale locale) {
            this.locale = locale;

            this.defaultFormats = new DateFormat[] {
                    getDateFormatByType(DATE_TYPE, -1),
                    getDateFormatByType(TIME_TYPE, -1),
                    getDateFormatByType(DATE_TIME_TYPE, -1) };
        }

        private DateFormat getDateFormatByType(int type, int style) {
            if (style < 0) {
                style = DEFAULT_STYLE_BY_TYPE[type];
            }
            switch (type) {
            case 0:
                return DateFormat.getDateInstance(style, locale);

            case 1:
                return DateFormat.getTimeInstance(style, locale);

            default:
                return DateFormat.getDateTimeInstance(style, style, locale);
            }
        }

        public Locale getLocale() {
            return locale;
        }

        public DateFormat getDefaultFormat(int type) {
            return defaultFormats[type];
        }

        public String getDefaultPattern(int type) {
            return getPattern(type, DEFAULT_STYLE_BY_TYPE[type]);
        }

        public synchronized String getPattern(int type, int style) {
            if (patternsByType == null) {
                patternsByType = new String[DATE_TIME_TYPE + 1][];
            }

            String patterns[] = patternsByType[type];
            if (patterns != null) {
                String pattern = patterns[style];
                if (pattern != null) {
                    return pattern;
                }
            } else {
                patterns = new String[DateFormat.SHORT + 1];
                patternsByType[type] = patterns;
            }

            DateFormat dateFormat;
            if (style == DEFAULT_STYLE_BY_TYPE[type]) {
                dateFormat = getDefaultFormat(type);

            } else {
                dateFormat = getDateFormatByType(type, style);
            }

            String pattern;
            synchronized (dateFormat) {
                if ((dateFormat instanceof SimpleDateFormat) == false) {
                    throw new FacesException(
                            "Can not get format pattern from date format: "
                                    + dateFormat + ": (locale '" + locale
                                    + "')");
                }

                pattern = ((SimpleDateFormat) dateFormat).toPattern();
            }

            patterns[style] = pattern;

            return pattern;
        }
    }

    public static String getDateTimeFormatPattern(Locale locale, int style,
            int type) {

        return LocaleTools.getCachedLocale(locale).getPattern(type, style);
    }

    public static DateFormat getDefaultFormat(UIComponent calendarComponent,
            int type) {
        Locale locale = ContextTools.getAttributesLocale(calendarComponent);

        return LocaleTools.getCachedLocale(locale).getDefaultFormat(type);
    }

    public static String getDefaultPattern(Locale locale, int type) {
        return LocaleTools.getCachedLocale(locale).getDefaultPattern(type);
    }

}
