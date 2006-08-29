/*
 * $Id$
 * 
 * $Log$
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

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.component.ServiceComponent;
import org.rcfaces.core.event.IServiceEventListener;
import org.rcfaces.core.event.ServiceEvent;
import org.rcfaces.core.progressMonitor.IProgressMonitor;
import org.rcfaces.core.progressMonitor.SubProgressMonitor;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ClientServiceRegistryImpl implements IClientServiceRegistry {
    private static final String REVISION = "$Revision$";

    private final Map clientServicesByRequestId = new HashMap(32);

    public IClientService getClientServiceById(String requestId) {
        return (IClientService) clientServicesByRequestId.get(requestId);
    }

    public IClientService createClientService(String requestId,
            ServiceComponent component, Object parameter, int syncMode)
            throws ClientServiceException {

        IClientService clientService = new ClientServiceImpl(requestId,
                component, parameter,
                syncMode == IClientServiceRegistry.ASYNC_MODE);

        return clientService;
    }

    public void startClientService(IClientService clientService) {
    }

    public Object waitClientService(IClientService clientService,
            IProgressMonitor progressMonitor) {
        ClientServiceImpl clientServiceImpl = (ClientServiceImpl) clientService;

        ServiceComponent serviceComponent = clientServiceImpl.popComponent();

        FacesListener fls[] = serviceComponent.listServiceEventListeners();

        ClientServiceEventReturnValue event = new ClientServiceEventReturnValue(
                serviceComponent, clientServiceImpl.popParameter(),
                progressMonitor, fls.length);

        Object returnValue = null;
        for (int i = 0; i < fls.length; i++) {
            IServiceEventListener serviceEventListener = (IServiceEventListener) fls[i];

            event.beginListener(i);

            serviceEventListener.processServiceEvent(event);

            event.endListener(i);

            returnValue = event.getReturnValue();
            if (returnValue != null) {
                break;
            }
        }

        event.listenersDone();

        return returnValue;
    }

    public void releaseClientService(IClientService clientService) {
        clientServicesByRequestId.remove(clientService.getClientServiceId());
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class ClientServiceImpl implements IClientService {
        private static final String REVISION = "$Revision$";

        private String clientServiceId;

        private int status;

        private int progress;

        private int errorCode;

        private String errorMessage;

        private Object parameter;

        private boolean asyncMode;

        private ServiceComponent component;

        ClientServiceImpl(String requestId, ServiceComponent component,
                Object parameter, boolean asyncMode) {
            this.clientServiceId = requestId;
            this.parameter = parameter;
            this.asyncMode = asyncMode;
            this.component = component;
        }

        public String getClientServiceId() {
            return clientServiceId;
        }

        public int getStatus() {
            return status;
        }

        public int getProgress() {
            return progress;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public final Object popParameter() {
            Object parameter = this.parameter;
            this.parameter = null;

            return parameter;
        }

        public final boolean isAsyncMode() {
            return asyncMode;
        }

        public final ServiceComponent popComponent() {
            ServiceComponent serviceComponent = this.component;
            this.component = null;

            return serviceComponent;
        }

        public void cancel() {
            // TODO Auto-generated method stub

        }

    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static final class ClientServiceEventReturnValue extends
            ServiceEvent implements IEventReturnValue {
        private static final String REVISION = "$Revision$";

        private final int nbListeners;

        private int listenerIndex = 0;

        private Object returnValue;

        private IProgressMonitor progressMonitor;

        public ClientServiceEventReturnValue(UIComponent component,
                Object data, IProgressMonitor progressMonitor, int nbListeners) {
            super(component, data);

            if (progressMonitor != null) {
                progressMonitor = new SubProgressMonitor(progressMonitor,
                        nbListeners);
            }
            this.progressMonitor = progressMonitor;
            this.nbListeners = nbListeners;
        }

        public void listenersDone() {
            if (progressMonitor == null) {
                return;
            }

            progressMonitor.done();
        }

        public void beginListener(int i) {
            listenerIndex = i;
        }

        public void endListener(int i) {
            if (progressMonitor == null) {
                return;
            }

            getProgressMonitor().done();

            resetProgressMonitor();
        }

        public void setReturnValue(Object ret) {
            this.returnValue = ret;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        protected IProgressMonitor createProgressMonitor() {
            if (progressMonitor == null) {
                return super.createProgressMonitor();
            }

            return new SubProgressMonitor(progressMonitor, 1);
        }

    }
}
