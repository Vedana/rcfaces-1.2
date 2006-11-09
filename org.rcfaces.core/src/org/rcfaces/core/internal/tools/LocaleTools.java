/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LocaleTools {
    private static final String REVISION = "$Revision$";

    public static final boolean NORMALIZE_LOCALE_PARAMETER_SUPPORT = false;

    public static final int DATE_TYPE = 0;

    public static final int TIME_TYPE = 1;

    public static final int DATE_TIME_TYPE = 2;

    public static final int NUMBER_TYPE = 3;

    public static final int INTEGER_TYPE = 4;

    public static final int PERCENT_TYPE = 5;

    public static final int CURRENCY_TYPE = 6;

    public static final int MAX_TYPE = 6;

    private static final Map dateFormatByLocale;
    static {
        if (Constants.CACHED_LOCALE_FORMAT) {
            dateFormatByLocale = new HashMap(32);
        }
    }

    private static final int DEFAULT_STYLE_BY_TYPE[] = new int[MAX_TYPE+1];
    static {
        DEFAULT_STYLE_BY_TYPE[0] = DateFormat.SHORT;
        DEFAULT_STYLE_BY_TYPE[1] = DateFormat.MEDIUM;
        DEFAULT_STYLE_BY_TYPE[2] = DateFormat.MEDIUM;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static interface IFormatNormalizer {
        String normalizeFormat(IComponentRenderContext componentRenderContext,
                int type, String format, String param);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class LocaleDateTimeFormatNormalizer implements
            LocaleTools.IFormatNormalizer {
        private static final String REVISION = "$Revision$";

        private final int style;

        LocaleDateTimeFormatNormalizer(int style) {
            this.style = style;
        }

        public String normalizeFormat(
                IComponentRenderContext componentRenderContext, int type,
                String format, String param) {

            Locale locale = getLocale(componentRenderContext, param);

            return getFormatPattern(locale, style, type);
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
            int type, Map normalizers) {
        if (format == null || format.length() < 1) {
            return format;
        }

        String param = null;
        if (LocaleTools.NORMALIZE_LOCALE_PARAMETER_SUPPORT) {
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

        LocaleTools.IFormatNormalizer normalizer = (LocaleTools.IFormatNormalizer) normalizers
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

    private static Format getFormatByType(Locale locale, int type, int style) {
        if (style < 0) {
            style = DEFAULT_STYLE_BY_TYPE[type];
        }
        switch (type) {
        case DATE_TYPE:
            return DateFormat.getDateInstance(style, locale);

        case TIME_TYPE:
            return DateFormat.getTimeInstance(style, locale);

        case DATE_TIME_TYPE:
            return DateFormat.getDateTimeInstance(style, style, locale);

        case NUMBER_TYPE:
            return NumberFormat.getNumberInstance(locale);

        case INTEGER_TYPE:
            return NumberFormat.getIntegerInstance(locale);

        case PERCENT_TYPE:
            return NumberFormat.getPercentInstance(locale);

        case CURRENCY_TYPE:
            return NumberFormat.getCurrencyInstance(locale);
        }

        return null;
    }

    private static String getPattern(Format format) {
        if (format instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) format).toPattern();
        }

        if (format instanceof DecimalFormat) {
            return ((DecimalFormat) format).toPattern();
        }

        throw new FacesException("Can not get format pattern from format: "
                + format);

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class CachedLocale {
        private final Locale locale;

        private final Format defaultFormats[];

        private String patternsByType[][];

        public CachedLocale(Locale locale) {
            this.locale = locale;

            this.defaultFormats = new Format[MAX_TYPE+1];
        }

        public Locale getLocale() {
            return locale;
        }

        public Format getDefaultFormat(int type) {
            Format format;

            synchronized (defaultFormats) {
                format = defaultFormats[type];
                if (format == null) {
                    format = getFormatByType(locale, type, -1);
                    defaultFormats[type] = format;
                }
            }

            return format;
        }

        public String getDefaultPattern(int type) {
            return getPattern(type, DEFAULT_STYLE_BY_TYPE[type]);
        }

        public String getPattern(int type, int style) {

            synchronized (this) {
                if (patternsByType == null) {
                    patternsByType = new String[MAX_TYPE + 1][];
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

                Format format;
                if (style == DEFAULT_STYLE_BY_TYPE[type]) {
                    format = getDefaultFormat(type);

                } else {
                    format = getFormatByType(locale, type, style);
                }

                String pattern;
                synchronized (format) {
                    pattern = LocaleTools.getPattern(format);
                }

                patterns[style] = pattern;

                return pattern;
            }
        }
    }

    public static String getFormatPattern(Locale locale, int style, int type) {

        if (Constants.CACHED_LOCALE_FORMAT == false) {
            Format format = getFormatByType(locale, type, style);

            return getPattern(format);
        }

        return LocaleTools.getCachedLocale(locale).getPattern(type, style);
    }

    public static Format getDefaultFormat(UIComponent component, int type) {

        Locale locale = ContextTools.getAttributesLocale(component);

        if (Constants.CACHED_LOCALE_FORMAT == false) {
            return getFormatByType(locale, type, -1);
        }

        return LocaleTools.getCachedLocale(locale).getDefaultFormat(type);
    }

    public static String getDefaultPattern(Locale locale, int type) {

        if (Constants.CACHED_LOCALE_FORMAT == false) {
            Format format = getFormatByType(locale, type, -1);

            return getPattern(format);
        }

        return LocaleTools.getCachedLocale(locale).getDefaultPattern(type);
    }

}
