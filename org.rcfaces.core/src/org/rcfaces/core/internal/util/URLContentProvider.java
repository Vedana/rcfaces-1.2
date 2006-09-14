/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.AbstractRepository.AbstractContent;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.core.internal.webapp.IRepository.IContentProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class URLContentProvider implements IContentProvider {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(URLContentProvider.class);

    public static final IContentProvider SINGLETON = new URLContentProvider();

    protected URLContentProvider() {
    }

    public IContent getContent(Object contentReference, Locale locale) {
        return new URLContent((URL) contentReference, locale);
    }

    public Object searchLocalizedContentReference(Object contentReference,
            Locale locale) {

        String localized = contentReference.toString();

        int idx = localized.lastIndexOf('.');
        if (idx <= 0) {
            return null;
        }

        String variant = locale.getVariant();
        String country = locale.getCountry();
        String language = locale.getLanguage();

        try {
            if (variant != null && variant.length() > 0) {
                URL l = new URL(localized.substring(0, idx) + "_"
                        + locale.getLanguage() + "_" + country + "_" + variant
                        + localized.substring(idx));

                if (testURL(l)) {
                    Locale foundLocale = new Locale(language);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + foundLocale);
                    }

                    return l;
                }
            }

            if (country != null && country.length() > 0) {
                URL l = new URL(localized.substring(0, idx) + "_" + language
                        + "_" + country + localized.substring(idx));

                if (testURL(l)) {
                    Locale foundLocale = new Locale(language, country);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + foundLocale);
                    }

                    return l;
                }
            }

            if (language != null && language.length() > 0) {
                URL l = new URL(localized.substring(0, idx) + "_" + language
                        + localized.substring(idx));

                if (testURL(l)) {
                    Locale foundLocale = new Locale(language);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + foundLocale);
                    }

                    return l;
                }
            }
        } catch (MalformedURLException ex) {
            LOG.error("Can not search localized url of uri '" + localized
                    + "' for locale '" + locale + "'.", ex);

            return null;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Localized version '" + locale + "' not found for url '"
                    + localized + "'.");
        }

        return null;
    }

    private boolean testURL(URL contentReference) {
        IContent content = getContent(contentReference, null);

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
            LOG.info("Can not close URL '" + contentReference + "'.", ex);
        }

        return true;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class URLContent extends AbstractContent {
        private static final String REVISION = "$Revision$";

        protected final URL url;

        protected final Locale locale;

        private URLConnection urlConnection;

        private boolean opened;

        public URLContent(URL url, Locale locale) {
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
