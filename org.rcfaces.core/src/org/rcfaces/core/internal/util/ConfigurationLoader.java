/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.context.ExternalContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConfigurationLoader {

    public static final Log LOG = LogFactory.getLog(ConfigurationLoader.class);

    public static final String RCFACES_CONFIG_FILENAME = RcfacesContext.RCFACES_CONFIG_FILENAME;

    public static final String RCFACES_CONFIG_INIT_PARAMETER = RcfacesContext.RCFACES_CONFIG_FILES_PARAMETER;

    private static final String FACES_CONFIG_RESOURCE_NAME = "faces-config.xml";

    private static final String FACES_CONFIG_INIT_PARAMETER = "javax.faces.CONFIG_FILES";

    private final List urls;

    private ConfigurationLoader(List urls) {
        this.urls = urls;
    }

    public void parse(Digester digester) {

        if (urls.isEmpty()) {
            return;
        }

        for (Iterator it = urls.iterator(); it.hasNext();) {
            URL url = (URL) it.next();

            InputStream inputStream;
            try {
                inputStream = url.openStream();

            } catch (IOException e) {
                LOG.error("Can not open url '" + url + "'.", e);
                continue;
            }

            if (inputStream == null) {
                LOG.debug("Configuration file '" + url + "' does not exist.");
                continue;
            }

            parse(digester, inputStream, url.toString());
        }
    }

    private void parse(Digester digester, InputStream inputStream,
            String resourceName) {

        LOG.debug("Loading config file '" + resourceName + "' ...");

        try {
            digester.parse(inputStream);

        } catch (Throwable th) {
            LOG.error("Can not parse config file '" + resourceName + "'.", th);
            return;

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error("Can not close config file '" + resourceName + "'.",
                        e);
            }
        }

        LOG.debug("Config file '" + resourceName + "' loaded !");
    }

    public static ConfigurationLoader scanConfigurationURLs(
            ExternalContext externalContext, String resourceName,
            String resourceNameInitParameter, List urls) {

        if (urls == null) {
            urls = Collections.emptyList();
        }

        urls = new ArrayList(urls);

        ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        if (contextClassLoader != null) {
            String metaInfResourceName = "META-INF/" + resourceName;

            Enumeration enumeration = null;
            try {
                enumeration = contextClassLoader
                        .getResources(metaInfResourceName);

            } catch (IOException e) {
                LOG.error("Can not scan resources '" + metaInfResourceName
                        + "' into context classloader.");
            }

            if (enumeration != null) {
                for (; enumeration.hasMoreElements();) {
                    URL url = (URL) enumeration.nextElement();

                    if (urls.contains(url)) {
                        continue;
                    }

                    LOG.debug("Resource '" + metaInfResourceName
                            + "' detected : " + url);

                    urls.add(url);
                }
            }
        }

        String configFilenames = externalContext
                .getInitParameter(resourceNameInitParameter);

        if (configFilenames == null) {
            configFilenames = "";
        }

        configFilenames = "WEB-INF/" + resourceName + ", " + configFilenames;

        // LOG.debug("Value for parameter '"+
        // CAMELIA_CONFIG_FILES_PARAMETER + "' detected : '"+
        // configFilenames + "'.");

        StringTokenizer st = new StringTokenizer(configFilenames, ",;\t \r\n");

        Set alreadyLoaded = new HashSet();

        for (; st.hasMoreTokens();) {
            String filename = st.nextToken();

            if (alreadyLoaded.add(filename) == false) {
                continue;
            }

            LOG.debug("An item of value of parameter '"
                    + resourceNameInitParameter + "' detected : '" + filename
                    + "'.");

            URL url = contextClassLoader.getResource(filename);

            if (url == null) {
                try {
                    url = externalContext.getResource(filename);

                } catch (MalformedURLException ex) {
                    LOG.error("Malformed url for filename '" + filename + "'.",
                            ex);
                }
            }

            if (url == null) {
                LOG.debug("Configuration file '" + filename
                        + "' does not exist.");
                continue;
            }

            if (urls.contains(url)) {
                continue;
            }

            LOG.debug("Configuration file '" + filename + "' registred.");

            urls.add(url);
        }

        return new ConfigurationLoader(urls);
    }

    public static ConfigurationLoader scanFacesConfig(
            ExternalContext externalContext, List urls) {

        return scanConfigurationURLs(externalContext,
                FACES_CONFIG_RESOURCE_NAME, FACES_CONFIG_INIT_PARAMETER, urls);
    }

    public static ConfigurationLoader scanRCFacesConfig(
            ExternalContext externalContext, List urls) {

        return scanConfigurationURLs(externalContext, RCFACES_CONFIG_FILENAME,
                RCFACES_CONFIG_INIT_PARAMETER, urls);
    }
}
