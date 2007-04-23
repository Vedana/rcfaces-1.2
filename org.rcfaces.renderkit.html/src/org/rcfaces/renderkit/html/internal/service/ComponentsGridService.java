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
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.renderer.ComponentsGridRenderer;
import org.rcfaces.renderkit.html.internal.util.JavaScriptResponseWriter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentsGridService extends AbstractHtmlService {
    private static final String REVISION = "$Revision$";

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".ComponentsGrid";

    private static final Log LOG = LogFactory
            .getLog(ComponentsGridService.class);

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private static final int INITIAL_SIZE = 8000;

    private static final String RENDER_CONTEXT_STATE = "camelia.cls.renderContext";

    private static final String COMPONENTS_LIST_SERVICE_VERSION = "1.0.0";

    public ComponentsGridService() {
    }

    public static ComponentsGridService getInstance(FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext).getServicesRegistry();
        if (serviceRegistry == null) {
            // Designer mode
            return null;
        }

        return (ComponentsGridService) serviceRegistry.getService(facesContext,
                RenderKitFactory.HTML_BASIC_RENDER_KIT, SERVICE_ID);
    }

    public void service(FacesContext facesContext, String commandId) {
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        String componentsGridId = (String) parameters.get("componentsGridId");
        if (componentsGridId == null) {
            sendJsError(facesContext, null, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'componentsListId' parameter.", null);
            return;
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot.getChildCount() == 0) {
            sendJsError(facesContext, componentsGridId,
                    SESSION_EXPIRED_SERVICE_ERROR, "No view !", null);
            return;
        }

        String index_s = (String) parameters.get("index");
        if (index_s == null) {
            sendJsError(facesContext, componentsGridId,
                    INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'index' parameter.", null);
            return;
        }

        String filterExpression = (String) parameters.get("filterExpression");

        String forcedRows_s = (String) parameters.get("rows");

        int rowIndex = Integer.parseInt(index_s);
        int forcedRows = -1;
        if (forcedRows_s != null && forcedRows_s.length() > 0) {
            forcedRows = Integer.parseInt(forcedRows_s);
            if (forcedRows < 1) {
                forcedRows = -1;
            }
        }

        UIComponent component = HtmlTools.getForComponent(facesContext,
                componentsGridId, viewRoot);
        if (component == null) {
            // Cas special: la session a du expir�e ....

            sendJsError(facesContext, componentsGridId,
                    INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find componentsGrid component (id='"
                            + componentsGridId + "').", null);

            return;
        }

        if ((component instanceof ComponentsGridComponent) == false) {
            sendJsError(facesContext, componentsGridId,
                    INVALID_PARAMETER_SERVICE_ERROR,
                    "Invalid componentsGrid component (id='" + componentsGridId
                            + "').", null);
            return;
        }

        ComponentsGridComponent dgc = (ComponentsGridComponent) component;

        ISortedComponent sortedComponents[] = null;

        /*
         * String sortIndex_s = (String) parameters.get("sortIndex"); if
         * (sortIndex_s != null) { DataColumnComponent columns[] =
         * dgc.listColumns().toArray();
         * 
         * String sortOrder_s = (String) parameters.get("sortOrder");
         * 
         * StringTokenizer st1 = new StringTokenizer(sortIndex_s, ",");
         * StringTokenizer st2 = null; if (sortOrder_s != null) { st2 = new
         * StringTokenizer(sortOrder_s, ","); }
         * 
         * sortedComponents = new ISortedComponent[st1.countTokens()];
         * 
         * for (int i = 0; st1.hasMoreTokens(); i++) { String tok1 =
         * st1.nextToken(); String tok2 = null; if (st2 != null) { tok2 =
         * st2.nextToken(); }
         * 
         * int idx = Integer.parseInt(tok1); boolean order =
         * "true".equalsIgnoreCase(tok2);
         * 
         * sortedComponents[i] = new DefaultSortedComponent(columns[idx], idx,
         * order); } }
         */

        ComponentsGridRenderer dgr = getComponentsGridRenderer(facesContext,
                dgc);
        if (dgr == null) {
            sendJsError(facesContext, componentsGridId,
                    INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find componentsGrid renderer. (componentsGridId='"
                            + componentsGridId + "')", null);
            return;
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        setNoCache(response);
        response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                + "; charset=" + RESPONSE_CHARSET);
        setCameliaResponse(response, COMPONENTS_LIST_SERVICE_VERSION);

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

            writeJs(facesContext, printWriter, dgc, componentsGridId, dgr,
                    rowIndex, forcedRows, sortedComponents, filterExpression);

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

    private ComponentsGridRenderer getComponentsGridRenderer(
            FacesContext facesContext, ComponentsGridComponent component) {

        Renderer renderer = getRenderer(facesContext, component);

        if ((renderer instanceof ComponentsGridRenderer) == false) {
            return null;
        }

        return (ComponentsGridRenderer) renderer;
    }

    private void writeJs(FacesContext facesContext, PrintWriter printWriter,
            ComponentsGridComponent dgc, String componentClientId,
            ComponentsGridRenderer dgr, int rowIndex, int forcedRows,
            ISortedComponent sortedComponents[], String filterExpression)
            throws IOException {

        IProcessContext processContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        Object states[] = (Object[]) dgc.getAttributes().get(
                RENDER_CONTEXT_STATE);
        String contentType = (String) states[1];

        IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(facesContext,
                pw, dgc, componentClientId);

        String varId = jsWriter.getComponentVarName();

        jsWriter.write("var ").write(varId).write('=').writeCall("f_core",
                "GetElementByClientId").writeString(componentClientId).writeln(
                ", document);");

        jsWriter.writeMethodCall("f_startNewPage").writeInt(rowIndex).writeln(
                ");");

        ResponseWriter oldWriter = facesContext.getResponseWriter();
        ResponseStream oldStream = facesContext.getResponseStream();

        try {
            CharArrayWriter myWriter = new CharArrayWriter(INITIAL_SIZE);

            ResponseWriter newWriter = facesContext.getRenderKit()
                    .createResponseWriter(myWriter, contentType,
                            RESPONSE_CHARSET);

            facesContext.setResponseWriter(newWriter);

            IRenderContext renderContext = HtmlRenderContext
                    .restoreRenderContext(facesContext, states[0], true);

            renderContext.pushComponent(dgc, componentClientId);

            IComponentWriter writer = renderContext.getComponentWriter();

            // IComponentTreeRenderProcessor
            // componentTreeRenderProcessor=ComponentTreeRenderProcessorFactory.get(facesContext)

            ComponentsGridRenderer.ComponentsGridRenderContext listContext = dgr
                    .createComponentsGridContext(processContext, jsWriter
                            .getJavaScriptRenderContext(), dgc, rowIndex,
                            forcedRows, sortedComponents, filterExpression);

            dgr.encodeChildren(writer, listContext);

            newWriter.flush();

            String buffer = myWriter.toString();

            int rowCount = 10;

            jsWriter.writeMethodCall("f_updateNewPage").writeInt(rowCount)
                    .write(',').writeString(buffer).writeln(");");

        } finally {
            if (oldWriter != null) {
                facesContext.setResponseWriter(oldWriter);
            }

            if (oldStream != null) {
                facesContext.setResponseStream(oldStream);
            }
        }

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }

    public void setupComponent(IComponentRenderContext componentRenderContext) {
        UIComponent dataListComponent = componentRenderContext.getComponent();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        Object state = htmlRenderContext.saveRenderContextState();

        if (state != null) {
            String contentType = htmlRenderContext.getFacesContext()
                    .getResponseWriter().getContentType();

            dataListComponent.getAttributes().put(RENDER_CONTEXT_STATE,
                    new Object[] { state, contentType });
        }
    }
}
