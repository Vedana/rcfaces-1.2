/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
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
 * Revision 1.2  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.core.internal.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.service.IService;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.util.ClassLocator;
import org.xml.sax.Attributes;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ServicesRegistryImpl extends AbstractRenderKitRegistryImpl
        implements Serializable, PhaseListener, IServicesRegistry {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ServicesRegistryImpl.class);

    private static final boolean TEST_SERVICE_WAIT = true;

    protected static final String CAMELIA_HEADER = "X-Camelia";

    private static final String SERVICE_WAIT_PROPERTY = "org.rcfaces.core.services.WAIT";

    public ServicesRegistryImpl() {
    }

    public IService getService(FacesContext facesContext, String renderKitId,
            String serviceId) {

        RenderKit renderKit = (RenderKit) getRenderKit(facesContext,
                renderKitId);
        if (renderKit == null) {
            throw new FacesException("No renderKit '" + renderKitId
                    + "' defined !");

        }

        ServiceFacade service = renderKit.getServiceById(serviceId);
        if (service == null) {
            throw new FacesException("Service '" + serviceId
                    + "' is not defined !");
        }

        return service.getService(facesContext);
    }

    public void beforePhase(PhaseEvent event) {
    }

    public final PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();

        Map headers = facesContext.getExternalContext().getRequestHeaderMap();
        String commandId = (String) headers.get(CAMELIA_HEADER);
        if (commandId == null) {
            return;
        }

        RenderKit renderKit = (RenderKit) getRenderKit(facesContext, null);
        if (renderKit == null) {
            throw new FacesException("No renderKit defined !");
        }

        ServiceFacade facade = renderKit.getServiceByCommandId(commandId);
        if (facade == null) {
            LOG.error("Can not find command '" + commandId + "'.");

            throw new FacesException("Can not find service '" + commandId
                    + "'.");
        }

        IService service = facade.getService(facesContext);

        if (TEST_SERVICE_WAIT) {
            String wait = facesContext.getExternalContext().getInitParameter(
                    SERVICE_WAIT_PROPERTY);

            if (wait != null && wait.length() > 0) {

                synchronized (wait) {
                    System.out.println("WAIT ...");
                    try {
                        wait.wait(Integer.parseInt(wait));
                    } catch (Exception ex) {
                    }
                }
            }
        }

        service.service(facesContext, commandId);
    }

    protected AbstractRenderKitRegistryImpl.RenderKit createRenderKit() {
        return new RenderKit();
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public static final class ServiceFacade {
        private static final String REVISION = "$Revision$";

        private String className;

        private String id;

        private boolean unavailable;

        private IService service;

        public ServiceFacade() {
        }

        public final void setId(String id) {
            this.id = id;
        }

        public final void setClassName(String className) {
            this.className = className;
        }

        public synchronized IService getService(FacesContext facesContext) {
            if (service != null) {
                return service;
            }

            if (unavailable) {
                throw new FacesException("Service '" + id
                        + "' is unavailable !");
            }

            try {
                unavailable = true;

                Class clazz = ClassLocator.load(className, this, facesContext);

                service = (IService) clazz.newInstance();

                service.initialize(facesContext);

                unavailable = false;

                return service;

            } catch (Throwable th) {
                LOG.error("Can not start service '" + id + "'.", th);

                throw new FacesException("Can not start service '" + id + "'.",
                        th);
            }

        }

        public String getId() {
            return id;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public static class RenderKit extends
            AbstractRenderKitRegistryImpl.RenderKit {
        private static final String REVISION = "$Revision$";

        private final Map serviceFacadeByCommandId;

        private final Map serviceFacadeByServiceId;

        public RenderKit() {
            serviceFacadeByCommandId = new HashMap(32);

            serviceFacadeByServiceId = new HashMap(16);
        }

        public ServiceFacade getServiceById(String serviceId) {
            return (ServiceFacade) serviceFacadeByServiceId.get(serviceId);
        }

        public ServiceFacade getServiceByCommandId(String commandId) {
            return (ServiceFacade) serviceFacadeByCommandId.get(commandId);
        }

        public void addService(ServiceFacade serviceFacade) {
            serviceFacadeByServiceId.put(serviceFacade.getId(), serviceFacade);
        }

        public void addCommand(String commandId, ServiceFacade serviceFacade) {
            serviceFacadeByCommandId.put(commandId, serviceFacade);
        }

    }

    public void configureRules(Digester digester) {

        digester.addRule("rcfaces-config/services/render-kit", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                String renderKitId = attributes.getValue("render-kit-id");

                RenderKit renderKit = (RenderKit) allocate(renderKitId);

                super.digester.push(renderKit);
            }

            public void end(String namespace, String name) throws Exception {
                super.digester.pop();
            }
        });

        digester.addObjectCreate("rcfaces-config/services/render-kit/service",
                ServiceFacade.class);
        digester.addSetProperties("rcfaces-config/services/render-kit/service",
                "id", "id");
        digester.addSetProperties("rcfaces-config/services/render-kit/service",
                "class", "className");
        digester.addRule("rcfaces-config/services/render-kit/service/command",
                new Rule() {
                    private static final String REVISION = "$Revision$";

                    public void begin(String namespace, String name,
                            Attributes attributes) throws Exception {

                        ServiceFacade service = (ServiceFacade) super.digester
                                .peek();
                        RenderKit renderKit = (RenderKit) super.digester
                                .peek(1);

                        String commandId = attributes.getValue("id");

                        renderKit.addCommand(commandId, service);
                    }
                });
        digester.addSetNext("rcfaces-config/services/render-kit/service",
                "addService");
    }
}