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
 * Revision 1.3  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.1  2004/08/12 14:21:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2003/01/20 14:01:25  oeuillot
 * Documentation JAVADOC
 * Supprime Parser pour le mettre dans Delay.
 *
 * Revision 1.1  2002/12/16 10:13:19  oeuillot
 * Nettoyage du code
 * Retire les setters finaux pour l'implementation
 * Ajout de methodes dans ElementTools
 *
 */
package org.rcfaces.core.internal.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Classe de constantes de gestion des delais en millisecondes.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Delay {
    private static final String REVISION = "$Revision$";

    /** The object has been deleted by its expirable date ! */
    public static final long EXPIRE_TIME_DELETE = -1;

    /**
     * The object has not an expirable date !<br>
     * <B> CAREFUL It's a LONG constant !</B>
     */
    public static final long IMMORTAL = 0;

    /** 1 second = 1000 milliseconds */
    public static final long SECOND = 1000l;

    /** 1 minute = 60000 milliseconds */
    public static final long MINUTE = 60 * SECOND;

    /** 1 hour = 3600000 milliseconds */
    public static final long HOUR = 60 * MINUTE;

    /** 1 day = 86400000 milliseconds */
    public static final long DAY = 24 * HOUR;

    /** 1 week = 604800000 milliseconds */
    public static final long WEEK = 7 * DAY;

    /**
     * 1 month = 2419200000 milliseconds <br>
     * <i>(One month is defined as four weeks.)</i>
     */
    public static final long MONTH = 30 * DAY;

    /**
     * 1 year = 365*DAY <br>
     * <i>(One year is defined as 365 days)</i>
     */
    public static final long YEAR = 365 * DAY;

    private static final NumberFormat delayNumberFormat;

    /**
     * Analyse une chaine de caracteres pour en deduire un delais.
     * 
     * @return Un delais en millisecondes.
     */
    public static long parseDelay(String value) throws ParseException {
        long total = 0;
        int current = 0;
        boolean cv = false;

        if (value.trim().equalsIgnoreCase("never")) {
            return (int) Delay.IMMORTAL;
        }

        StringTokenizer st = new StringTokenizer(value, " wdhmsWDHMS", true);

        for (; st.hasMoreTokens();) {
            String token = st.nextToken();

            if (Character.isDigit(token.charAt(0))) {
                if (cv == true) {
                    throw new ParseException(
                            "Two value in same expression. expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                cv = true;
                current = Integer.parseInt(token);

                continue;
            }

            if (token.trim().length() < 1) {
                continue;
            }

            if (token.equalsIgnoreCase("w")) {
                if (cv == false) {
                    throw new ParseException(
                            "Specified 'w' token, but no value defined ! expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                cv = false;
                total += current * Delay.WEEK;
                continue;
            }

            if (token.equalsIgnoreCase("d")) {
                if (cv == false) {
                    throw new ParseException(
                            "Specified 'd' token, but no value defined ! expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                total += current * Delay.DAY;
                cv = false;
                continue;
            }

            if (token.equalsIgnoreCase("h")) {
                if (cv == false) {
                    throw new ParseException(
                            "Specified 'h' token, but no value defined ! expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                total += current * Delay.HOUR;
                cv = false;
                continue;
            }

            if (token.equalsIgnoreCase("m")) {
                if (cv == false) {
                    throw new ParseException(
                            "Specified 'm' token, but no value defined ! expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                total += current * Delay.MINUTE;
                cv = false;
                continue;
            }

            if (token.equalsIgnoreCase("s")) {
                if (cv == false) {
                    throw new ParseException(
                            "Specified 's' token, but no value defined ! expression='"
                                    + value + "'  token='" + token + "'", 0);
                }
                total += current * Delay.SECOND;
                cv = false;
                continue;
            }

            throw new ParseException("Specified unknown token ! expression='"
                    + value + "'  token='" + token + "'", 0);
        }

        if (cv == true) {
            total += current;
        }

        return total;
    }

    public static final String format(long delay) {

        double d;
        String unit;
        if (delay >= Delay.YEAR) {
            d = delay / (double) Delay.YEAR;
            unit = "year";

        } else if (delay >= Delay.MONTH) {
            d = delay / (double) Delay.MONTH;
            unit = "month";

        } else if (delay >= Delay.WEEK) {
            d = delay / (double) Delay.WEEK;
            unit = "week";

        } else if (delay >= Delay.HOUR * 24) {
            d = delay / (double) Delay.DAY;
            unit = "day";

        } else if (delay >= Delay.MINUTE * 60) {
            d = delay / (double) Delay.HOUR;
            unit = "hour";

        } else if (delay >= Delay.SECOND * 60) {
            d = delay / (double) Delay.MINUTE;
            unit = "minute";

        } else if (delay >= Delay.SECOND) {
            d = delay / (double) Delay.SECOND;
            unit = "minute";

        } else if (delay > 0) {
            d = delay;
            unit = "millisecond";

        } else {
            d = delay;
            unit = "never";
        }

        long floor = (int) Math.floor(d);
        if (floor != d) {
            return delayNumberFormat.format(d) + " " + unit
                    + ((d > 1.0) ? "s" : "");
        }
        return floor + " " + unit + ((d > 1.0) ? "s" : "");
    }

    static {
        delayNumberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        if (delayNumberFormat instanceof DecimalFormat) {
            ((DecimalFormat) delayNumberFormat).applyPattern("###0.00");
        }

    }
}
