/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.renderkit.html.internal.agent.IClientBrowser;
import org.rcfaces.renderkit.html.internal.agent.IUserAgent.BrowserType;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ClientBrowserImpl implements IClientBrowser {

    private static final Log LOG = LogFactory.getLog(ClientBrowserImpl.class);

    private final String userAgent;

    private final BrowserType browserType;

    private final int majorVersion;

    private final int minorVersion;

    private final int releaseVersion;

    private final String browserId;

    private final Boolean isMobileVersion;

    ClientBrowserImpl(String userAgent, BrowserType browserType,
            int majorVersion, int minorVersion, int releaseVersion,
            String browserId, Boolean isMobileVersion) {

        this.userAgent = userAgent;
        this.browserType = browserType;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.releaseVersion = releaseVersion;
        this.browserId = browserId;
        this.isMobileVersion = isMobileVersion;
    }

    public BrowserType getBrowserType() {
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

    public Boolean isMobileVersion() {
        return isMobileVersion;
    }

    public boolean equalsType(IClientBrowser clientBrowser) {
        if (clientBrowser == null) {
            return false;
        }

        return getBrowserId().equals(clientBrowser.getBrowserId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((browserId == null) ? 0 : browserId.hashCode());
        result = prime * result
                + ((browserType == null) ? 0 : browserType.hashCode());
        result = prime * result
                + ((isMobileVersion == null) ? 0 : isMobileVersion.hashCode());
        result = prime * result + majorVersion;
        result = prime * result + minorVersion;
        result = prime * result + releaseVersion;
        result = prime * result
                + ((userAgent == null) ? 0 : userAgent.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientBrowserImpl other = (ClientBrowserImpl) obj;
        if (browserId == null) {
            if (other.browserId != null)
                return false;
        } else if (!browserId.equals(other.browserId))
            return false;
        if (browserType != other.browserType)
            return false;
        if (isMobileVersion == null) {
            if (other.isMobileVersion != null)
                return false;
        } else if (!isMobileVersion.equals(other.isMobileVersion))
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

    public String toString() {
        return "[ClientBrowserImpl browserId='" + browserId + "', browserType="
                + browserType + ", majorVersion=" + majorVersion
                + ", minorVersion=" + minorVersion + ", releaseVersion="
                + releaseVersion + ", isMobileVersion=" + isMobileVersion
                + " userAgent='" + userAgent + "']";
    }
}