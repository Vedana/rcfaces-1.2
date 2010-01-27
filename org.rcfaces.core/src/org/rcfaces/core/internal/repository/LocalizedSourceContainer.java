/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.repository;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class LocalizedSourceContainer extends SourceContainer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(LocalizedSourceContainer.class);

    private final String localizedSuffixes[];

    public LocalizedSourceContainer(ServletConfig config,
            String repositoryType, Set modules, String charSet,
            boolean canUseGzip, boolean canUseETag, boolean canUseHash,
            String externalRepositoriesPropertyName, String repositoryVersion)
            throws ServletException {
        super(config, repositoryType, modules, charSet, canUseGzip, canUseETag,
                canUseHash, externalRepositoriesPropertyName, repositoryVersion);

        this.localizedSuffixes = getLocalizedSuffixes(config);
    }

    protected URL getURL(String path) {
        for (int i = 0; i < localizedSuffixes.length; i++) {
            String p = path + localizedSuffixes[i];

            URL url = super.getURL(p);
            if (url != null) {
                return url;
            }
        }

        return null;
    }

    protected String[] getLocalizedSuffixes(ServletConfig config) {
        Locale locale = Locale.getDefault();

        List l = new ArrayList();

        if (locale != null) {
            String language = locale.getLanguage();
            if (language != null && language.length() > 0) {
                String country = locale.getCountry();
                if (country != null && country.length() > 0) {
                    String variant = locale.getVariant();
                    if (variant != null && variant.length() > 0) {
                        l.add("_" + language + "_" + country + "_" + variant);
                    }

                    l.add("_" + language + "_" + country);
                }
                l.add("_" + language);
            }
        }

        l.add("");

        return (String[]) l.toArray(new String[l.size()]);
    }
}
