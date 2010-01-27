/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ClientBrowserImpl implements IClientBrowser {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ClientBrowserImpl.class);

    private final String userAgent;

    private final int browserType;

    private final int majorVersion;

    private final int minorVersion;

    private final int releaseVersion;

    private final String browserId;

    ClientBrowserImpl(String userAgent, int browserType, int majorVersion,
            int minorVersion, int releaseVersion, String browserId) {

        this.userAgent = userAgent;
        this.browserType = browserType;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.releaseVersion = releaseVersion;
        this.browserId = browserId;
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

    public String getBrowserId() {
        return browserId;
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

    public boolean equalsType(IClientBrowser clientBrowser) {
        if (clientBrowser == null) {
            return false;
        }

        return getBrowserId().equals(clientBrowser.getBrowserId());
    }

    public String toString() {
        return "[ClientBrowserImpl browserId=" + browserId + ", browserType="
                + browserType + ", majorVersion=" + majorVersion
                + ", minorVersion=" + minorVersion + ", releaseVersion="
                + releaseVersion + ", userAgent=" + userAgent + "]";
    }

}
