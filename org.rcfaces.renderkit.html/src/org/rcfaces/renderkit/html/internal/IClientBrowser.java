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

    int UNKNOWN_BROWSER_TYPE = 0;

    int MICROSOFT_INTERNET_EXPLORER_BROWSER_TYPE = 1;

    int FIREFOX_BROWSER_TYPE = 2;

    int SAFARI_BROWSER_TYPE = 3;

    int OPERA_BROWSER_TYPE = 4;

    int CHROME_BROWSER_TYPE = 5;

    int getBrowserType();

    String getUserAgent();

    int getMajorVersion();

    int getMinorVersion();

    int getReleaseVersion();

    String getBrowserId();

    boolean equalsType(IClientBrowser clientBrowser);
}
