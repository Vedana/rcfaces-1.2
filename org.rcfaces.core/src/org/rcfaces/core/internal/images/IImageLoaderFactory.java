/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageLoaderFactory {

    String getName();

    IImageLoader loadImage(ServletContext context, HttpServletRequest request,
            HttpServletResponse response, String uri);

    public interface IImageLoader {

        boolean isErrored();

        InputStream openStream();

        int getContentLength();

        long getLastModified();

        String getContentType();
    }

}
