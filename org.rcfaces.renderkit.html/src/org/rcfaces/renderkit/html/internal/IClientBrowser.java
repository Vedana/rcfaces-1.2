/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientBrowser {

    public enum BrowserType {
        UNKNOWN, MICROSOFT_INTERNET_EXPLORER, FIREFOX, SAFARI, OPERA, CHROME
    }

    BrowserType getBrowserType();

    String getUserAgent();

    int getMajorVersion();

    int getMinorVersion();

    int getReleaseVersion();

    String getBrowserId();

    /**
     * 
     * @return Boolean if mobile version is determined, or <code>null</code> if
     *         not detected.
     */
    Boolean isMobileVersion();

    boolean equalsType(IClientBrowser clientBrowser);
}
