/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.webapp;

import java.io.Serializable;
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExpirationDate implements Serializable {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 8408879197958606825L;

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

        long d = 0;
        if (expiresDate > 0) {
            d = expiresDate;

            if (LOG.isTraceEnabled()) {
                LOG.trace("Expiration date is setted.");
            }

        } else if (expiresDelay > 0) {
            d = System.currentTimeMillis() + expiresDelay;

            if (expiresMaxAge != null) {
                response.setHeader(ConfiguredHttpServlet.HTTP_CACHE_CONTROL,
                        expiresMaxAge);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Compute expiration date from delay: " + expiresDelay
                        / Delay.SECOND + "s");
            }
        }

        if (d > 0) {
            d -= (d % 1000); // Retire les ms !

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set expiration date to " + d + " (" + new Date(d)
                        + ")");
            }

            response.setDateHeader(ConfiguredHttpServlet.HTTP_EXPIRES, d);

        } else if (LOG.isDebugEnabled()) {
            LOG.debug("No expiration defined");
        }

    }

    public long getExpiresDate() {
        return expiresDate;
    }

    public long getExpiresDelay() {
        return expiresDelay;
    }

}
