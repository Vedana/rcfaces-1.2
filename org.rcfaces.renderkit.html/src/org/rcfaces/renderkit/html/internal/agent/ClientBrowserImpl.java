/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.agent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ClientBrowserImpl implements IClientBrowser {

    private static final Log LOG = LogFactory.getLog(ClientBrowserImpl.class);

    private final String userAgent;

    private final BrowserType browserType;

    private final Integer majorVersion;

    private final Integer minorVersion;

    private final Integer releaseVersion;

    private final String browserId;

    private final Boolean isMobileVersion;

    private final String browserIdVersion;

    ClientBrowserImpl(String userAgent, BrowserType browserType,
            Integer majorVersion, Integer minorVersion, Integer releaseVersion,
            String browserId, Boolean isMobileVersion) {

        this.userAgent = userAgent;
        this.browserType = browserType;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.releaseVersion = releaseVersion;
        this.browserId = browserId;
        this.isMobileVersion = isMobileVersion;

        StringBuilder sb = new StringBuilder(128);
        sb.append(browserId);
        if (majorVersion != null) {
            sb.append('.').append(majorVersion);

            if (minorVersion != null) {
                sb.append('.').append(minorVersion);

                if (releaseVersion != null) {
                    sb.append('.').append(releaseVersion);
                }
            }
        }
        browserIdVersion = sb.toString();
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public Integer getReleaseVersion() {
        return releaseVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getBrowserId() {
        return browserId;
    }

    public String getBrowserIdAndVersion() {
        return browserIdVersion;
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
        result = prime * result
                + ((majorVersion == null) ? 0 : majorVersion.hashCode());
        result = prime * result
                + ((minorVersion == null) ? 0 : minorVersion.hashCode());
        result = prime * result
                + ((releaseVersion == null) ? 0 : releaseVersion.hashCode());
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
        if (majorVersion == null) {
            if (other.majorVersion != null)
                return false;
        } else if (!majorVersion.equals(other.majorVersion))
            return false;
        if (minorVersion == null) {
            if (other.minorVersion != null)
                return false;
        } else if (!minorVersion.equals(other.minorVersion))
            return false;
        if (releaseVersion == null) {
            if (other.releaseVersion != null)
                return false;
        } else if (!releaseVersion.equals(other.releaseVersion))
            return false;
        if (userAgent == null) {
            if (other.userAgent != null)
                return false;
        } else if (!userAgent.equals(other.userAgent))
            return false;
        return true;
    }

    public void textForm(StringBuilder sb) {
        sb.append(getBrowserType().shortName());

        if (getMajorVersion() != null) {
            sb.append('/').append(getMajorVersion());

            if (getMinorVersion() != null) {
                sb.append('.').append(getMinorVersion());

                if (getReleaseVersion() != null) {
                    sb.append('.').append(getReleaseVersion());
                }
            }
        }
    }

    public String toString() {
        return "[ClientBrowserImpl browserId='" + browserId + "', browserType="
                + browserType + ", majorVersion=" + majorVersion
                + ", minorVersion=" + minorVersion + ", releaseVersion="
                + releaseVersion + ", isMobileVersion=" + isMobileVersion
                + " userAgent='" + userAgent + "']";
    }

}
