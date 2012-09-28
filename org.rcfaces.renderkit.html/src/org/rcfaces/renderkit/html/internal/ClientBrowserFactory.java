/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.renderkit.html.internal.agent.IClientBrowser;
import org.rcfaces.renderkit.html.internal.agent.IUserAgent.BrowserType;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientBrowserFactory {

    private static final Log LOG = LogFactory
            .getLog(ClientBrowserFactory.class);

    private static final Map<BrowserType, IClientBrowser> USER_AGENTS = new HashMap<IClientBrowser.BrowserType, IClientBrowser>();
    static {
        USER_AGENTS.put(BrowserType.UNKNOWN, new ClientBrowserImpl(null,
                BrowserType.UNKNOWN, 0, 0, 0, "", null));

        USER_AGENTS.put(BrowserType.MICROSOFT_INTERNET_EXPLORER,
                new ClientBrowserImpl(null,
                        BrowserType.MICROSOFT_INTERNET_EXPLORER, 0, 0, 0, "ie",
                        null));

        USER_AGENTS.put(BrowserType.FIREFOX, new ClientBrowserImpl(null,
                BrowserType.FIREFOX, 0, 0, 0, "ff", null));

        USER_AGENTS.put(BrowserType.SAFARI, new ClientBrowserImpl(null,
                BrowserType.SAFARI, 0, 0, 0, "sa", null));

        USER_AGENTS.put(BrowserType.OPERA, new ClientBrowserImpl(null,
                BrowserType.OPERA, 0, 0, 0, "op", null));

        USER_AGENTS.put(BrowserType.CHROME, new ClientBrowserImpl(null,
                BrowserType.CHROME, 0, 0, 0, "ch", null));
    }

    private static ClientBrowserFactory SINGLETON = new ClientBrowserFactory();

    public static ClientBrowserFactory Get() {
        return SINGLETON;
    }

    private ClientBrowserFactory() {

    }

    public IClientBrowser get(FacesContext facesContext) {

        Map<String, String> requestHeaderMap = facesContext
                .getExternalContext().getRequestHeaderMap();

        String userAgent = requestHeaderMap
                .get(ConfiguredHttpServlet.USER_AGENT);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get client browser from facesContext='" + userAgent
                    + "'");
        }

        return getClientBrowserByUserAgent(userAgent);
    }

    public IClientBrowser get(HttpServletRequest request) {

        String userAgent = request.getHeader(ConfiguredHttpServlet.USER_AGENT);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get client browser from httpServletRequest='"
                    + userAgent + "'");
        }

        return getClientBrowserByUserAgent(userAgent);
    }

    public IClientBrowser getClientBrowserByUserAgent(String userAgent) {

        if (userAgent == null) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get client browser from userAgent: userAgent is NULL");
            }
            return USER_AGENTS.get(BrowserType.UNKNOWN);
        }

        String ua = userAgent.toLowerCase().trim();

        BrowserType type = BrowserType.UNKNOWN;
        String version = null;
        Boolean isMobileVersion = null;

        int idx = ua.indexOf("msie");
        if (idx >= 0) {
            type = BrowserType.MICROSOFT_INTERNET_EXPLORER;
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
                type = BrowserType.OPERA;
                version = searchVersion(ua, idx);

            } else {
                idx = ua.indexOf("chrome");
                if (idx >= 0) {
                    type = BrowserType.CHROME;
                    int idx2 = ua.indexOf('/', idx);
                    int idx3 = ua.indexOf(' ', idx);
                    if (idx3 < 0) {
                        idx3 = ua.length();
                    }

                    if (idx2 > idx && idx3 > idx2) {
                        version = ua.substring(idx2 + 1, idx3).trim();
                    }

                } else {
                    idx = ua.indexOf("safari");
                    if (idx >= 0) {
                        idx = ua.indexOf("version");
                        type = BrowserType.SAFARI;
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
                            type = BrowserType.FIREFOX;
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

        IClientBrowser clientBrowser = new ClientBrowserImpl(userAgent, type,
                major, minor, release, USER_AGENTS.get(type).getBrowserId(),
                isMobileVersion);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get client browser from userAgent='" + userAgent
                    + "' => " + clientBrowser);
        }

        return clientBrowser;
    }

    private String searchVersion(String ua, int index) {
        for (; index < ua.length(); index++) {
            char ch = ua.charAt(index);

            if (Character.isLetter(ch)) {
                continue;
            }

            break;
        }

        if (index == ua.length()) {
            return null;
        }

        if (ua.charAt(index++) != '/') {
            return null;
        }

        int startIndex = index;

        for (; index < ua.length(); index++) {
            char ch = ua.charAt(index);

            if (Character.isDigit(ch) || ch == '.') {
                continue;
            }

            break;
        }

        return ua.substring(startIndex, index);
    }

    public IClientBrowser getClientBrowserById(String browserId) {
        for (IClientBrowser clientBrowser : USER_AGENTS.values()) {

            if (clientBrowser.getBrowserId().equalsIgnoreCase(browserId) == false) {
                continue;
            }

            return clientBrowser;
        }

        return USER_AGENTS.get(BrowserType.UNKNOWN);
    }
}
