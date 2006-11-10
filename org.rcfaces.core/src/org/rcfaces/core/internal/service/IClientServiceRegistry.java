/*
 * $Id$
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
