/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.core.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.core.internal.webapp.IRepository.IContentProvider;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicContentProvider implements IContentProvider {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicContentProvider.class);

    public static final IContentProvider SINGLETON = new BasicContentProvider();

    protected BasicContentProvider() {
    }

    public IContent getContent(URL url, Locale locale) {
        return new BasicContent(url, locale);
    }

    public boolean testURL(URL url) {
        IContent content = getContent(url, null);

        InputStream inputStream;
        try {
            inputStream = content.getInputStream();

        } catch (IOException ex) {
            // LOG.info("URL '" + url + "' does not exist !", ex);
            return false;
        }

        if (inputStream == null) {
            return false;
        }

        try {
            inputStream.close();

        } catch (IOException ex) {
            LOG.info("Can not close URL '" + url + "'.", ex);
        }

        return true;
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected static class BasicContent implements IContent {
        private static final String REVISION = "$Revision$";

        protected final URL url;

        protected final Locale locale;

        private URLConnection urlConnection;

        private boolean opened;

        public BasicContent(URL url, Locale locale) {
            this.url = url;
            this.locale = locale;
        }

        public InputStream getInputStream() throws IOException {
            return getInputStream(false);
        }

        protected InputStream getInputStream(boolean toClose)
                throws IOException {
            if (opened) {
                throw new IOException("Already opened !");
            }
            opened = true;

            return openInputStream(toClose);
        }

        protected InputStream openInputStream(boolean toClose)
                throws IOException {
            return getURLConnection().getInputStream();
        }

        protected URLConnection getURLConnection() throws IOException {
            if (urlConnection != null) {
                return urlConnection;
            }

            urlConnection = url.openConnection();

            return urlConnection;
        }

        public long getLastModified() throws IOException {
            URLConnection urlConnection = getURLConnection();

            return urlConnection.getLastModified();
        }

        public long getLength() throws IOException {
            URLConnection urlConnection = getURLConnection();

            return urlConnection.getContentLength();
        }

        public void release() {
            if (urlConnection == null || opened) {
                return;
            }

            try {
                InputStream in = getInputStream(true);
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }

            urlConnection = null;
        }
    }
}
