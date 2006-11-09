/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ResourceImageLoaderFactory extends AbstractImageLoaderFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ResourceImageLoaderFactory.class);

    public String getName() {
        return "Load image by classloader context";
    }

    public IImageLoader loadImage(ServletContext context,
            HttpServletRequest request, HttpServletResponse response, String uri) {
        return new ResourceImageLoader(context, uri);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ResourceImageLoader implements IImageLoader {
        private static final String REVISION = "$Revision$";

        private final ServletContext servletContext;

        private final String url;

        private URLConnection urlConnection;

        private boolean errored;

        private int contentLength;

        private long lastModified;

        private String contentType;

        public ResourceImageLoader(ServletContext servletContext, String url) {
            this.servletContext = servletContext;
            this.url = url;
        }

        public boolean isErrored() {
            if (errored == false && urlConnection == null) {
                openURLConnection();
            }
            return errored;
        }

        private void openURLConnection() {

            URL imageURL;
            try {
                imageURL = servletContext.getResource("/" + url);

            } catch (MalformedURLException ex) {
                LOG.error("Malformed url '" + url + "'.", ex);
                errored = true;
                return;
            }

            if (imageURL == null) {
                LOG.error("Can not get image specified by path '" + url + "'.");
                errored = true;
                return;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Search resource '/" + url + "' => '" + imageURL
                        + "'.");
            }

            try {
                urlConnection = imageURL.openConnection();
                if (urlConnection == null) {
                    LOG.error("Can not get image specified by path '" + url
                            + "'.");
                    errored = true;
                    return;
                }

                contentLength = urlConnection.getContentLength();
                lastModified = urlConnection.getLastModified();
                contentType = urlConnection.getContentType();

                if (LOG.isDebugEnabled()) {
                    LOG
                            .debug("Resource '"
                                    + imageURL
                                    + "' contentType='"
                                    + contentType
                                    + "' contentLength="
                                    + contentLength
                                    + " lastModified="
                                    + ((lastModified > 0) ? new Date(
                                            lastModified).toString() : String
                                            .valueOf(lastModified)));
                }

            } catch (IOException ex) {
                LOG.error("Can not get content of image '" + imageURL + "'.",
                        ex);
                errored = true;
            }
        }

        public InputStream openStream() {
            if (isErrored()) {
                return null;
            }

            try {
                InputStream ins = urlConnection.getInputStream();
                if (ins != null) {
                    return ins;
                }

                LOG.error("Can not get image specified by path '" + url + "'.");
                errored = true;
                return null;

            } catch (IOException ex) {
                LOG.error("Can not get content of image '" + url + "'.", ex);
                errored = true;
                return null;
            }
        }

        public int getContentLength() {
            if (isErrored()) {
                return -1;
            }
            return contentLength;
        }

        public long getLastModified() {
            if (isErrored()) {
                return -1;
            }
            return lastModified;
        }

        public String getContentType() {
            if (isErrored()) {
                return null;
            }
            return contentType;
        }

    }
}