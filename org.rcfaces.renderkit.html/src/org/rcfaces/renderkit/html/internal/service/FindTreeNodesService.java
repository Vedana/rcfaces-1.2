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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.IOutlinedLabelCapability;
import org.rcfaces.core.component.iterator.ISelectItemFinder;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.item.BasicSelectItemPath;
import org.rcfaces.core.item.ISelectItemPath;
import org.rcfaces.core.lang.FilterPropertiesMap;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.util.SelectItemTools;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.HtmlTools.ILocalizedComponent;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.renderer.TreeRenderer;
import org.rcfaces.renderkit.html.internal.util.MapDecoder;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FindTreeNodesService extends AbstractHtmlService {

    private static final Log LOG = LogFactory
            .getLog(FindTreeNodesService.class);

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".FindTreeNodes";

    private static final boolean LOG_TREE = false;

    // private static final boolean LOG_JAVASCRIPT = true;

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private static final String FIND_TREENODE_SERVICE_VERSION = "1.0.0";

    public FindTreeNodesService() {
    }

    public static FindTreeNodesService getInstance(FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext).getServicesRegistry();
        if (serviceRegistry == null) {
            // Designer mode
            return null;
        }

        return (FindTreeNodesService) serviceRegistry.getService(facesContext,
                RenderKitFactory.HTML_BASIC_RENDER_KIT, SERVICE_ID);
    }

    public void service(FacesContext facesContext, String commandId) {
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        String treeId = (String) parameters.get("treeId");
        if (treeId == null) {
            sendJsError(facesContext, null, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'treeId' parameter.", null);
            return;
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot.getChildCount() == 0) {
            sendJsError(facesContext, treeId, SESSION_EXPIRED_SERVICE_ERROR,
                    "No view !", null);
            return;
        }

        String params = (String) parameters.get("params");
        if (params == null) {
            sendJsError(facesContext, treeId, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find 'text' parameter.", null);
            return;
        }

        Map<String, Object> properties = MapDecoder.parse(params);

        ILocalizedComponent localizedComponent = HtmlTools.localizeComponent(
                facesContext, treeId);
        if (localizedComponent == null) {
            // Cas special: la session a du expirée ....

            sendJsError(facesContext, treeId, INVALID_PARAMETER_SERVICE_ERROR,
                    "Can not find treeComponent (id='" + treeId + "').", null);

            return;
        }

        try {
            UIComponent component = localizedComponent.getComponent();

            if ((component instanceof TreeComponent) == false) {
                sendJsError(facesContext, treeId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Invalid treeComponent (id='" + treeId + "').", null);
                return;
            }

            TreeComponent treeComponent = (TreeComponent) component;

            TreeRenderer treeRenderer = getTreeRenderer(facesContext,
                    treeComponent);
            if (treeRenderer == null) {
                sendJsError(facesContext, treeId,
                        INVALID_PARAMETER_SERVICE_ERROR,
                        "Can not find treeRenderer. (treeId='" + treeId + "')",
                        null);
                return;
            }

            ServletResponse response = (ServletResponse) facesContext
                    .getExternalContext().getResponse();

            setNoCache(response);
            response.setContentType("application/x-www-form-urlencoded; charset="
                    + RESPONSE_CHARSET);
            setCameliaResponse(response, FIND_TREENODE_SERVICE_VERSION);

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

                writePaths(facesContext, printWriter, treeComponent, treeId,
                        treeRenderer, properties);

            } catch (IOException ex) {
                throw new FacesException("Can not write tree node paths !", ex);

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

    private TreeRenderer getTreeRenderer(FacesContext facesContext,
            TreeComponent treeComponent) {

        Renderer renderer = getRenderer(facesContext, treeComponent);

        if ((renderer instanceof TreeRenderer) == false) {
            return null;
        }

        return (TreeRenderer) renderer;
    }

    private void writePaths(FacesContext facesContext, PrintWriter printWriter,
            final TreeComponent treeComponent, String treeId,
            TreeRenderer treeRenderer, final Map<String, Object> parameters)
            throws IOException {

        final IFilterProperties filterProperties = new FilterPropertiesMap(
                parameters);

        IHtmlProcessContext htmlProcessContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        final List<ISelectItemPath> paths = new ArrayList<ISelectItemPath>();

        final EnumSet<IOutlinedLabelCapability.Method> methods = treeComponent
                .getOutlinedLabelMethodSet();

        final IAdapterManager adapterManager = htmlProcessContext
                .getRcfacesContext().getAdapterManager();

        ISelectItemFinder finder = findFinder(adapterManager, treeComponent);

        if (finder != null) {
            ISelectItemPath[] ps = finder.search(treeComponent, parameters);

            if (ps != null) {
                paths.addAll(Arrays.asList(ps));
            }

        } else {
            SelectItemTools
                    .traverseSelectItemTree(
                            facesContext,
                            treeComponent,
                            filterProperties,
                            new SelectItemTools.DefaultSelectItemNodeHandler<Boolean>() {

                                private List<SelectItem> stack = new ArrayList<SelectItem>();

                                @Override
                                public Boolean beginNode(UIComponent component,
                                        SelectItem selectItem) {

                                    stack.add(selectItem);

                                    String label = selectItem.getLabel();

                                    if (match(treeComponent, filterProperties,
                                            methods, label)) {
                                        paths.add(new BasicSelectItemPath(stack));
                                    }

                                    return null;
                                }

                                @Override
                                public Boolean endNode(UIComponent component,
                                        SelectItem selectItem) {

                                    stack.remove(stack.size() - 1);

                                    return null;
                                }

                            });
        }

        StringBuilder sb = new StringBuilder(1024);
        for (ISelectItemPath path : paths) {
            if (sb.length() > 0) {
                sb.append('&');
            }

            String normalizedPath = path.normalizePath(facesContext,
                    treeComponent, treeComponent.getConverter());
            sb.append(normalizedPath);
        }

        pw.print(sb.toString());

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }

    private ISelectItemFinder findFinder(IAdapterManager adapterManager,
            TreeComponent treeComponent) {
        for (UIComponent child : treeComponent.getChildren()) {
            if (child instanceof UISelectItem) {
                UISelectItem uiSelectItem = (UISelectItem) child;

                Object value = uiSelectItem.getValue();
                if (value == null) {
                    continue;
                }

                if (value instanceof ISelectItemFinder) {
                    return (ISelectItemFinder) value;
                }

                if (value instanceof IAdaptable) {
                    ISelectItemFinder finder = ((IAdaptable) value).getAdapter(
                            ISelectItemFinder.class, treeComponent);

                    if (finder != null) {
                        return finder;
                    }
                }

                if (org.rcfaces.core.internal.Constants.ADAPT_SELECT_ITEMS) {
                    ISelectItemFinder finder = adapterManager.getAdapter(value,
                            ISelectItemFinder.class, treeComponent);

                    if (finder != null) {
                        return finder;
                    }
                }
            }
        }
        return null;
    }

    protected boolean match(TreeComponent treeComponent,
            IFilterProperties filterProperties,
            EnumSet<IOutlinedLabelCapability.Method> methods, String label) {

        String text = filterProperties.getStringProperty("text");

        if (label.toLowerCase().indexOf(text.toLowerCase()) >= 0) {
            return true;
        }

        return false;
    }

}