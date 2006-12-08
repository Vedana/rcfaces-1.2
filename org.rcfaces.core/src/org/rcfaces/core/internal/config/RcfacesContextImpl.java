/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import java.io.CharArrayReader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.rcfaces.core.internal.adapter.AdapterManagerImpl;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorRegistry;
import org.rcfaces.core.internal.contentAccessor.IContentVersionHandler;
import org.rcfaces.core.internal.contentStorage.ContentStorageAccessorHandler;
import org.rcfaces.core.internal.contentStorage.IContentStorageEngine;
import org.rcfaces.core.internal.images.ImageContentAccessorHandlerImpl;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.version.IResourceVersionHandler;
import org.rcfaces.core.internal.version.ResourceVersionHandlerImpl;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RcfacesContextImpl extends RcfacesContext implements
        Externalizable {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -4224530723124583628L;

    private static final Log LOG = LogFactory.getLog(RcfacesContextImpl.class);

    private static final String RCFACES_CONFIG_FILENAME = "rcfaces-config.xml";

    private static final String RCFACES_RESOURCE_NAME = "META-INF/"
            + RCFACES_CONFIG_FILENAME;

    private static final Package[] KERNEL_CONFIG_FILENAMES = new Package[] {
            RcfacesContext.class.getPackage(),
            RcfacesContextImpl.class.getPackage(),
            AdapterManagerImpl.class.getPackage(),
            ResourceVersionHandlerImpl.class.getPackage(),
            ContentAccessorsRegistryImpl.class.getPackage(),
            ImageContentAccessorHandlerImpl.class.getPackage(),
            ContentAccessorsRegistryImpl.class.getPackage(),
            ContentStorageAccessorHandler.class.getPackage() };

    private static final String DESIGNER_MODE_PROPERTY = "com.vedana.nodus3.designer";

    private transient ServicesRegistryImpl servicesRegistry;

    private transient ClientValidatorsRegistryImpl clientValidatorsRegistry;

    private transient ProvidersRegistry providersRegistry;

    private transient BorderRenderersRegistryImpl borderRenderersRegistry;

    private final Map attributes = new HashMap(32);

    private transient IContentVersionHandler contentVersionHandler;

    private transient IContentAccessorRegistry contentAccessorRegistry;

    private transient IResourceVersionHandler resourceVersionHandler;

    private transient String applicationVersion;

    private transient IAdapterManager adapterManager;

    private transient IContentStorageEngine indirectContentRepository;

    private boolean designerMode;

    public RcfacesContextImpl() {
    }

    protected void initialize(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        designerMode = facesContext.getExternalContext().getApplicationMap()
                .containsKey(DESIGNER_MODE_PROPERTY);
        if (designerMode) {
            LOG.info("Designer MODE  detected.");
        }

        initializeRegistries(null);

        loadConfigs(facesContext);

        initializeConfigs(facesContext);
    }
    
    protected void initializeRegistries(FacesContext facesContext) {

        if (designerMode) {
            LOG.debug("Ignore service registry (designer mode)");

            LOG.debug("Ignore clientValidators registry (designer mode)");

        } else {
            LOG.debug("Initialize service registry");
            servicesRegistry = createServicesRegistry();

            LOG.debug("Initialize clientValidators registry");
            clientValidatorsRegistry = createClientValidatorsRegistry();
        }

        LOG.debug("Initialize border renderers registry");
        borderRenderersRegistry = createBorderRenderersRegistry();

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

    private void loadConfigs(FacesContext facesContext) {
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

        List urls = new ArrayList(32);

        LOG.debug("Search configuration files ...");
        for (int i = 0; i < KERNEL_CONFIG_FILENAMES.length; i++) {
            Package pkg = KERNEL_CONFIG_FILENAMES[i];

            String resourceName = pkg.getName().replace('.', '/') + '/'
                    + RCFACES_CONFIG_FILENAME;

            URL url = getClass().getClassLoader().getResource(resourceName);

            LOG.debug("Configuration file '" + resourceName + "' => "
                    + ((url != null) ? "exists" : "ignore"));
            if (url == null) {
                continue;
            }

            urls.add(url);
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

            if (enumeration != null) {
                for (; enumeration.hasMoreElements();) {
                    URL url = (URL) enumeration.nextElement();

                    LOG.debug("Rcfaces resource in meta-inf detected : " + url);

                    urls.add(url);
                }
            }

            ExternalContext externalContext = facesContext.getExternalContext();

            String configFilenames = externalContext
                    .getInitParameter(CAMELIA_CONFIG_FILES_PARAMETER);

            if (configFilenames != null) {
                LOG.debug("Value for parameter '"
                        + CAMELIA_CONFIG_FILES_PARAMETER + "' detected : '"
                        + configFilenames + "'.");

                StringTokenizer st = new StringTokenizer(configFilenames,
                        ",;\t \r\n");

                for (; st.hasMoreTokens();) {
                    String filename = st.nextToken();

                    URL url = contextClassLoader.getResource(filename);

                    if (url == null) {
                        try {
                            url = externalContext.getResource(filename);

                        } catch (MalformedURLException ex) {
                            LOG.error("Malformed url for filename '" + filename
                                    + "'.", ex);
                        }
                    }

                    if (url == null) {
                        LOG.debug("Configuration file '" + filename
                                + "' does not exist.");
                        continue;
                    }

                    urls.add(url);
                }
            }
        }

        loadConfigurations(digester, urls);

        loadProvidersConfiguration(facesContext, urls);

        LOG.info("Rcfaces config loaded.");
    }

    private void loadConfigurations(Digester digester, List urls) {

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

            loadConfig(digester, inputStream, url.toString());
        }
    }

    private void loadConfig(Digester digester, InputStream inputStream,
            String resourceName) {

        LOG.debug("Loading rcfaces config file '" + resourceName + "' ...");

        try {
            digester.parse(inputStream);

        } catch (Throwable th) {
            LOG.error("Can not parse Rcfaces config file '" + resourceName
                    + "'.", th);
            return;

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error("Can not close Rcfaces config file '" + resourceName
                        + "'.", e);
            }
        }

        LOG.debug("Rcfaces config file '" + resourceName + "' loaded !");
    }

    private void configureRules(Digester digester) {
        if (designerMode == false) {
            ((ServicesRegistryImpl) getServicesRegistry())
                    .configureRules(digester);
            ((ClientValidatorsRegistryImpl) getClientValidatorsRegistry())
                    .configureRules(digester);
        }

        ((ProvidersRegistry) getProvidersRegistry()).configureRules(digester);
        ((BorderRenderersRegistryImpl) getBorderRenderersRegistry())
                .configureRules(digester);
    }

    private void loadProvidersConfiguration(FacesContext facesContext,
            final List urls) {

        IProvidersConfigurator providersConfigurator = new IProvidersConfigurator() {

            public void parseConfiguration(Digester digester) {
                loadConfigurations(digester, urls);
            }

        };

        ((ProvidersRegistry) getProvidersRegistry())
                .loadProvidersConfiguration(providersConfigurator);

        ((ProvidersRegistry) getProvidersRegistry())
                .startupProviders(facesContext);
    }

    protected void initializeConfigs(FacesContext facesContext) {

        Map applicationMap = facesContext.getExternalContext()
                .getApplicationMap();

        applicationVersion = (String) applicationMap
                .get(APPLICATION_VERSION_PROPERTY);

        LOG.debug("Set application version to '" + applicationVersion + "'.");

        LOG.debug("Initialize all configs: done.");
    }

    public final String getApplicationVersion() {
        return applicationVersion;
    }

    public void setDefaultContentVersionHandler(
            IContentVersionHandler contentVersionHandler) {
        this.contentVersionHandler = contentVersionHandler;
    }

    public final IContentVersionHandler getDefaultContentVersionHandler() {
        return contentVersionHandler;
    }

    public void readExternal(ObjectInput in) {
        // On ne serialize rien !
    }

    public void writeExternal(ObjectOutput out) {
        // On ne serialize rien !
    }

    public IAdapterManager getAdapterManager() {
        return adapterManager;
    }

    public void setAdapterManager(IAdapterManager adapterManager) {
        this.adapterManager = adapterManager;
    }

    public boolean isDesignerMode() {
        return designerMode;
    }

    public IContentAccessorRegistry getContentAccessorRegistry() {
        return contentAccessorRegistry;
    }

    public IResourceVersionHandler getResourceVersionHandler() {
        return resourceVersionHandler;
    }

    public void setContentAccessorRegistry(
            IContentAccessorRegistry contentAccessorRegistry) {
        this.contentAccessorRegistry = contentAccessorRegistry;
    }

    public void setResourceVersionHandler(
            IResourceVersionHandler resourceVersionHandler) {
        this.resourceVersionHandler = resourceVersionHandler;
    }

    public IContentStorageEngine getContentStorageEngine() {
        return indirectContentRepository;
    }

    public void setContentStorageEngine(
            IContentStorageEngine indirectContentRepository) {
        this.indirectContentRepository = indirectContentRepository;
    }

}
