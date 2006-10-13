/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
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
