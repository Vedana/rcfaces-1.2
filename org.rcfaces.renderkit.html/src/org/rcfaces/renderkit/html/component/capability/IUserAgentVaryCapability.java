/*
 * $Id$
 */
package org.rcfaces.renderkit.html.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUserAgentVaryCapability {

    String MICROSOFT_INTERNET_EXPLORER = "msie";

    String INTERNET_EXPLORER = "ie";

    String FIREFOX = "firefox";

    String OPERA = "opera";

    String SAFARI = "safari";

    String CHROME = "chrome";

    String SUPPORTED_AGENT_NAMES[] = { MICROSOFT_INTERNET_EXPLORER, FIREFOX,
            OPERA, SAFARI, CHROME };

    String getUserAgent();

    void setUserAgent(String userAgent);
}
