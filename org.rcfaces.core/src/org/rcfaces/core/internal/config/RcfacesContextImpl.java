/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.6  2006/07/18 17:06:31  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.5  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.4  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.3  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.config;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class RcfacesContextImpl extends RcfacesContext implements Serializable {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(RcfacesContextImpl.class);

    private static final String RCFACES_CONFIG_FILENAME = "rcfaces-config.xml";

    private static final String RCFACES_RESOURCE_NAME = "META-INF/"
            + RCFACES_CONFIG_FILENAME;

    private static final Package[] KERNEL_CONFIG_FILENAMES = new Package[] {
            RcfacesContext.class.getPackage(),
            RcfacesContextImpl.class.getPackage()
    // HtmlRenderKit.class.getPackage()
    };

    private transient ServicesRegistryImpl servicesRegistry;

    private transient ClientValidatorsRegistryImpl clientValidatorsRegistry;

    private transient ProvidersRegistry providersRegistry;

    private transient BorderRenderersRegistryImpl borderRenderersRegistry;

    private final Map attributes = new HashMap(32);

    private String applicationVersion;

    public RcfacesContextImpl() {
        this(null);
    }

    public RcfacesContextImpl(ExternalContext externalContext) {
        initialize(externalContext);
    }

    private synchronized void initialize(ExternalContext externalContext) {

        if (externalContext == null) {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
        }

        initializeRegistries(externalContext);

        loadConfigs(externalContext);

        initializeConfigs(externalContext);
    }

    protected void initializeRegistries(ExternalContext externalContext) {

        LOG.debug("Initialize service registry");
        servicesRegistry = createServicesRegistry();

        LOG.debug("Initialize border renderers registry");
        borderRenderersRegistry = createBorderRenderersRegistry();

        LOG.debug("Initialize clientValidators registry");
        clientValidatorsRegistry = createClientValidatorsRegistry();

        LOG.debug("Initialize providers registry");
        providersRegistry = createProvidersRegistry();
    }

    public final IServicesRegistry getServicesRegistry() {
        return servicesRegistry;
    }

    protected ServicesRegistryImpl createServicesRegistry() {
        LOG.debug("Installing services registry");

        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);

        Iterator it = lifecycleFactory.getLifecycleIds();
        for (; it.hasNext();) {
            String lifecycleId = (String) it.next();

            Lifecycle lifecycle = lifecycleFactory.getLifecycle(lifecycleId);

            PhaseListener phaseListeners[] = lifecycle.getPhaseListeners();
            if (phaseListeners == null || phaseListeners.length < 1) {
                continue;
            }

            for (int i = 0; i < phaseListeners.length; i++) {
                PhaseListener phaseListener = phaseListeners[i];

                if ((phaseListener instanceof ServicesRegistryImpl) == false) {
                    continue;
                }

                servicesRegistry = (ServicesRegistryImpl) phaseListener;
                servicesRegistry.initialize(null);

                LOG.debug("Services registry installed.");
                return servicesRegistry;
            }
        }

        LOG.error("Can not find Services Registry into lifeCycle factories.");

        throw new FacesException("Can not find ServicesRegistry !");
    }

    public final IClientValidatorsRegistry getClientValidatorsRegistry() {
        return clientValidatorsRegistry;
    }

    protected ClientValidatorsRegistryImpl createClientValidatorsRegistry() {
        return new ClientValidatorsRegistryImpl();
    }

    public final IProvidersRegistry getProvidersRegistry() {
        return providersRegistry;
    }

    protected ProvidersRegistry createProvidersRegistry() {
        return new ProvidersRegistry();
    }

    public final IBorderRenderersRegistry getBorderRenderersRegistry() {
        return borderRenderersRegistry;
    }

    protected BorderRenderersRegistryImpl createBorderRenderersRegistry() {
        return new BorderRenderersRegistryImpl();
    }

    public final Serializable setAttribute(String property, Serializable value) {
        return (Serializable) attributes.put(property, value);
    }

    public final Serializable getAttribute(String property) {
        return (Serializable) attributes.get(property);
    }

    public final Serializable removeAttribute(String property) {
        return (Serializable) attributes.remove(property);
    }

    private void loadConfigs(ExternalContext externalContext) {
        LOG.info("Loading rcfaces config ...");

        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);

        digester.setEntityResolver(new EntityResolver() {
            private static final String REVISION = "$Revision$";

            public InputSource resolveEntity(String string, String string1) {
                return new InputSource(new CharArrayReader(new char[0]));
            }

        });

        LOG.debug("Declare configurations rules.");
        configureRules(digester);

        LOG.debug("Search configuration files ...");
        for (int i = 0; i < KERNEL_CONFIG_FILENAMES.length; i++) {
            Package pkg = KERNEL_CONFIG_FILENAMES[i];

            String resourceName = pkg.getName().replace('.', '/') + '/'
                    + RCFACES_CONFIG_FILENAME;

            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(resourceName);

            LOG.debug("Load configuration file '" + resourceName + "' => "
                    + ((inputStream != null) ? "exists" : "ignore"));
            if (inputStream == null) {
                continue;
            }

            loadConfig(digester, inputStream, resourceName);
        }

        ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        if (contextClassLoader != null) {

            Enumeration enumeration = null;
            try {
                enumeration = contextClassLoader
                        .getResources(RCFACES_RESOURCE_NAME);
            } catch (IOException e) {
                LOG.error("Can not scan resources '" + RCFACES_RESOURCE_NAME
                        + "' into context classloader.");
            }

            for (; enumeration.hasMoreElements();) {
                URL url = (URL) enumeration.nextElement();

                LOG.debug("Rcfaces resource in meta-inf detected : " + url);

                InputStream inputStream;
                try {
                    inputStream = url.openStream();

                } catch (IOException e) {
                    LOG.error("Can not open url '" + url + "'.", e);
                    continue;
                }

                if (inputStream == null) {
                    LOG.debug("Configuration file '" + url
                            + "' does not exist.");
                    continue;
                }

                loadConfig(digester, inputStream, url.toString());
            }

            String configFilenames = externalContext
                    .getInitParameter(CAMELIA_CONFIG_FILES_PARAMETER);

            LOG.debug("Value of '" + CAMELIA_CONFIG_FILES_PARAMETER
                    + "' property is '" + configFilenames + "'.");

            if (configFilenames != null) {
                StringTokenizer st = new StringTokenizer(configFilenames,
                        ",;\t \r\n");

                for (; st.hasMoreTokens();) {
                    String filename = st.nextToken();

                    InputStream inputStream = contextClassLoader
                            .getResourceAsStream(filename);

                    if (inputStream == null) {
                        inputStream = externalContext
                                .getResourceAsStream(filename);
                    }

                    if (inputStream == null) {
                        LOG.debug("Configuration file '" + filename
                                + "' does not exist.");
                        continue;
                    }

                    loadConfig(digester, inputStream, filename);
                }
            }
        }

        LOG.info("Rcfaces config loaded.");
    }

    private void loadConfig(Digester digester, InputStream inputStream,
            String resourceName) {

        LOG.debug("Loading rcfaces config file '" + resourceName + "' ...");

        try {
            digester.parse(inputStream);

        } catch (Exception e) {
            LOG.error("Can not parse '" + resourceName + "'.", e);

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error("Can not close '" + resourceName + "'.", e);
            }
        }

        LOG.debug("Rcfaces config file '" + resourceName + "' loaded !");
    }

    private void configureRules(Digester digester) {
        ((ServicesRegistryImpl) getServicesRegistry()).configureRules(digester);
        ((ClientValidatorsRegistryImpl) getClientValidatorsRegistry())
                .configureRules(digester);
        ((ProvidersRegistry) getProvidersRegistry()).configureRules(digester);
        ((BorderRenderersRegistryImpl) getBorderRenderersRegistry())
                .configureRules(digester);
    }

    protected void initializeConfigs(ExternalContext externalContext) {

        Map applicationMap = externalContext.getApplicationMap();

        applicationVersion = (String) applicationMap
                .get(APPLICATION_VERSION_PROPERTY);

        LOG.debug("Set application version to '" + applicationVersion + "'.");

        LOG.debug("Initialize all configs: done.");
    }

    public final String getApplicationVersion() {
        return applicationVersion;
    }
}
