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
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.model.DefaultSortedComponent;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.renderer.DataGridRenderer;
import org.rcfaces.renderkit.html.internal.util.JavaScriptResponseWriter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataGridService extends AbstractHtmlService {
    private static final String REVISION = "$Revision$";

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".DataGrid";

    private static final Log LOG = LogFactory.getLog(DataGridService.class);

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public DataGridService() {
    }

    public static DataGridService getInstance(FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext).getServicesRegistry();
        if (serviceRegistry == null) {
            // Designer mode
            return null;
        }

        return (DataGridService) serviceRegistry.getService(facesContext,
                RenderKitFactory.HTML_BASIC_RENDER_KIT, SERVICE_ID);
    }

    public void service(FacesContext facesContext, String commandId) {
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        UIViewRoot viewRoot = facesContext.getViewRoot();

        String index_s = (String) parameters.get("index");
        if (index_s == null) {
            sendJsError(facesContext, "Can not find 'index' parameter.");
            return;
        }

        String dataGridId = (String) parameters.get("dataGridId");
        if (dataGridId == null) {
            sendJsError(facesContext, "Can not find 'dataGridId' parameter.");
            return;
        }

        String filterExpression = (String) parameters.get("filterExpression");

        int rowIndex = Integer.parseInt(index_s);

        UIComponent component = HtmlTools.getForComponent(facesContext,
                dataGridId, viewRoot);
        if (component == null) {
            // Cas special: la session a du expir�e ....

            sendCancel(facesContext, dataGridId, rowIndex);

            return;
        }

        if ((component instanceof DataGridComponent) == false) {
            sendJsError(facesContext, "Can not find dataGridComponent (id='"
                    + dataGridId + "').");
            return;
        }

        DataGridComponent dgc = (DataGridComponent) component;

        ISortedComponent sortedComponents[] = null;

        String sortIndex_s = (String) parameters.get("sortIndex");
        if (sortIndex_s != null) {
            DataColumnComponent columns[] = dgc.listColumns().toArray();

            StringTokenizer st1 = new StringTokenizer(sortIndex_s, ",");

            sortedComponents = new ISortedComponent[st1.countTokens() / 2];

            for (int i = 0; st1.hasMoreTokens(); i++) {
                String tok1 = st1.nextToken();
                String tok2 = st1.nextToken();

                int idx = Integer.parseInt(tok1);
                boolean order = "true".equalsIgnoreCase(tok2);

                sortedComponents[i] = new DefaultSortedComponent(columns[idx],
                        idx, order);
            }
        }

        DataGridRenderer dgr = getDataGridRenderer(facesContext, dgc);
        if (dgr == null) {
            sendJsError(facesContext,
                    "Can not find dataGridRenderer. (dataGridId='" + dataGridId
                            + "')");
            return;
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        setNoCache(response);
        response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                + "; charset=" + RESPONSE_CHARSET);

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

            writeJs(facesContext, printWriter, dgc, dataGridId, dgr, rowIndex,
                    sortedComponents, filterExpression);

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

        facesContext.responseComplete();

    }

    private void sendCancel(FacesContext facesContext, String componentId,
            int rowIndex) {
        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        setNoCache(response);
        response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                + "; charset=" + RESPONSE_CHARSET);

        try {
            PrintWriter printWriter = response.getWriter();

            IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(
                    facesContext, printWriter, null, null);

            String varId = jsWriter.getComponentVarName();

            jsWriter.write("var ").write(varId).write('=').writeCall("f_core",
                    "GetElementById").writeString(componentId).writeln(
                    ", document);");

            jsWriter.writeMethodCall("fa_cancelFilterRequest").writeInt(rowIndex)
                    .write(");");

        } catch (IOException ex) {
            throw new FacesException("Can not write cancel response.", ex);
        }

        facesContext.responseComplete();
    }

    private DataGridRenderer getDataGridRenderer(FacesContext facesContext,
            DataGridComponent component) {

        Renderer renderer = getRenderer(facesContext, component);

        if ((renderer instanceof DataGridRenderer) == false) {
            return null;
        }

        return (DataGridRenderer) renderer;
    }

    private void writeJs(FacesContext facesContext, PrintWriter printWriter,
            DataGridComponent dgc, String componentId, DataGridRenderer dgr,
            int rowIndex, ISortedComponent sortedComponents[],
            String filterExpression) throws IOException {

        DataGridRenderer.TableRenderContext tableContext = dgr
                .createTableContext(facesContext, dgc, rowIndex,
                        sortedComponents, filterExpression);

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(facesContext,
                pw, dgc, componentId);

        String varId = jsWriter.getComponentVarName();

        jsWriter.write("var ").write(varId).write('=').writeCall("f_core",
                "GetElementById").writeString(componentId).writeln(
                ", document);");

        jsWriter.writeMethodCall("_startNewPage").writeInt(rowIndex).writeln(
                ");");

        String rowVarId = jsWriter.getJavaScriptRenderContext()
                .allocateVarName();
        tableContext.setRowVarName(rowVarId);

        dgr.encodeJsTransactionalRows(jsWriter, tableContext, false);

        jsWriter.writeMethodCall("_updateNewPage").writeln(");");

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }
}