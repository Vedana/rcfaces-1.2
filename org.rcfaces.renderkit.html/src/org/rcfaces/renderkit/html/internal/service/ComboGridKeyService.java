/*
 * $Id$
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
import org.rcfaces.core.component.KeyEntryComponent;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.HtmlTools.ILocalizedComponent;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.renderer.DataGridRenderer.DataGridRenderContext;
import org.rcfaces.renderkit.html.internal.renderer.KeyEntryRenderer;
import org.rcfaces.renderkit.html.internal.util.JavaScriptResponseWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboGridKeyService extends AbstractHtmlService {

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".ComboGridKey";

    private static final Log LOG = LogFactory.getLog(ImageService.class);

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private static final int INITIAL_SIZE = 8000;

    private static final String COMBO_GRID_KEY_SERVICE_VERSION = "1.0.0";

    public ComboGridKeyService() {
    }

    public void service(FacesContext facesContext, String commandId) {
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        UIViewRoot viewRoot = facesContext.getViewRoot();

        String componentClientId = (String) parameters.get("gridId");
        if (componentClientId == null) {
            sendJsError(facesContext, null, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'componentId' parameter.", null);
            return;
        }

        if (viewRoot.getChildCount() == 0) {
            sendJsError(facesContext, componentClientId,
                    SESSION_EXPIRED_SERVICE_ERROR, "No view !", null);
            return;
        }

        String rowKey = (String) parameters.get("key");
        if (rowKey == null) {
            sendJsError(facesContext, null, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'key' parameter.", null);
            return;
        }

        String filterExpression = (String) parameters.get("filterExpression");

        ILocalizedComponent localizedComponent = HtmlTools.localizeComponent(
                facesContext, componentClientId);
        if (localizedComponent == null) {
            // Cas special: la session a du expir�e ....

            sendJsError(facesContext, componentClientId,
                    INVALID_PARAMETER_SERVICE_ERROR,
                    "Component is not found !", null);

            return;
        }

        try {

            UIComponent component = localizedComponent.getComponent();

            if ((component instanceof KeyEntryComponent) == false) {
                sendJsError(facesContext, componentClientId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Component is invalid. (not an ComboGridComponent)",
                        null);
                return;
            }

            KeyEntryComponent comboGridComponent = (KeyEntryComponent) component;

            KeyEntryRenderer comboGridRenderer = getComboGridRenderer(
                    facesContext, comboGridComponent);
            if (comboGridRenderer == null) {
                sendJsError(facesContext, componentClientId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find comboGridRenderer.", null);
                return;
            }

            ServletResponse response = (ServletResponse) facesContext
                    .getExternalContext().getResponse();

            setNoCache(response);
            response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                    + "; charset=" + RESPONSE_CHARSET);

            setCameliaResponse(response, COMBO_GRID_KEY_SERVICE_VERSION);

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

                    Writer writer = new OutputStreamWriter(gzipOutputStream,
                            RESPONSE_CHARSET);

                    printWriter = new PrintWriter(writer, false);
                }

                writeJs(facesContext, printWriter, comboGridComponent,
                        comboGridRenderer, filterExpression, componentClientId,
                        rowKey);

            } catch (IOException ex) {
                throw new FacesException(
                        "Can not write dataGrid javascript rows !", ex);

            } catch (RuntimeException ex) {
                LOG.error("Catch runtime exception !", ex);

                throw ex;

            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }

        } finally {
            localizedComponent.end();
        }

        facesContext.responseComplete();

    }

    private KeyEntryRenderer getComboGridRenderer(FacesContext facesContext,
            KeyEntryComponent component) {

        Renderer renderer = getRenderer(facesContext, component);

        if ((renderer instanceof KeyEntryRenderer) == false) {
            return null;
        }

        return (KeyEntryRenderer) renderer;
    }

    private void writeJs(FacesContext facesContext, PrintWriter printWriter,
            KeyEntryComponent comboGridComponent,
            KeyEntryRenderer comboGridRenderer, String filterExpression,
            String componentClientId, String rowKey) throws IOException {

        IProcessContext processContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        // FILL

        IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(facesContext,
                pw, RESPONSE_CHARSET, comboGridComponent, componentClientId);

        DataGridRenderContext tableContext = comboGridRenderer
                .createTableContext(processContext,
                        jsWriter.getJavaScriptRenderContext(),
                        comboGridComponent, 0, -1, null, filterExpression,
                        null, null, null);

        tableContext.getFiltersMap().put("key", rowKey);

        String varId = jsWriter.getComponentVarName();

        jsWriter.write("var ").write(varId).write('=')
                .writeCall("f_core", "GetElementByClientId")
                .writeString(componentClientId).writeln(", document);");

        comboGridRenderer.encodeRowByKey(jsWriter, tableContext);

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }
}