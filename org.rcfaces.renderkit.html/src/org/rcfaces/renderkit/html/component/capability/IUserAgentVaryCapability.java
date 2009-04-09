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

    String FIREFOX = "firefox";

    String OPERA = "opera";

    String SAFARI = "safari";

    String getUserAgent();

    void setUserAgent(String userAgent);
}
