/*
 * $Id$
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
import org.rcfaces.core.internal.repository.AbstractRepository.AbstractContent;
import org.rcfaces.core.internal.repository.IRepository.IContent;
import org.rcfaces.core.internal.repository.IRepository.IContentProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class URLContentProvider implements IContentProvider {

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

        int idx0 = localized.lastIndexOf('.');
        if (idx0 <= 0) {
            return null;
        }

        String variant = locale.getVariant();
        String country = locale.getCountry();
        String language = locale.getLanguage();

        String suffix = "";
        String baseURL = localized;
        if (localized.lastIndexOf('/') < idx0) {
            baseURL = localized.substring(0, idx0);
            suffix = localized.substring(idx0);
        }

        if (language.length() > 0) {
            int idx2 = baseURL.lastIndexOf('/');

            baseURL = baseURL.substring(0, idx2) + '/' + language
                    + baseURL.substring(idx2);
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("searchLocalizedContentReference ref='"
                    + contentReference + "' locale='" + locale
                    + "' => baseURL=" + baseURL);
        }

        try {
            if (variant != null && variant.length() > 0) {
                URL l = new URL(baseURL + "_" + language + "_" + country + "_"
                        + variant + suffix);

                Locale tryLocale = locale;
                if (testURL(l, tryLocale)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + tryLocale);
                    }

                    return l;
                }
            }

            if (country != null && country.length() > 0) {
                URL l = new URL(baseURL + "_" + language + "_" + country
                        + suffix);

                Locale tryLocale = locale;
                if (variant != null && variant.length() > 0) {
                    tryLocale = new Locale(language, country);
                }

                if (testURL(l, tryLocale)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + tryLocale);
                    }

                    return l;
                }
            }

            if (language != null && language.length() > 0) {
                URL l = new URL(baseURL + "_" + language + suffix);

                Locale tryLocale = locale;
                if ((country != null && country.length() > 0)
                        || (variant != null && variant.length() > 0)) {
                    tryLocale = new Locale(language);
                }

                if (testURL(l, tryLocale)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Localized version '" + locale
                                + "' found for url '" + localized + "' => "
                                + tryLocale);
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

    private boolean testURL(URL contentReference, Locale locale) {
        IContent content = getContent(contentReference, locale);

        if (LOG.isTraceEnabled()) {
            LOG.trace("TestURL '" + contentReference + "' locale='" + locale
                    + "'");
        }

        InputStream inputStream;
        try {
            inputStream = content.getInputStream();

        } catch (IOException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("URL '" + contentReference + "' does not exist !", ex);
            }
            return false;
        }

        if (inputStream == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("TestURL '" + contentReference + "' locale='"
                        + locale + "' => NOT FOUND");
            }

            return false;
        }

        try {
            inputStream.close();

        } catch (IOException ex) {
            LOG.info("Can not close URL '" + contentReference + "'.", ex);
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("TestURL '" + contentReference + "' locale='" + locale
                    + "' => FOUND");
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

        @Override
        public long getLastModified() throws IOException {
            URLConnection urlConnection = getURLConnection();

            return urlConnection.getLastModified();
        }

        @Override
        public long getLength() throws IOException {
            URLConnection urlConnection = getURLConnection();

            return urlConnection.getContentLength();
        }

        @Override
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
