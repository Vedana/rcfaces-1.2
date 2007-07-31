/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.component.UIData2;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.HtmlRequestContext;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.renderer.AbstractGridRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdditionalInformationService extends AbstractHtmlService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AdditionalInformationService.class);

    private static final String ADDITIONAL_INFORMATION_SERVICE_VERSION = "1.0.0";

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public void service(FacesContext facesContext, String commandId) {
        try {
            Map parameters = facesContext.getExternalContext()
                    .getRequestParameterMap();

            String componentId = (String) parameters.get("gridId");
            if (componentId == null) {
                AbstractHtmlService.sendJsError(facesContext, null,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find 'gridId' parameter.", null);
                return;
            }

            UIViewRoot viewRoot = facesContext.getViewRoot();

            if (viewRoot.getChildCount() == 0) {
                AbstractHtmlService.sendJsError(facesContext, componentId,
                        SESSION_EXPIRED_SERVICE_ERROR, "No view !", null);
                return;
            }

            componentId = HtmlTools.computeComponentId(facesContext,
                    componentId);
            UIComponent component = ComponentTools.getForComponent(
                    facesContext, componentId, viewRoot);
            if (component == null) {
                AbstractHtmlService.sendJsError(facesContext, componentId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find component '" + componentId + "'.", null);

                return;
            }

            if ((component instanceof IGridComponent) == false) {
                AbstractHtmlService.sendJsError(facesContext, componentId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find IAdditionalInformationProvider (id='"
                                + componentId + "').", null);
                return;
            }

            String rowValue = (String) parameters.get("rowValue");
            if (rowValue == null) {
                AbstractHtmlService.sendJsError(facesContext, null,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find 'rowValue' parameter.", null);
                return;
            }

            String rowIndex = (String) parameters.get("rowIndex");
            if (rowIndex == null) {
                AbstractHtmlService.sendJsError(facesContext, null,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find 'rowIndex' parameter.", null);
                return;
            }

            AbstractGridRenderer gridRenderer = getGridRenderer(facesContext,
                    (IGridComponent) component);
            if (gridRenderer == null) {
                sendJsError(facesContext, componentId,
                        AbstractHtmlService.INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find grid renderer. (gridId='" + componentId
                                + "')", null);
                return;
            }

            decodeSubComponents(facesContext, (IGridComponent) component,
                    parameters, rowIndex);

            ServletResponse response = (ServletResponse) facesContext
                    .getExternalContext().getResponse();

            setNoCache(response);
            response.setContentType(IHtmlRenderContext.HTML_TYPE + "; charset="
                    + RESPONSE_CHARSET);
            setCameliaResponse(response, ADDITIONAL_INFORMATION_SERVICE_VERSION);

            boolean useGzip = canUseGzip(facesContext);

            PrintWriter printWriter = null;

            try {
                if (useGzip == false) {
                    printWriter = response.getWriter();

                } else {
                    ConfiguredHttpServlet
                            .setGzipContentEncoding((HttpServletResponse) response);

                    OutputStream outputStream = response.getOutputStream();

                    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
                            outputStream, DEFAULT_BUFFER_SIZE);

                    Writer writer = new OutputStreamWriter(gzipOutputStream,
                            RESPONSE_CHARSET);

                    printWriter = new PrintWriter(writer, false);
                }

                writeAdditionalInformations(facesContext, printWriter,
                        (IGridComponent) component, gridRenderer, rowValue,
                        rowIndex);

            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }

        } catch (IOException ex) {
            throw new FacesException(
                    "Can not write dataGrid javascript rows !", ex);

        } catch (RuntimeException ex) {
            LOG.error("Catch runtime exception !", ex);

            throw ex;
        }

        facesContext.responseComplete();
    }

    private AbstractGridRenderer getGridRenderer(FacesContext facesContext,
            IGridComponent component) {

        Renderer renderer = getRenderer(facesContext, (UIComponent) component);

        if ((renderer instanceof AbstractGridRenderer) == false) {
            LOG.error("Renderer is not a valid type (AbstractGridRenderer) => "
                    + renderer);
            return null;
        }

        return (AbstractGridRenderer) renderer;
    }

    private void writeAdditionalInformations(FacesContext facesContext,
            PrintWriter printWriter, IGridComponent gridComponent,
            AbstractGridRenderer dgr, String rowValue, String rowIndex)
            throws WriterException {

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        Object states[] = dgr
                .getAdditionalInformationsRenderContextState(gridComponent);

        IHtmlRenderContext renderContext = HtmlRenderContext
                .restoreRenderContext(facesContext, states[0], true);

        renderContext.pushComponent((UIComponent) gridComponent,
                ((UIComponent) gridComponent).getClientId(facesContext));

        dgr.renderAdditionalInformation(renderContext, pw, gridComponent,
                RESPONSE_CHARSET, rowValue, rowIndex);

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }

    private void decodeSubComponents(FacesContext facesContext,
            IGridComponent dgc, Map parameters, String rowIndex) {

        if (parameters.containsKey(HtmlRequestContext.EVENT_SERIAL) == false) {
            return;
        }

        if (dgc instanceof UIData2) {
            ((UIData2) dgc).addDecodedIndexes(Integer.parseInt(rowIndex), 1);

            ((UIComponent) dgc).processDecodes(facesContext);
        }
    }
}
