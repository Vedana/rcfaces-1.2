/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractClientService extends AbstractService {

    private static final Log LOG = LogFactory
            .getLog(AbstractClientService.class);

    private static final String CLIENT_SERVICE_REGISTRY_PROPERTY = "camelia.client.service.Repository";

    public static IClientServiceRegistry getClientServiceRegistry(
            FacesContext facesContext) {

        Map<String, Object> applicationMap = facesContext.getExternalContext()
                .getApplicationMap();
        synchronized (AbstractClientService.class) {
            IClientServiceRegistry clientServiceRegistry = (IClientServiceRegistry) applicationMap
                    .get(CLIENT_SERVICE_REGISTRY_PROPERTY);
            if (clientServiceRegistry != null) {
                return clientServiceRegistry;
            }

            clientServiceRegistry = createClientServiceRegistry();

            applicationMap.put(CLIENT_SERVICE_REGISTRY_PROPERTY,
                    clientServiceRegistry);

            return clientServiceRegistry;
        }
    }

    protected static IClientServiceRegistry createClientServiceRegistry() {
        return new ClientServiceRegistryImpl();
    }

}
