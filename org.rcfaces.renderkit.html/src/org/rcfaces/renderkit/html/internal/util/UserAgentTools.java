/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.renderkit.html.component.capability.IUserAgentVaryCapability;
import org.rcfaces.renderkit.html.internal.ClientBrowserFactory;
import org.rcfaces.renderkit.html.internal.IClientBrowser;
import org.rcfaces.renderkit.html.internal.IClientBrowser.BrowserType;
import org.rcfaces.renderkit.html.item.IUserAgentVaryFileItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserAgentTools {

    private static final Log LOG = LogFactory.getLog(UserAgentTools.class);

    private static final String USER_AGENTS_PROPERTY = "org.rcfaces.renderkit.html.USER_AGENTS";

    private static final Map<String, BrowserType> AGENT_NAMES = new HashMap<String, BrowserType>();
    static {
        AGENT_NAMES.put(IUserAgentVaryCapability.MICROSOFT_INTERNET_EXPLORER,
                BrowserType.MICROSOFT_INTERNET_EXPLORER);
        AGENT_NAMES.put(IUserAgentVaryCapability.INTERNET_EXPLORER,
                BrowserType.MICROSOFT_INTERNET_EXPLORER);

        AGENT_NAMES.put(IUserAgentVaryCapability.FIREFOX, BrowserType.FIREFOX);

        AGENT_NAMES.put(IUserAgentVaryCapability.SAFARI, BrowserType.SAFARI);

        AGENT_NAMES.put(IUserAgentVaryCapability.OPERA, BrowserType.OPERA);

        AGENT_NAMES.put(IUserAgentVaryCapability.CHROME, BrowserType.CHROME);
    }

    public static boolean accept(FacesContext facesContext,
            IUserAgentVaryCapability userAgentVaryCapability) {
        String userAgent = userAgentVaryCapability.getUserAgent();

        return accept(facesContext, userAgent);
    }

    public static boolean accept(FacesContext facesContext,
            IUserAgentVaryFileItem userAgentVaryFileItem) {
        String userAgent = userAgentVaryFileItem.getUserAgent();

        return accept(facesContext, userAgent);
    }

    @SuppressWarnings("unchecked")
    private static boolean accept(FacesContext facesContext, String userAgent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Verify browser '" + userAgent + "'");
        }

        if (userAgent == null || userAgent.length() == 0) {
            return true;
        }

        userAgent = userAgent.trim();

        boolean not = false;
        if (userAgent.charAt(0) == '!') {
            not = true;
            userAgent = userAgent.substring(1);
        }

        Map<String, Object> requestMap = facesContext.getExternalContext()
                .getRequestMap();

        Map<String, Boolean> userAgentVersions = (Map<String, Boolean>) requestMap
                .get(USER_AGENTS_PROPERTY);
        if (userAgentVersions == null) {
            userAgentVersions = new HashMap<String, Boolean>(4);
            requestMap.put(USER_AGENTS_PROPERTY, userAgentVersions);

        } else {
            Boolean b = userAgentVersions.get(userAgent);

            if (b != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Cached browser '" + userAgent + "' value => "
                            + b + " not=" + not);
                }

                return b.booleanValue() == not;
            }
        }

        IClientBrowser client = ClientBrowserFactory.Get().get(facesContext);

        if (client == null) {
            // Pas de client détecté !

            if (LOG.isDebugEnabled()) {
                LOG.debug("No client detected return FALSE not=" + not);
            }

            userAgentVersions.put(userAgent, Boolean.FALSE);
            return not;
        }

        BrowserType browserType = client.getBrowserType();

        StringTokenizer st = new StringTokenizer(userAgent, ",");
        next_rule: for (; st.hasMoreTokens();) {
            String ruleAgent = st.nextToken().trim();
            String ruleVersion = null;

            int idx = ruleAgent.indexOf('/');
            if (idx > 0) {
                ruleVersion = ruleAgent.substring(idx + 1);
                ruleAgent = ruleAgent.substring(0, idx);
            }

            BrowserType ruleBrowserType = computeBrowserType(ruleAgent);
            if (ruleBrowserType == BrowserType.UNKNOWN) {
                LOG.info("Unknown browser '" + ruleAgent + "'");
                continue;
            }
            if (ruleBrowserType != browserType) {
                continue;
            }

            if (ruleVersion != null && ruleVersion.length() > 0) {
                int mode = 1;

                char c = ruleVersion.charAt(0);
                if (c == '>' || c == '<') {
                    if (ruleVersion.length() > 1
                            && ruleVersion.charAt(1) == '=') {
                        mode = (c == '>') ? 1 : 3;
                        ruleVersion = ruleVersion.substring(2);
                    } else {
                        mode = (c == '>') ? 0 : 4;
                        ruleVersion = ruleVersion.substring(1);
                    }
                } else if (c == '=') {
                    mode = 2;
                    ruleVersion = ruleVersion.substring(1);
                }

                StringTokenizer st2 = new StringTokenizer(ruleVersion, ".");

                for (int cnt = 0; st2.hasMoreTokens() && cnt < 3; cnt++) {
                    int va = Integer.parseInt(st2.nextToken());
                    int vb = 0;

                    switch (cnt) {
                    case 0:
                        vb = client.getMajorVersion();
                        break;

                    case 1:
                        vb = client.getMinorVersion();
                        break;

                    case 2:
                        vb = client.getReleaseVersion();
                        break;
                    }

                    switch (mode) {
                    case 0:
                        if (va >= vb) {
                            continue next_rule;
                        }
                        break;

                    case 1:
                        if (va > vb) {
                            continue next_rule;
                        }
                        break;

                    case 2:
                        if (va != vb) {
                            continue next_rule;
                        }
                        break;

                    case 3:
                        if (va < vb) {
                            continue next_rule;
                        }
                        break;

                    case 4:
                        if (va <= vb) {
                            continue next_rule;
                        }
                        break;
                    }
                }
            }

            return not == false;
        }

        userAgentVersions.put(userAgent, Boolean.FALSE);
        return not;
    }

    private static BrowserType computeBrowserType(String agent) {
        BrowserType type = AGENT_NAMES.get(agent.toLowerCase());
        if (type == null) {
            return BrowserType.UNKNOWN;
        }
        return type;
    }

}
