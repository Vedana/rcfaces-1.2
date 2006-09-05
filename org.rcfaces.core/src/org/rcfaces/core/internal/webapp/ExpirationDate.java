/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.core.internal.webapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.Delay;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ExpirationDate {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ExpirationDate.class);

    private final long expiresDate;

    private final long expiresDelay;

    private final String expiresMaxAge;

    protected ExpirationDate(long expires, long expiresDate) {
        this.expiresDate = expiresDate;

        this.expiresDelay = expires;
        if (expires >= Delay.SECOND) {
            this.expiresMaxAge = "max-age=" + (expires / Delay.SECOND); // en

        } else {
            this.expiresMaxAge = null;
        }
    }

    public static ExpirationDate parse(String servletName,
            String expireProperty, String expiresValue) {

        if (expiresValue.indexOf('/') >= 0) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            long expiresDate;
            try {
                Date date = dateFormat.parse(expiresValue);

                expiresDate = date.getTime();

            } catch (ParseException e) {
                LOG.error("Can not parse date attribute ('" + expireProperty
                        + "') value='" + expiresValue + "'  for sevlet '"
                        + servletName + "'.", e);

                IllegalArgumentException ex = new IllegalArgumentException(
                        "Can not parse date attribute (" + expireProperty
                                + "') value='" + expiresValue + "'.");

                ex.initCause(e);

                throw ex;
            }

            return new ExpirationDate(-1, expiresDate);
        }

        long expires;

        try {
            expires = Delay.parseDelay(expiresValue);

        } catch (ParseException e) {
            LOG.error("Can not parse expire attribute ('" + expireProperty
                    + "') value='" + expiresValue + "'  for sevlet '"
                    + servletName + "'.", e);

            IllegalArgumentException ex = new IllegalArgumentException(
                    "Can not parse expire attribute ('" + expireProperty
                            + "') value='" + expiresValue + "'.");

            ex.initCause(e);

            throw ex;
        }

        return new ExpirationDate(expires, -1);
    }

    public static ExpirationDate fromDelay(long delay) {
        return new ExpirationDate(delay, -1);
    }

    public static ExpirationDate noExpiration() {
        return new ExpirationDate(-1, -1);
    }

    public void sendExpires(HttpServletResponse response) {

        if (expiresDate > 0) {
            response.setDateHeader(ParametredHttpServlet.HTTP_EXPIRES,
                    expiresDate);

            if (LOG.isTraceEnabled()) {
                LOG.trace("Set expiration date to " + expiresDate);
            }

            return;
        }

        if (expiresDelay > 0) {
            long d = System.currentTimeMillis() + expiresDelay;
            response.setDateHeader(ParametredHttpServlet.HTTP_EXPIRES, d);

            if (expiresMaxAge != null) {
                response.setHeader(ParametredHttpServlet.HTTP_CACHE_CONTROL,
                        expiresMaxAge);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Compute expiration date: " + new Date(d)
                        + " (delay=" + expiresDelay / Delay.SECOND + "s)");
            }

            return;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("No expiration defined");
        }

    }

    public long getExpiresDate() {
        return expiresDate;
    }

    public long getExpiresDelay() {
        return expiresDelay;
    }

}
