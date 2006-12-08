/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.config.IProvidersRegistry;
import org.rcfaces.core.internal.config.RcfacesContextImpl;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorRegistry;
import org.rcfaces.core.internal.contentAccessor.IContentVersionHandler;
import org.rcfaces.core.internal.contentStorage.IContentStorageEngine;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.version.IResourceVersionHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class RcfacesContext {
    private static final String REVISION = "$Revision$";

    private static final String CAMELIA_CONTEXT_PROPERTY = "org.rcfaces.core.internal.CAMELIA_CONTEXT";

    protected static final String CAMELIA_CONFIG_FILES_PARAMETER = Constants
            .getPackagePrefix()
            + ".CONFIG_FILES";

    public static final String APPLICATION_VERSION_PROPERTY = "org.rcfaces.core.internal.APPLICATION_VERSION";

    private static final Lifecycle EMPTY_LIFECYCLE = new Lifecycle() {
        private static final String REVISION = "$Revision$";

        public void addPhaseListener(PhaseListener listener) {
        }

        public void execute(FacesContext context) throws FacesException {
        }

        public PhaseListener[] getPhaseListeners() {
            return new PhaseListener[0];
        }

        public void removePhaseListener(PhaseListener listener) {
        }

        public void render(FacesContext context) throws FacesException {
        }
    };

    protected RcfacesContext() {
    }

    public static final RcfacesContext getCurrentInstance() {
        return getInstance((FacesContext) null);
    }

    public static final RcfacesContext getInstance(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        Map applicationMap = facesContext.getExternalContext()
                .getApplicationMap();

        RcfacesContext cameliaContext;
        synchronized (CAMELIA_CONTEXT_PROPERTY) {
            cameliaContext = (RcfacesContext) applicationMap
                    .get(CAMELIA_CONTEXT_PROPERTY);

            if (cameliaContext == null) {
                applicationMap.put(CAMELIA_CONTEXT_PROPERTY, Boolean.FALSE);

                cameliaContext = createCameliaContext();

                applicationMap.put(CAMELIA_CONTEXT_PROPERTY, cameliaContext);

                cameliaContext.initialize(facesContext);
            }
        }

        return cameliaContext;
    }

    public static final RcfacesContext getInstance(
            ServletContext servletContext, ServletRequest request,
            ServletResponse response) {
        synchronized (CAMELIA_CONTEXT_PROPERTY) {
            RcfacesContext cameliaContext = (RcfacesContext) servletContext
                    .getAttribute(CAMELIA_CONTEXT_PROPERTY);
            if (cameliaContext != null) {
                return cameliaContext;
            }

            cameliaContext = createCameliaContext(servletContext, request,
                    response);

            return cameliaContext;
        }
    }

    public static void runIntoFacesContext(ServletContext context,
            ServletRequest request, ServletResponse response, Runnable runnable) {

        if (FacesContext.getCurrentInstance() != null) {
            runnable.run();
            return;
        }

        FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        if (facesContextFactory == null) {
            throw new FacesException("Can not get Faces Context Factory !");
        }

        FacesContext facesContext = facesContextFactory.getFacesContext(
                context, request, response, EMPTY_LIFECYCLE);
        try {
            runnable.run();

        } finally {
            facesContext.release();
        }
    }

    private static RcfacesContext createCameliaContext(
            final ServletContext context, ServletRequest request,
            ServletResponse response) {

        final RcfacesContext rcfacesContextRef[] = new RcfacesContext[1];

        runIntoFacesContext(context, request, response, new Runnable() {

            public void run() {
                FacesContext facesContext = FacesContext.getCurrentInstance();

                RcfacesContext rcfacesContext = createCameliaContext();

                context.setAttribute(CAMELIA_CONTEXT_PROPERTY, rcfacesContext);

                rcfacesContext.initialize(facesContext);

                rcfacesContextRef[0] = rcfacesContext;
            }

        });

        return rcfacesContextRef[0];
    }

    protected abstract void initialize(FacesContext facesContext);

    private static RcfacesContext createCameliaContext() {

        // @XXX Rechercher dans les propriétés la classe d'impl !

        RcfacesContext cameliaContext = new RcfacesContextImpl();

        return cameliaContext;
    }

    public abstract String getApplicationVersion();

    public abstract boolean isDesignerMode();

    public abstract IServicesRegistry getServicesRegistry();

    public abstract IClientValidatorsRegistry getClientValidatorsRegistry();

    public abstract IBorderRenderersRegistry getBorderRenderersRegistry();

    public abstract IProvidersRegistry getProvidersRegistry();

    public abstract IAdapterManager getAdapterManager();

    public abstract void setAdapterManager(IAdapterManager adapterManager);

    public abstract IContentVersionHandler getDefaultContentVersionHandler();

    public abstract void setDefaultContentVersionHandler(
            IContentVersionHandler handler);

    public abstract IContentAccessorRegistry getContentAccessorRegistry();

    public abstract void setContentAccessorRegistry(
            IContentAccessorRegistry registry);

    public abstract IResourceVersionHandler getResourceVersionHandler();

    public abstract void setResourceVersionHandler(
            IResourceVersionHandler resourceVersionHandler);

    public abstract IContentStorageEngine getContentStorageEngine();

    public abstract void setContentStorageEngine(
            IContentStorageEngine indirectContentRepository);
}
