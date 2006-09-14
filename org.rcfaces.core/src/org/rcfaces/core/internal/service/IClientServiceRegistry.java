/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
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
 */
package org.rcfaces.core.internal.service;

import org.rcfaces.core.component.ServiceComponent;
import org.rcfaces.core.progressMonitor.IProgressMonitor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientServiceRegistry {

    int SYNC_MODE = 1;

    int ASYNC_MODE = 2;

    IClientService getClientServiceById(String requestId);

    IClientService createClientService(String requestId,
            ServiceComponent component, Object parameter, int syncMode)
            throws ClientServiceException;

    void startClientService(IClientService clientService);

    Object waitClientService(IClientService clientService,
            IProgressMonitor progressMonitor);

    void releaseClientService(IClientService clientService);
}
