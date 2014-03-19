/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.agent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientBrowser extends IUserAgent {

    String getUserAgent();

    String getBrowserId();

    String getBrowserIdAndVersion();

    /**
     * 
     * @return Boolean if mobile version is determined, or <code>null</code> if
     *         not detected.
     */
    Boolean isMobileVersion();
}
