/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.service;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractClientService extends AbstractService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractClientService.class);

    private static final String CLIENT_SERVICE_REGISTRY_PROPERTY = "camelia.client.service.Repository";

    protected IClientServiceRegistry getClientServiceRegistry(
            FacesContext facesContext) {

        Map applicationMap = facesContext.getExternalContext()
                .getApplicationMap();
        synchronized (applicationMap) {

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

    protected IClientServiceRegistry createClientServiceRegistry() {
        return new ClientServiceRegistryImpl();
    }

}
