/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.repository.IRepository.IContent;
import org.rcfaces.core.internal.util.FilteredContentProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResourcesBundleContentProvider extends FilteredContentProvider {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ResourcesBundleContentProvider.class);

    private static final String JAVASCRIPT_CHARSET = "UTF-8";

    private final String resourceBundleBaseName;

    public ResourcesBundleContentProvider(String resourceBundleBaseName) {
        this.resourceBundleBaseName = resourceBundleBaseName;
    }

    protected String getCharset() {
        return JAVASCRIPT_CHARSET;
    }

    protected String updateBuffer(String buffer, URL url, Locale locale) {

        if (locale == null) {
            return super.updateBuffer(buffer, url, locale);
        }

        // XXX ??? pourquoi ???
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                resourceBundleBaseName, locale);

        StringAppender sa = new StringAppender(buffer, 8000);

        return super.updateBuffer(sa.toString(), url, locale);
    }

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            return loader;
        }

        return fallbackClass.getClass().getClassLoader();
    }
}
