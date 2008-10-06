/*
 * $Id$
 */
package org.rcfaces.core.internal.resource;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResourceLoaderFactory {

    String getName();

    IResourceLoader loadResource(ServletContext context,
            HttpServletRequest request, HttpServletResponse response, String uri);

    public interface IResourceLoader {

        boolean isErrored();

        InputStream openStream();

        int getContentLength();

        long getLastModified();

        String getContentType();
    }

}
