/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientBrowserImpl implements IClientBrowser {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ClientBrowserImpl.class);

    private static final IClientBrowser UNDEFINED_USER_AGENT = new ClientBrowserImpl(
            null, UNKNOWN_BROWSER_TYPE, 0, 0, 0);

    private final String userAgent;

    private final int browserType;

    private final int majorVersion;

    private final int minorVersion;

    private final int releaseVersion;

    private ClientBrowserImpl(String userAgent, int browserType,
            int majorVersion, int minorVersion, int releaseVersion) {

        this.userAgent = userAgent;
        this.browserType = browserType;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.releaseVersion = releaseVersion;
    }

    public int getBrowserType() {
        return browserType;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getReleaseVersion() {
        return releaseVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + browserType;
        result = prime * result + majorVersion;
        result = prime * result + minorVersion;
        result = prime * result + releaseVersion;
        result = prime * result
                + ((userAgent == null) ? 0 : userAgent.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientBrowserImpl other = (ClientBrowserImpl) obj;
        if (browserType != other.browserType)
            return false;
        if (majorVersion != other.majorVersion)
            return false;
        if (minorVersion != other.minorVersion)
            return false;
        if (releaseVersion != other.releaseVersion)
            return false;
        if (userAgent == null) {
            if (other.userAgent != null)
                return false;
        } else if (!userAgent.equals(other.userAgent))
            return false;
        return true;
    }

    public static IClientBrowser get(FacesContext facesContext) {

        String userAgent = (String) facesContext.getExternalContext()
                .getRequestHeaderMap().get(ConfiguredHttpServlet.USER_AGENT);

        if (userAgent == null) {
            return UNDEFINED_USER_AGENT;
        }

        String ua = userAgent.toLowerCase().trim();

        int type = UNKNOWN_BROWSER_TYPE;
        String version = null;

        int idx = ua.indexOf("msie");
        if (idx >= 0) {
            type = MICROSOFT_INTERNET_EXPLORER_BROWSER_TYPE;
            int idx2 = ua.indexOf(';', idx);
            int idx3 = ua.indexOf(')', idx);

            if (idx3 < idx2) {
                idx2 = idx3;
            }

            if (idx < idx2) {
                version = ua.substring(idx + 4, idx2).trim();
            }
        } else {
            idx = ua.indexOf("opera");
            if (idx >= 0) {
                type = OPERA_BROWSER_TYPE;
                int idx2 = ua.indexOf('/', idx);
                int idx3 = ua.indexOf('(', idx);

                if (idx2 > idx && idx3 > idx2) {
                    version = ua.substring(idx2 + 1, idx3).trim();
                }

            } else {
                idx = ua.indexOf("safari");
                if (idx >= 0) {
                    type = SAFARI_BROWSER_TYPE;
                    int idx2 = ua.indexOf('/', idx);
                    int idx3 = ua.indexOf(' ', idx);
                    if (idx3 < 0) {
                        idx3 = ua.length();
                    }

                    if (idx2 > idx && idx3 > idx2) {
                        version = ua.substring(idx2 + 1, idx3).trim();
                    }

                } else {
                    idx = ua.indexOf("firefox");
                    if (idx >= 0) {
                        type = FIREFOX_BROWSER_TYPE;
                        int idx2 = ua.indexOf('/', idx);
                        int idx3 = ua.indexOf(' ', idx);
                        if (idx3 < 0) {
                            idx3 = ua.length();
                        }

                        if (idx2 > idx && idx3 > idx2) {
                            version = ua.substring(idx2 + 1, idx3).trim();
                        }
                    }
                }
            }
        }

        int major = 0;
        int minor = 0;
        int release = 0;

        if (version != null) {
            StringTokenizer st = new StringTokenizer(version, ".");

            if (st.hasMoreTokens()) {
                try {
                    major = Integer.parseInt(st.nextToken());

                    if (st.hasMoreTokens()) {
                        minor = Integer.parseInt(st.nextToken());

                        if (st.hasMoreTokens()) {
                            release = Integer.parseInt(st.nextToken());
                        }
                    }

                } catch (NumberFormatException ex) {
                    LOG.error("Can not parse version '" + version
                            + "' from userAgent='" + userAgent + "'", ex);
                }
            }
        }

        return new ClientBrowserImpl(userAgent, type, major, minor, release);
    }

}
