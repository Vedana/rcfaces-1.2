/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
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
