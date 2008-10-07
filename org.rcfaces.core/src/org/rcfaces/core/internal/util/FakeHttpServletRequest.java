/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FakeHttpServletRequest extends FakeServletRequest implements
        HttpServletRequest {
    private static final String REVISION = "$Revision$";

    public static final HttpServletRequest SINGLETON = new FakeHttpServletRequest();

    public String getAuthType() {

        return null;
    }

    public String getContextPath() {

        return null;
    }

    public Cookie[] getCookies() {

        return null;
    }

    public long getDateHeader(String arg0) {

        return 0;
    }

    public String getHeader(String arg0) {

        return null;
    }

    public Enumeration getHeaderNames() {

        return null;
    }

    public Enumeration getHeaders(String arg0) {

        return null;
    }

    public int getIntHeader(String arg0) {

        return 0;
    }

    public String getMethod() {

        return null;
    }

    public String getPathInfo() {

        return null;
    }

    public String getPathTranslated() {

        return null;
    }

    public String getQueryString() {

        return null;
    }

    public String getRemoteUser() {

        return null;
    }

    public String getRequestURI() {

        return null;
    }

    public StringBuffer getRequestURL() {

        return null;
    }

    public String getRequestedSessionId() {

        return null;
    }

    public String getServletPath() {

        return null;
    }

    public HttpSession getSession() {

        return null;
    }

    public HttpSession getSession(boolean arg0) {

        return null;
    }

    public Principal getUserPrincipal() {

        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {

        return false;
    }

    public boolean isRequestedSessionIdFromURL() {

        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {

        return false;
    }

    public boolean isRequestedSessionIdValid() {

        return false;
    }

    public boolean isUserInRole(String arg0) {

        return false;
    }

}
