/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.rcfaces.core.internal.webapp.ExtendedHttpServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class IncludeHttpServletRequest extends HttpServletRequestWrapper {
    private static final String REVISION = "$Revision$";

    private static final Set IGNORED_HEADER = new HashSet(8);
    static {
        IGNORED_HEADER.add(ExtendedHttpServlet.HTTP_IF_MODIFIED_SINCE);
        IGNORED_HEADER.add(ExtendedHttpServlet.HTTP_IF_NONE_MATCH);
        IGNORED_HEADER.add(ExtendedHttpServlet.HTTP_LAST_MODIFIED);
        IGNORED_HEADER.add(ExtendedHttpServlet.HTTP_IF_NOT_HASH);
    }

    public IncludeHttpServletRequest(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public long getDateHeader(String headerName) {
        if (IGNORED_HEADER.contains(headerName)) {
            return -1;
        }
        return super.getDateHeader(headerName);
    }

    public String getHeader(String headerName) {
        if (IGNORED_HEADER.contains(headerName)) {
            return null;
        }
        return super.getHeader(headerName);
    }

    public int getIntHeader(String headerName) {
        if (IGNORED_HEADER.contains(headerName)) {
            return -1;
        }

        return super.getIntHeader(headerName);
    }

    public String getParameter(String parameterName) {
        if (IGNORED_HEADER.contains(parameterName)) {
            return null;
        }

        return super.getParameter(parameterName);
    }

    public Enumeration getHeaders(String parameterName) {
        if (IGNORED_HEADER.contains(parameterName)) {
            return new Vector().elements();
        }
        return super.getHeaders(parameterName);
    }

    public String[] getParameterValues(String parameterName) {
        if (IGNORED_HEADER.contains(parameterName)) {
            return null;
        }

        return super.getParameterValues(parameterName);
    }

    public String getMethod() {
        return "GET";
    }

    public String getScheme() {
        return "http";
    }

}
