/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.3  2006/03/28 12:22:46  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.2  2005/12/22 11:48:06  oeuillot
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
package org.rcfaces.core.internal;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
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
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.rewriting.IResourceVersionHandler;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;

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

    protected RcfacesContext() {
    }

    public static final RcfacesContext getCurrentInstance() {
        return getInstance((ExternalContext) null);
    }

    public static final RcfacesContext getInstance(FacesContext facesContext) {
        return getInstance(facesContext.getExternalContext());
    }

    public static final RcfacesContext getInstance(
            ExternalContext externalContext) {
        if (externalContext == null) {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
        }

        Map applicationMap = externalContext.getApplicationMap();

        RcfacesContext cameliaContext;
        synchronized (CAMELIA_CONTEXT_PROPERTY) {
            cameliaContext = (RcfacesContext) applicationMap
                    .get(CAMELIA_CONTEXT_PROPERTY);

            if (cameliaContext == null) {
                applicationMap.put(CAMELIA_CONTEXT_PROPERTY, Boolean.FALSE);

                cameliaContext = createCameliaContext();

                applicationMap.put(CAMELIA_CONTEXT_PROPERTY, cameliaContext);

                cameliaContext.initialize(externalContext);
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

    private static RcfacesContext createCameliaContext(ServletContext context,
            ServletRequest request, ServletResponse response) {
        try {
            FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
                    .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

            if (facesContextFactory == null) {
                throw new FacesException("Can not get Faces Context Factory !");
            }

            Lifecycle lifecycle = new Lifecycle() {
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

            FacesContext facesContext = facesContextFactory.getFacesContext(
                    context, request, response, lifecycle);
            try {
                RcfacesContext rcfacesContext = createCameliaContext();

                context.setAttribute(CAMELIA_CONTEXT_PROPERTY, rcfacesContext);

                rcfacesContext.initialize(facesContext.getExternalContext());

                return rcfacesContext;

            } finally {
                facesContext.release();
            }

        } catch (FacesException e) {
            Throwable rootCause = e.getCause();
            if (rootCause == null) {
                throw e;
            }

            throw new FacesException(e.getMessage(), rootCause);
        }

    }

    protected abstract void initialize(ExternalContext externalContext);

    private static RcfacesContext createCameliaContext() {

        // @XXX Rechercher dans les propriétés la classe d'impl !

        RcfacesContext cameliaContext = new RcfacesContextImpl();

        return cameliaContext;
    }

    public abstract IServicesRegistry getServicesRegistry();

    public abstract IClientValidatorsRegistry getClientValidatorsRegistry();

    public abstract IBorderRenderersRegistry getBorderRenderersRegistry();

    public abstract IProvidersRegistry getProvidersRegistry();

    public abstract String getApplicationVersion();

    public abstract IResourceVersionHandler getResourceVersionHandler();

    public abstract IAdapterManager getAdapterManager();

}
