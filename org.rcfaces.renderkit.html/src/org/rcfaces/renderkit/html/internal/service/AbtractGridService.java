/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.component.UIData2;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.renderkit.html.internal.HtmlRequestContext;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.HtmlTools.ILocalizedComponent;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.renderer.AbstractGridRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbtractGridService extends AbstractHtmlService {

    private static final Log LOG = LogFactory.getLog(AbtractGridService.class);

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

            ILocalizedComponent localizedComponent = HtmlTools
                    .localizeComponent(facesContext, componentId);

            if (localizedComponent == null) {
                AbstractHtmlService.sendJsError(facesContext, componentId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find component '" + componentId + "'.", null);

                return;
            }

            UIComponent component = localizedComponent.getComponent();

            try {
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

                AbstractGridRenderer gridRenderer = getGridRenderer(
                        facesContext, (IGridComponent) component);
                if (gridRenderer == null) {
                    sendJsError(
                            facesContext,
                            componentId,
                            AbstractHtmlService.INVALID_PARAMETER_SERVICE_ERROR,
                            "Can not find grid renderer. (gridId='"
                                    + componentId + "')", null);
                    return;
                }

                decodeSubComponents(facesContext, (IGridComponent) component,
                        parameters, rowIndex, null);

                ServletResponse response = (ServletResponse) facesContext
                        .getExternalContext().getResponse();

                setNoCache(response);
                response.setContentType(getResponseContentType() + "; charset="
                        + RESPONSE_CHARSET);
                setCameliaResponse(response,
                        ADDITIONAL_INFORMATION_SERVICE_VERSION);

                boolean useGzip = canUseGzip(facesContext);

                PrintWriter printWriter = null;

                try {
                    if (useGzip == false) {
                        printWriter = response.getWriter();

                    } else {
                        ConfiguredHttpServlet.setGzipContentEncoding(
                                (HttpServletResponse) response, true);

                        OutputStream outputStream = response.getOutputStream();

                        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
                                outputStream, DEFAULT_BUFFER_SIZE);

                        Writer writer = new OutputStreamWriter(
                                gzipOutputStream, RESPONSE_CHARSET);

                        printWriter = new PrintWriter(writer, false);
                    }

                    writeElement(facesContext, printWriter,
                            (IGridComponent) component, gridRenderer, rowValue,
                            rowIndex);

                    saveView(facesContext, null);

                } finally {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                }

            } finally {
                localizedComponent.end();
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

    protected String getResponseContentType() {
        return IHtmlRenderContext.HTML_TYPE;
    }

    protected abstract void writeElement(FacesContext facesContext,
            PrintWriter printWriter, IGridComponent component,
            AbstractGridRenderer gridRenderer, String rowValue, String rowIndex)
            throws WriterException;

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

    protected void decodeSubComponents(FacesContext facesContext,
            IGridComponent dgc, Map parameters, String rowIndex, String colIndex) {

        String eventSerial = (String) parameters
                .get(HtmlRequestContext.EVENT_SERIAL);
        if (eventSerial == null || eventSerial.length() == 0) {
            return;
        }

        if (dgc instanceof UIData2) {
            ((UIData2) dgc).addDecodedIndexes(Integer.parseInt(rowIndex), 1);

            ((UIComponent) dgc).processDecodes(facesContext);
        }

        if (colIndex != null) {
            IColumnIterator columnIterator = dgc.listColumns();
            UIColumn column = columnIterator.toArray()[Integer
                    .parseInt(colIndex)];
            column.processDecodes(facesContext);
        }

    }
}
