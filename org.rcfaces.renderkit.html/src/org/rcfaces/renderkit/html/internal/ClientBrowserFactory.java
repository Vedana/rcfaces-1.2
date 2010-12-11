/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientBrowserFactory {

    private static final Log LOG = LogFactory
            .getLog(ClientBrowserFactory.class);

    private static final IClientBrowser USER_AGENTS[] = {
            new ClientBrowserImpl(null, IClientBrowser.UNKNOWN_BROWSER_TYPE, 0,
                    0, 0, ""),
            new ClientBrowserImpl(null,
                    IClientBrowser.MICROSOFT_INTERNET_EXPLORER_BROWSER_TYPE, 0,
                    0, 0, "ie"),
            new ClientBrowserImpl(null, IClientBrowser.FIREFOX_BROWSER_TYPE, 0,
                    0, 0, "ff"),
            new ClientBrowserImpl(null, IClientBrowser.SAFARI_BROWSER_TYPE, 0,
                    0, 0, "sa"),
            new ClientBrowserImpl(null, IClientBrowser.OPERA_BROWSER_TYPE, 0,
                    0, 0, "op"),
            new ClientBrowserImpl(null, IClientBrowser.CHROME_BROWSER_TYPE, 0,
                    0, 0, "ch") };

    private static ClientBrowserFactory SINGLETON = new ClientBrowserFactory();

    public static ClientBrowserFactory Get() {
        return SINGLETON;
    }

    private ClientBrowserFactory() {

    }

    public IClientBrowser get(FacesContext facesContext) {

        String userAgent = (String) facesContext.getExternalContext()
                .getRequestHeaderMap().get(ConfiguredHttpServlet.USER_AGENT);

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
            return USER_AGENTS[IClientBrowser.UNKNOWN_BROWSER_TYPE];
        }

        String ua = userAgent.toLowerCase().trim();

        int type = IClientBrowser.UNKNOWN_BROWSER_TYPE;
        String version = null;

        int idx = ua.indexOf("msie");
        if (idx >= 0) {
            type = IClientBrowser.MICROSOFT_INTERNET_EXPLORER_BROWSER_TYPE;
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
                type = IClientBrowser.OPERA_BROWSER_TYPE;
                version = searchVersion(ua, idx);

            } else {
                idx = ua.indexOf("safari");
                if (idx >= 0) {
                    type = IClientBrowser.SAFARI_BROWSER_TYPE;
                    version = searchVersion(ua, idx);

                } else {
                    idx = ua.indexOf("firefox");
                    if (idx >= 0) {
                        type = IClientBrowser.FIREFOX_BROWSER_TYPE;
                        version = searchVersion(ua, idx);
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
                major, minor, release, USER_AGENTS[type].getBrowserId());

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
        for (int i = 0; i < USER_AGENTS.length; i++) {
            IClientBrowser clientBrowser = USER_AGENTS[i];

            if (clientBrowser.getBrowserId().equalsIgnoreCase(browserId) == false) {
                continue;
            }

            return clientBrowser;
        }

        return USER_AGENTS[IClientBrowser.UNKNOWN_BROWSER_TYPE];
    }
}
