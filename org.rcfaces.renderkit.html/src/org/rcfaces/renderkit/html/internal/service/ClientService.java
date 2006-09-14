/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
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
package org.rcfaces.renderkit.html.internal.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ServiceComponent;
import org.rcfaces.core.internal.service.AbstractClientService;
import org.rcfaces.core.internal.service.ClientServiceException;
import org.rcfaces.core.internal.service.IClientService;
import org.rcfaces.core.internal.service.IClientServiceRegistry;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.progressMonitor.IProgressMonitor;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.w3c.dom.Document;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientService extends AbstractClientService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ClientService.class);

    private static final String PLAIN_TEXT_MIME_TYPE = "text/plain";

    private static final String PARAMETER = "data";

    private static final String PARAMETER_TYPE = "type";

    private static final String SYNC_MODE = "syncMode";

    private static final String COMPONENT_ID = "X-Camelia-Component-Id";

    private static final String REQUEST_ID = "X-Camelia-Request-Id";

    private static final String CAMELIA_CONTENT_TYPE = "X-Camelia-Content-Type";

    private static final String CAMELIA_PROGRESS_MONITOR = "X-Camelia-ProgressMonitor";

    public void service(FacesContext facesContext, String commandId) {
        if ("client.newService".equals(commandId)) {
            createOperation(facesContext);
            return;
        }

        if ("client.infoService".equals(commandId)) {
            infoClientService(facesContext);
            return;
        }

        AbstractHtmlService.sendJsError(facesContext,
                "Can not identify command '" + commandId + "'.");
    }

    private void createOperation(FacesContext facesContext) {
        ExternalContext externalContext = facesContext.getExternalContext();
        Map requestHeader = externalContext.getRequestHeaderMap();

        String requestId = (String) requestHeader.get(REQUEST_ID);
        if (requestId == null) {
            AbstractHtmlService.sendJsError(facesContext, "Can not get '"
                    + REQUEST_ID + "' parameter !");
            return;
        }

        String componentId = (String) requestHeader.get(COMPONENT_ID);
        if (componentId == null) {
            AbstractHtmlService.sendJsError(facesContext, "Can not get '"
                    + COMPONENT_ID + "' parameter !");
            return;
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();

        UIComponent component = ComponentTools.getForComponent(facesContext,
                componentId, viewRoot);
        if (component == null) {
            AbstractHtmlService.sendJsError(facesContext, "Can not get view '"
                    + componentId + "' !");
            return;
        }

        if ((component instanceof ServiceComponent) == false) {
            AbstractHtmlService.sendJsError(facesContext, "Bad component '"
                    + componentId + "' !");
            return;
        }

        ServiceComponent serviceComponent = (ServiceComponent) component;

        int syncMode = IClientServiceRegistry.SYNC_MODE;

        String syncModeAtt = (String) requestHeader.get(SYNC_MODE);
        if ("asynchronous".equals(syncModeAtt)) {
            syncMode = IClientServiceRegistry.ASYNC_MODE;
        }

        Object parameter = null;

        String progressMonitorValue = (String) requestHeader
                .get(CAMELIA_PROGRESS_MONITOR);

        boolean formParameters = true;
        String cameliaContentType = (String) requestHeader
                .get(CAMELIA_CONTENT_TYPE);
        if ("xml".equals(cameliaContentType)) {
            formParameters = false;

            // Deserialize le Document xml
            parameter = null; // <<< ICI
            progressMonitorValue = null;
        }

        if (formParameters) {
            Map request = externalContext.getRequestParameterMap();

            String parameterType = (String) request.get(PARAMETER_TYPE);

            String parameterString = (String) request.get(PARAMETER);

            parameter = deserializeParameter(serviceComponent, parameterType,
                    parameterString);
        }

        IClientServiceRegistry operationsRegistry = getClientServiceRegistry(facesContext);

        IClientService clientService;
        try {
            clientService = operationsRegistry.createClientService(requestId,
                    serviceComponent, parameter, syncMode);

        } catch (ClientServiceException ex) {
            LOG.error("Can not create operation '" + requestId + "'.", ex);

            clientService = null;
        }

        IProgressMonitor progressMonitor = null;

        if (syncMode == IClientServiceRegistry.SYNC_MODE) {
            operationsRegistry.startClientService(clientService);

            Object ret = operationsRegistry.waitClientService(clientService,
                    progressMonitor);

            sendResponse(facesContext, ret, progressMonitor);

            return;
        }

        sendOperationStatus(facesContext, clientService, true);
    }

    protected void sendResponse(FacesContext facesContext, Object ret,
            IProgressMonitor progressMonitor) {
        HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();

        AbstractHtmlService.setNoCache(response);

        String buffer = null;
        String type = "string";
        String contenType = "text/plain";
        if (ret instanceof Document) {
            contenType = "text/xml";
            type = "xml";

        } else if (ret instanceof Map) {
            buffer = HtmlTools.encodeParametersFromMap((Map) ret, '&');
            type = "object";

        } else if (ret == null) {
            type = "null";
            buffer = null;

        } else {
            buffer = String.valueOf(ret);
        }

        response.setContentType(contenType + "; charset="
                + AbstractHtmlService.RESPONSE_CHARSET);
        response.setHeader(CAMELIA_CONTENT_TYPE, type);

        try {
            PrintWriter pw = response.getWriter();

            if (buffer != null) {
                pw.print(buffer);

            } else {
                // C'est un document ... on serialize !
            }

        } catch (IOException ex) {
            LOG.error("Can not send response !", ex);
        }

        facesContext.responseComplete();
    }

    private Object deserializeParameter(UIComponent component, String type,
            String parameterString) {
        if ("xml".equals(type)) {
            // => transforme en Document !
            return null;
        }

        if ("object".equals(type)) {
            return HtmlTools.decodeParametersToMap(component, parameterString,
                    '&', null);

        } else if ("null".equals(type)) {
            return null;
        }

        return parameterString;
    }

    private void infoClientService(FacesContext facesContext) {
        Map request = facesContext.getExternalContext().getRequestMap();

        String requestId = (String) request.get(REQUEST_ID);
        if (requestId == null) {
            AbstractHtmlService.sendJsError(facesContext, "Can not get '"
                    + REQUEST_ID + "' parameter !");
            return;
        }

        IClientServiceRegistry operationsRegistry = getClientServiceRegistry(facesContext);

        IClientService service = operationsRegistry
                .getClientServiceById(requestId);

        sendOperationStatus(facesContext, service, true);
    }

    protected void sendClientServiceStatus(FacesContext facesContext,
            IClientService operation, boolean sendResult) {
        int status = 0;
        int progress = -1;
        int errorCode = 0;

        if (operation != null) {
            status = operation.getStatus();
            progress = operation.getProgress();
            errorCode = operation.getErrorCode();
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        AbstractHtmlService.setNoCache(response);

        response.setContentType(PLAIN_TEXT_MIME_TYPE + "; charset="
                + AbstractHtmlService.RESPONSE_CHARSET);

        try {
            PrintWriter pw = response.getWriter();

            pw.println("status=" + status);
            if (progress >= 0) {
                pw.println("progress=" + progress);
            }
            if (errorCode != 0) {
                pw.println("errorCode=" + errorCode);
            }

        } catch (IOException ex) {
            LOG.error("Can not write status '" + status + "' for operation '"
                    + operation + "'.", ex);
        }

        facesContext.responseComplete();
    }

    private void cancelOperation(FacesContext facesContext) {
        Map request = facesContext.getExternalContext().getRequestMap();

        String operationKey = (String) request.get(REQUEST_ID);
        if (operationKey == null) {
            AbstractHtmlService.sendJsError(facesContext, "Can not get '"
                    + REQUEST_ID + "' parameter !");
            return;
        }

        IClientServiceRegistry clientServiceRegistry = getClientServiceRegistry(facesContext);

        IClientService clientService = clientServiceRegistry
                .getClientServiceById(operationKey);
        if (clientService != null) {
            clientService.cancel();
        }

        sendOperationStatus(facesContext, clientService, false);
    }

    private void sendOperationStatus(FacesContext facesContext,
            IClientService operation, boolean b) {
        // TODO Auto-generated method stub

    }

    public void initialize(FacesContext facesContext) {
    }

}
