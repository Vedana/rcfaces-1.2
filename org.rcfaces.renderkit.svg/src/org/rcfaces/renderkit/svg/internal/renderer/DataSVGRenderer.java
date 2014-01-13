package org.rcfaces.renderkit.svg.internal.renderer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.CharArrayWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolTipComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.IRowToolTipIdCapability;
import org.rcfaces.core.internal.capability.IColumnsContainer;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.model.IComponentRefModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.renderer.ITooltipGridRenderer;
import org.rcfaces.renderkit.html.internal.renderer.TooltipContainerRenderContext;
import org.rcfaces.renderkit.svg.component.DataSVGComponent;
import org.rcfaces.renderkit.svg.component.SVGDataColumnComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataSVGRenderer extends SVGRenderer implements
        ITooltipGridRenderer {

    private static final Log LOG = LogFactory.getLog(DataSVGRenderer.class);

    private static final String TOOLTIPS_RENDER_CONTEXT_STATE = "org.rcfaces.html.TT_CONTEXT";

    private static final String RENDER_CONTEXT_PROPERTY = "org.rcfaces.svg.DATA_SVG_CONTEXT";

    @Override
    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        super.encodeEnd(writer);

        ((IHtmlWriter) writer).enableJavaScript();
    }

    @Override
    protected void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {
        super.encodeJavaScript(jsWriter);

        DataSVGComponent component = (DataSVGComponent) jsWriter
                .getComponentRenderContext().getComponent();
        FacesContext facesContext = jsWriter.getFacesContext();

        DataSVGContext dataContext = getContext(jsWriter
                .getComponentRenderContext());

        String var = component.getVar(facesContext);

        Map<String, Object> requestMap = facesContext.getExternalContext()
                .getRequestMap();

        List<UIColumn> columns = ComponentIterators.list(component,
                UIColumn.class);
        Map<String, SVGDataColumnComponent> columnById = new HashMap<String, SVGDataColumnComponent>(
                columns.size());
        for (UIColumn column : columns) {
            if (column instanceof SVGDataColumnComponent) {
                columnById.put(column.getId(), (SVGDataColumnComponent) column);
            }
        }

        DataModel dataModel = dataContext.getDataModel();
        try {
            if (columnById.isEmpty()) {
                BeanInfo beanInfo = null;

                dataModel.setRowIndex(0);
                if (dataModel.isRowAvailable()) {
                    Object sampleData = dataModel.getRowData();

                    if (sampleData != null) {
                        try {
                            beanInfo = Introspector.getBeanInfo(sampleData
                                    .getClass());
                        } catch (IntrospectionException ex) {
                            LOG.error("Can not inspect sample data '"
                                    + sampleData + "'", ex);
                        }
                    }
                }

                addColumn(facesContext, component, columnById, beanInfo,
                        "svgID", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "visibility", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "color", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "text", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "styleClass", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "fill", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "tooltipText", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "value", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "selectable", var);
                addColumn(facesContext, component, columnById, beanInfo,
                        "audioDescription", var);
            }

            SVGDataColumnComponent idColumn = columnById.get("svgID");
            if (idColumn == null) {
                throw new FacesException("svgID column must be defined !");
            }
            SVGDataColumnComponent visibilityColumn = columnById
                    .get("visibility");
            SVGDataColumnComponent colorColumn = columnById.get("color");
            SVGDataColumnComponent textColumn = columnById.get("text");
            SVGDataColumnComponent styleClassColumn = columnById
                    .get("styleClass");
            SVGDataColumnComponent fillColumn = columnById.get("fill");
            SVGDataColumnComponent tooltipTextColumn = columnById
                    .get("tooltipText");
            SVGDataColumnComponent valueColumn = columnById.get("value");
            SVGDataColumnComponent selectableColumn = columnById
                    .get("selectable");
            SVGDataColumnComponent audioDescriptionColumn = columnById
                    .get("audioDescription");

            Object oldValue = requestMap.get(var);
            try {
                for (int index = 0;; index++) {
                    dataModel.setRowIndex(index);

                    if (dataModel.isRowAvailable() == false) {
                        break;
                    }

                    Object periodData = dataModel.getRowData();

                    requestMap.put(var, periodData);

                    String id = String.valueOf(idColumn.getValue());
                    if (id == null || id.length() == 0) {
                        continue;
                    }

                    Boolean visibility = null;
                    String color = null;
                    String fill = null;
                    String styleClass = null;
                    String text = null;
                    String tooltipText = null;
                    String value = null;
                    Boolean selectable = null;
                    String audioDescription = null;

                    if (visibilityColumn != null) {
                        visibility = ValuesTools
                                .valueToBoolean(visibilityColumn);
                    }

                    if (colorColumn != null) {
                        color = ValuesTools.valueToString(colorColumn,
                                facesContext);
                    }

                    if (styleClassColumn != null) {
                        styleClass = ValuesTools.valueToString(
                                styleClassColumn, facesContext);
                    }

                    if (fillColumn != null) {
                        fill = ValuesTools.valueToString(fillColumn,
                                facesContext);
                    }

                    if (textColumn != null) {
                        text = ValuesTools.valueToString(textColumn,
                                facesContext);
                    }

                    if (tooltipTextColumn != null) {
                        tooltipText = ValuesTools.valueToString(
                                tooltipTextColumn, facesContext);
                    }

                    if (valueColumn != null) {
                        value = ValuesTools.valueToString(valueColumn,
                                facesContext);
                    }

                    if (selectableColumn != null) {
                        selectable = ValuesTools
                                .valueToBoolean(selectableColumn);
                    }

                    if (audioDescriptionColumn != null) {
                        audioDescription = ValuesTools.valueToString(
                                audioDescriptionColumn, facesContext);
                    }

                    ToolTipComponent tooltipComponent = null;

                    if (component instanceof IRowToolTipIdCapability) {
                        String tooltipClientId = ((IRowToolTipIdCapability) component)
                                .getRowToolTipId();

                        if (tooltipClientId != null
                                && tooltipClientId.length() > 0) {
                            IRenderContext renderContext = jsWriter
                                    .getHtmlRenderContext();

                            if (tooltipClientId.charAt(0) != ':') {
                                tooltipClientId = renderContext
                                        .computeBrotherComponentClientId(
                                                component, tooltipClientId);
                            }

                            if (tooltipClientId != null) {
                                UIComponent comp = renderContext
                                        .getFacesContext().getViewRoot()
                                        .findComponent(tooltipClientId);
                                if (comp instanceof ToolTipComponent) {
                                    tooltipComponent = (ToolTipComponent) comp;

                                    dataContext
                                            .registerTooltip(tooltipComponent);

                                    if (tooltipComponent.isRendered() == false) {
                                        tooltipComponent = null;
                                    }
                                }
                            }
                        }
                    }

                    if (tooltipComponent == null) {
                        tooltipComponent = dataContext.findTooltipByIdOrName(
                                jsWriter.getComponentRenderContext(),
                                component, "#row", null);
                    }

                    String tooltipId = null;
                    String tooltipContent = null;
                    if (tooltipComponent != null) {
                        tooltipText = null;

                        if (tooltipComponent.getAsyncRenderMode(facesContext) == IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                            tooltipContent = encodeToolTip(jsWriter,
                                    tooltipComponent);
                            tooltipId = jsWriter.allocateString("##CONTENT");

                        } else {
                            tooltipId = tooltipComponent
                                    .getClientId(facesContext);

                            if (tooltipId != null) {
                                tooltipId = jsWriter.allocateString(tooltipId);
                            }
                        }
                    }

                    if (visibility == null && color == null
                            && styleClass == null && fill == null
                            && text == null && tooltipText == null
                            && value == null && selectable == null
                            && tooltipId == null && tooltipComponent == null) {
                        continue;
                    }

                    String colorVar = null;
                    if (color != null) {
                        colorVar = jsWriter.allocateString(color);
                    }
                    String styleClassVar = null;
                    if (styleClass != null) {
                        styleClassVar = jsWriter.allocateString(styleClass);
                    }
                    String fillVar = null;
                    if (fill != null) {
                        fillVar = jsWriter.allocateString(fill);
                    }

                    jsWriter.writeMethodCall("_update").writeString(id)
                            .write(',');

                    IObjectLiteralWriter objectLiteralWriter = jsWriter
                            .writeObjectLiteral(false);

                    objectLiteralWriter.writeSymbol("_rowIndex")
                            .writeInt(index);

                    if (visibility != null) {
                        objectLiteralWriter.writeSymbol("_visibility")
                                .writeBoolean(visibility.booleanValue());
                    }
                    if (colorVar != null) {
                        objectLiteralWriter.writeSymbol("_color").write(
                                colorVar);
                    }
                    if (styleClassVar != null) {
                        objectLiteralWriter.writeSymbol("_styleClass").write(
                                styleClassVar);
                    }
                    if (fillVar != null) {
                        objectLiteralWriter.writeSymbol("_fill").write(fillVar);
                    }
                    if (text != null) {
                        objectLiteralWriter.writeSymbol("_text").writeString(
                                text);
                    }
                    if (value != null) {
                        objectLiteralWriter.writeSymbol("_value").writeString(
                                value);
                    }
                    if (selectable != null) {
                        objectLiteralWriter.writeSymbol("_selectable")
                                .writeBoolean(selectable.booleanValue());
                    }
                    if (audioDescription != null) {
                        objectLiteralWriter.writeSymbol("_audioDescription")
                                .writeString(audioDescription);
                    }

                    if (tooltipId != null) {
                        objectLiteralWriter.writeSymbol("_toolTipId").write(
                                tooltipId);

                        if (tooltipContent != null) {
                            objectLiteralWriter.writeSymbol("_toolTipContent")
                                    .writeString(tooltipContent);
                        }

                    } else if (tooltipText != null) {
                        objectLiteralWriter.writeSymbol("_tooltipText")
                                .writeString(tooltipText);
                    }

                    objectLiteralWriter.end().writeln(");");

                }
            } finally {
                requestMap.put(var, oldValue);
            }
        } finally {
            dataModel.setRowIndex(-1);
        }
    }

    private void addColumn(FacesContext facesContext, DataSVGComponent grid,
            Map<String, SVGDataColumnComponent> columnById, BeanInfo beanInfo,
            String fieldName, String var) {

        if (beanInfo != null) {
            boolean found = false;

            if (Map.class.isAssignableFrom(beanInfo.getBeanDescriptor()
                    .getBeanClass()) == false) {
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

                for (int i = 0; i < pds.length; i++) {
                    PropertyDescriptor pd = pds[i];

                    if (pd.getName().equals(fieldName)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found == false) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Can not find fieldName '" + fieldName
                            + "' into bean '"
                            + beanInfo.getBeanDescriptor().getBeanClass()
                            + "': IGNORE IT");
                }
                return;
            }
        }

        ELContext elContext = facesContext.getELContext();

        ValueExpression valueExpression = facesContext
                .getApplication()
                .getExpressionFactory()
                .createValueExpression(elContext,
                        "#{" + var + "." + fieldName + "}", Object.class);

        SVGDataColumnComponent column = new SVGDataColumnComponent(fieldName);
        column.setValueExpression("value", valueExpression);

        grid.getChildren().add(column);
        columnById.put(fieldName, column);
    }

    protected String encodeToolTip(IJavaScriptWriter jsWriter,
            ToolTipComponent tooltipComponent) throws WriterException {

        CharArrayWriter writer = new CharArrayWriter(8000);

        encodeToolTip(jsWriter.getFacesContext(), writer, jsWriter
                .getComponentRenderContext().getComponent(), tooltipComponent,
                jsWriter.getResponseCharacterEncoding());

        writer.close();

        return writer.toString();
    }

    // peut surment ne pas recopier se code
    protected void encodeToolTip(FacesContext facesContext, Writer writer,
            UIComponent component, ToolTipComponent tooltipComponent,
            String responseCharacterEncoding) throws WriterException {

        ResponseWriter oldResponseWriter = facesContext.getResponseWriter();
        ResponseWriter newResponseWriter;

        ResponseStream oldResponseStream = null;
        if (oldResponseWriter == null) {
            // Appel AJAX pour un TRI par exemple ... (ou changement de page)

            oldResponseStream = facesContext.getResponseStream();

            Object states[] = getTooltipsRenderContextState((IColumnsContainer) component);
            if (states == null) {
                throw new FacesException(
                        "Can not get render context state for tooltip of gridComponent='"
                                + component + "'");
            }

            newResponseWriter = facesContext.getRenderKit()
                    .createResponseWriter(writer, (String) states[1],
                            responseCharacterEncoding);

            HtmlRenderContext.restoreRenderContext(facesContext, states[0],
                    true);

        } else {
            newResponseWriter = oldResponseWriter.cloneWithWriter(writer);
        }

        facesContext.setResponseWriter(newResponseWriter);
        try {
            ComponentTools.encodeChildrenRecursive(facesContext,
                    tooltipComponent);

        } finally {
            if (oldResponseWriter != null) {
                facesContext.setResponseWriter(oldResponseWriter);
            }

            if (oldResponseStream != null) {
                facesContext.setResponseStream(oldResponseStream);
            }
        }
    }

    @Override
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATA_SVG;
    }

    @Override
    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        DataSVGComponent component = (DataSVGComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        DataSVGContext dataContext = getContext(htmlWriter
                .getComponentRenderContext());

        boolean ajax = false;

        if (dataContext.containsTooltips()) {
            ajax = true;
            javaScriptRenderContext.appendRequiredClass(
                    JavaScriptClasses.DATA_SVG, "toolTip");

            IHtmlRenderContext htmlRenderContext = htmlWriter
                    .getHtmlComponentRenderContext().getHtmlRenderContext();
            Object state = htmlRenderContext.saveState(htmlRenderContext
                    .getFacesContext());

            if (LOG.isDebugEnabled()) {
                LOG.debug("Save context state on "
                        + ((UIComponent) component).getId() + " for tooltips");
            }

            if (state != null) {
                String contentType = htmlRenderContext.getFacesContext()
                        .getResponseWriter().getContentType();

                component.getAttributes().put(TOOLTIPS_RENDER_CONTEXT_STATE,
                        new Object[] { state, contentType });
            }
        }

        if (ajax) {
            addAjaxRequiredClasses(javaScriptRenderContext);
        }
    }

    protected void addAjaxRequiredClasses(
            IJavaScriptRenderContext javaScriptRenderContext) {
        javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.DATA_SVG,
                "ajax");
    }

    public Object[] getTooltipsRenderContextState(IColumnsContainer component) {
        Object[] state = (Object[]) ((UIComponent) component).getAttributes()
                .get(TOOLTIPS_RENDER_CONTEXT_STATE);

        return state;
    }

    protected DataSVGContext getContext(
            IComponentRenderContext componentRenderContext) {
        DataSVGContext context = (DataSVGContext) componentRenderContext
                .getAttribute(RENDER_CONTEXT_PROPERTY);
        if (context != null) {
            return context;
        }
        context = new DataSVGContext(componentRenderContext);
        componentRenderContext.setAttribute(RENDER_CONTEXT_PROPERTY, context);

        return context;
    }

    public static class DataSVGContext extends TooltipContainerRenderContext {

        private final DataModel dataModel;

        public DataSVGContext(IComponentRenderContext componentRenderContext) {
            super(componentRenderContext.getComponent());

            DataSVGComponent dataComponent = (DataSVGComponent) componentRenderContext
                    .getComponent();
            FacesContext facesContext = componentRenderContext
                    .getFacesContext();

            dataModel = GridTools.getDataModel(
                    dataComponent.getDataModel(facesContext), dataComponent,
                    facesContext);
        }

        public DataModel getDataModel() {
            return dataModel;
        }
    }

    public void renderTooltip(IHtmlWriter htmlWriter,
            IColumnsContainer columnsContainer,
            String responseCharacterEncoding, String rowValue, String rowIndex,
            String tooltipId) throws WriterException {

        DataSVGComponent gridComponent = (DataSVGComponent) columnsContainer;

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        // On prepare le DataModel !
        DataSVGContext context = getContext(htmlWriter
                .getHtmlComponentRenderContext());
        try {
            if (isDataModelRowAvailable(htmlRenderContext, gridComponent,
                    responseCharacterEncoding, rowValue,
                    Integer.parseInt(rowIndex)) == false) {
                return;
            }

            FacesContext facesContext = htmlRenderContext.getFacesContext();

            ToolTipComponent tooltipComponent = (ToolTipComponent) facesContext
                    .getViewRoot().findComponent(tooltipId);

            if (tooltipComponent == null) {
                return;
            }

            Map<String, Object> requestMap = facesContext.getExternalContext()
                    .getRequestMap();

            Object data = context.getDataModel().getRowData();
            String var = gridComponent.getVar(facesContext);

            Object old = requestMap.put(var, data);
            try {
                encodeToolTip(facesContext, facesContext.getResponseWriter(),
                        gridComponent, tooltipComponent,
                        responseCharacterEncoding);

            } finally {
                if (old == null) {
                    requestMap.remove(var);
                } else {
                    requestMap.put(var, old);
                }
            }

        } finally {
            context.getDataModel().setRowIndex(-1);
        }
    }

    public boolean isDataModelRowAvailable(
            IHtmlRenderContext htmlRenderContext,
            DataSVGComponent gridComponent, String responseCharacterEncoding,
            String rowValue2, int rowIndex) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) htmlRenderContext
                .getComponentWriter();

        DataSVGContext tableContext = getContext(htmlWriter
                .getHtmlComponentRenderContext());

        DataModel dataModel = tableContext.getDataModel();

        IComponentRefModel componentRefModel = getAdapter(
                IComponentRefModel.class, dataModel);

        if (componentRefModel != null) {
            componentRefModel.setComponent(gridComponent);
        }

        IFilterProperties filtersMap = gridComponent.getFilterProperties();
        IFiltredModel filtredDataModel = getAdapter(IFiltredModel.class,
                dataModel);

        if (filtersMap != null) {
            if (filtredDataModel != null) {
                filtredDataModel.setFilter(filtersMap);
            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
            }

        } else if (filtredDataModel != null) {
            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
        }

        dataModel.setRowIndex(rowIndex);
        return dataModel.isRowAvailable();
    }
}
