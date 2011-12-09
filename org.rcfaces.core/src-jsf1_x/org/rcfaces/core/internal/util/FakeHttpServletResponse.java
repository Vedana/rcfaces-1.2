/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FakeHttpServletResponse extends FakeServletResponse implements
        HttpServletResponse {
    private static final String REVISION = "$Revision$";

    public static final HttpServletResponse SINGLETON = new FakeHttpServletResponse();

    public void addCookie(Cookie arg0) {

    }

    public void addDateHeader(String arg0, long arg1) {

    }

    public void addHeader(String arg0, String arg1) {

    }

    public void addIntHeader(String arg0, int arg1) {

    }

    public boolean containsHeader(String arg0) {

        return false;
    }

    public String encodeRedirectURL(String arg0) {

        return null;
    }

    public String encodeRedirectUrl(String arg0) {

        return null;
    }

    public String encodeURL(String arg0) {

        return null;
    }

    public String encodeUrl(String arg0) {

        return null;
    }

    public void sendError(int arg0) throws IOException {

    }

    public void sendError(int arg0, String arg1) throws IOException {

    }

    public void sendRedirect(String arg0) throws IOException {

    }

    public void setDateHeader(String arg0, long arg1) {

    }

    public void setHeader(String arg0, String arg1) {

    }

    public void setIntHeader(String arg0, int arg1) {

    }

    public void setStatus(int arg0) {

    }

    public void setStatus(int arg0, String arg1) {

    }

}
