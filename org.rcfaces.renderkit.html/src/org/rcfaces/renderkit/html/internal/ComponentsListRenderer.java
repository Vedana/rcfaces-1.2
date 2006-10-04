/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsListComponent;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.service.ComponentsListService;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentsListRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ComponentsListRenderer.class);

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    private static final boolean ENABLE_SERVER_REQUEST = true;

    private static final String LIST_CONTEXT = "componentsList.listContext";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ComponentsListComponent componentsListComponent = (ComponentsListComponent) componentRenderContext
                .getComponent();

        componentsListComponent.setRowIndex(-1);

        ListContext listContext = new ListContext(facesContext,
                componentsListComponent);
        componentRenderContext.setAttribute(LIST_CONTEXT, listContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV", componentsListComponent);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (ENABLE_SERVER_REQUEST) {
            ComponentsListService componentsListServer = ComponentsListService
                    .getInstance(facesContext);
            if (componentsListServer != null) {
                htmlWriter.writeAttribute("v:asyncRender", "true");
            }

            /* Si le tableau n'est pas visible ! */

            String interactiveComponentClientId = getHtmlRenderContext(
                    htmlWriter).getCurrentInteractiveRenderComponentClientId();

            if (interactiveComponentClientId != null) {
                // Pas de donn�es si nous sommes dans un scope interactif !
                htmlWriter.writeAttribute("v:interactiveShow",
                        interactiveComponentClientId);

                listContext.setInteractiveShow(true);
            }
        }

        /*
         * if (listContext.getDataModel() instanceof IFiltredDataModel) {
         * htmlWriter.writeAttribute("v:filtred", "true");
         * 
         * IFilterProperties filterMap = listContext.getFiltersMap(); if
         * (filterMap != null && filterMap.isEmpty() == false) { String
         * filterExpression = HtmlTools .encodeFilterExpression(filterMap);
         * htmlWriter.writeAttribute("v:filterExpression", filterExpression); } }
         */

        int rows = listContext.getRows();
        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }

        int rowCount = listContext.getRowCount();
        if (rowCount >= 0) {
            htmlWriter.writeAttribute("v:rowCount", rowCount);
        }

        int first = listContext.getFirst();
        if (first > 0) {
            htmlWriter.writeAttribute("v:first", first);
        }

        htmlWriter.startElement("TABLE");

        String w = componentsListComponent.getWidth(facesContext);
        if (w != null) {
            htmlWriter.writeAttribute("width", "100%");
        }

        String ccls = componentsListComponent.getColumnStyleClass(facesContext);
        String columnClasses[] = parseClasses(ccls);

        int columnNumber = listContext.getColumnNumber();
        if (columnNumber < 1) {
            columnNumber = 1;
        }

        if (columnNumber > 0) {
            for (int i = 0; i < columnNumber; i++) {
                htmlWriter.startElement("COL");
                htmlWriter.writeAttribute("width", "1*");

                if (columnClasses == null || columnClasses.length < 1) {
                    htmlWriter.endElement("COL");
                    continue;
                }
                int rs = i % columnClasses.length;

                htmlWriter.writeAttribute("class", columnClasses[rs]);
                htmlWriter.endElement("COL");
            }
        }

        htmlWriter.startElement("TBODY");
        String className = getStyleClassName(componentRenderContext,
                componentsListComponent);
        htmlWriter.writeAttribute("class", className + "_tbody");
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter(facesContext);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        ListContext listContext = (ListContext) componentRenderContext
                .getAttribute(LIST_CONTEXT);

        // Dans tous les cas il faut positionner le renderContext !
        ComponentsListService componentsListServer = ComponentsListService
                .getInstance(facesContext);
        componentsListServer.setupComponent(componentRenderContext);

        if (listContext.isInteractiveShow()) {
            return;
        }

        encodeChildren(htmlWriter, listContext);
    }

    public void encodeChildren(IComponentWriter writer, ListContext listContext)
            throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        ComponentsListComponent componentsListComponent = (ComponentsListComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        boolean filtred = false;
        /*
         * DataModel dataModel = listContext.getDataModel();
         * 
         * IFilterProperties filtersMap = listContext.getFiltersMap(); if
         * (filtersMap != null) { if (dataModel instanceof IFiltredDataModel) {
         * IFiltredDataModel filtredDataModel = (IFiltredDataModel) dataModel;
         * 
         * filtredDataModel.setFilter(filtersMap); listContext.updateRowCount();
         * 
         * filtred = true; } else { dataModel =
         * FiltredDataModel.filter(dataModel, filtersMap);
         * listContext.updateRowCount(); } } else if (dataModel instanceof
         * IFiltredDataModel) { IFiltredDataModel filtredDataModel =
         * (IFiltredDataModel) dataModel;
         * 
         * filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
         * listContext.updateRowCount();
         * 
         * filtred = true; }
         */

        UIColumn columns[] = listContext.listColumns();

        // Debut
        int rowIndex = listContext.getFirst();

        // Nombre de ligne a lire !
        int rows = listContext.getRows();

        int columnNumber = listContext.getColumnNumber();

        if (columnNumber > 1) {
            rows = rows * columnNumber;
        }

        String rcls = componentsListComponent.getRowStyleClass(facesContext);
        String rowClasses[] = parseClasses(rcls);

        // String ccls =
        // componentsListComponent.getColumnStyleClass(facesContext);
        // String columnClasses[] = parseClasses(ccls);

        String tdClass = getStyleClassName(componentRenderContext,
                componentsListComponent)
                + "_cell";

        int count = listContext.getRowCount();

        Map varContext = facesContext.getExternalContext().getRequestMap();
        String rowCountVar = listContext.getRowCountVar();
        if (rowCountVar != null) {
            varContext.put(rowCountVar, new Integer(count));
        }

        String rowIndexVar = listContext.getRowIndexVar();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) htmlWriter
                .getComponentRenderContext().getRenderContext();

        try {
            int processed = 0;

            for (; rows <= 0 || processed < rows; processed++, rowIndex++) {

                componentsListComponent.setRowIndex(rowIndex);
                if (componentsListComponent.isRowAvailable() == false) {
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(processed));
                }

                if ((processed % columnNumber) == 0) {
                    // Render the beginning of this row
                    htmlWriter.startElement("TR");

                    String rowId = htmlRenderContext.getComponentId(
                            facesContext, componentsListComponent);
                    if (rowId != null) {
                        htmlWriter.writeAttribute("id", rowId);
                    }

                    htmlWriter.writeAttribute("v:nc", "true");

                    if (rowClasses.length > 0) {
                        int rs = (processed / columnNumber) % rowClasses.length;

                        htmlWriter.writeAttribute("class", rowClasses[rs]);
                    }
                }

                htmlWriter.startElement("TD");
                htmlWriter.writeAttribute("class", tdClass);

                htmlWriter.writeln();

                /*
                 * if (columnClasses.length > 0) { int rs = (processed %
                 * columnNumber) % columnClasses.length;
                 * 
                 * htmlWriter.writeAttribute("class", columnClasses[rs]); }
                 */

                htmlWriter.flush();

                UIColumn column = columns[processed % columns.length];

                ComponentTools.encodeChildrenRecursive(facesContext, column);

                htmlWriter.writeln();

                htmlWriter.endElement("TD");

                if (((processed + 1) % columnNumber) == 0) {
                    htmlWriter.endElement("TR");
                }
            }

            if ((processed % columnNumber) > 0) {
                for (; (processed % columnNumber) > 0; processed++) {
                    htmlWriter.startElement("TD");
                    htmlWriter.endElement("TD");
                }

                htmlWriter.endElement("TR");
            }

        } finally {
            componentsListComponent.setRowIndex(-1);
        }
    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        ComponentsListComponent componentsListComponent = (ComponentsListComponent) writer
                .getComponentRenderContext().getComponent();

        componentsListComponent.setRowIndex(-1);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("TBODY");

        htmlWriter.endElement("TABLE");

        htmlWriter.endElement("DIV");

        super.encodeEnd(htmlWriter);
    }

    private String[] parseClasses(String classes) {

        if (classes == null || classes.length() < 1) {
            return STRING_EMPTY_ARRAY;
        }

        List l = null;
        StringTokenizer st = new StringTokenizer(classes, ",");
        for (; st.hasMoreTokens();) {
            String cls = st.nextToken();

            if (l == null) {
                l = new ArrayList(4);
            }

            l.add(cls);
        }

        if (l == null) {
            return STRING_EMPTY_ARRAY;
        }

        return (String[]) l.toArray(new String[l.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMPONENTS_LIST;
    }

    protected void writeCustomCss(IHtmlWriter componentWriter,
            ICssWriter cssWriter) {
        super.writeCustomCss(componentWriter, cssWriter);

        IComponentRenderContext componentRenderContext = componentWriter
                .getComponentRenderContext();
        ComponentsListComponent componentsListComponent = (ComponentsListComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (componentsListComponent.isBorder(facesContext) == false) {
            cssWriter.writeProperty("border-style", "none");
        }

        String w = componentsListComponent.getWidth(facesContext);
        String h = componentsListComponent.getHeight(facesContext);
        if (w != null || h != null) {
            cssWriter.writeSize(componentsListComponent);
            if (h != null) {
                cssWriter.writeProperty("overflow", "auto");
            }
        }

        cssWriter.writeMargin(componentsListComponent);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        // FacesContext facesContext = context.getFacesContext();

        ComponentsListComponent dg = (ComponentsListComponent) component;

        Number first = componentData.getNumberProperty("first");
        if (first != null) {
            int old = dg.getFirst();

            dg.setFirst(first.intValue());

            component.queueEvent(new PropertyChangeEvent(component,
                    Properties.FIRST, new Integer(old), first));
        }

        /*
         * String filterExpression =
         * componentData.getStringProperty("filterExpression"); if
         * (filterExpression != null) { if (filterExpression.length() < 1) {
         * filterExpression = null; }
         * 
         * dg.setFilterProperties(HtmlTools
         * .decodeFilterExpression(filterExpression)); }
         */

        /*
         * IComponentPreference preference = dg.getPreference(facesContext); if
         * (preference != null) { preference.savePreference(facesContext, dg); }
         */
    }

    public static class ListContext {

        private final FacesContext facesContext;

        private final ComponentsListComponent componentsListComponent;

        private final String rowIndexVar;

        private final String rowCountVar;

        // private DataModel dataModel;

        private UIColumn columns[];

        private int columnNumber;

        private int first;

        private int rowCount;

        private int rows = -2;

        // private IFilterProperties filtersMap;

        private boolean interactiveShow;

        ListContext(FacesContext facesContext,
                ComponentsListComponent componentsListComponent) {
            this(facesContext, componentsListComponent, false);

            first = componentsListComponent.getFirst();

            // filtersMap =
            // componentsListComponent.getFilterProperties(facesContext);
        }

        public String getRowIndexVar() {
            return rowIndexVar;
        }

        public String getRowCountVar() {
            return rowCountVar;
        }

        ListContext(FacesContext facesContext,
                ComponentsListComponent componentsListComponent, int rowIndex,
                String filterExpression) {
            this(facesContext, componentsListComponent, true);

            first = rowIndex;
            // this.filtersMap =
            // HtmlTools.decodeFilterExpression(filterExpression);
        }

        private ListContext(FacesContext facesContext,
                ComponentsListComponent componentsListComponent, boolean dummy) {
            this.facesContext = facesContext;
            this.componentsListComponent = componentsListComponent;

            columnNumber = componentsListComponent
                    .getColumnNumber(facesContext);

            rowIndexVar = componentsListComponent.getRowIndexVar(facesContext);

            rowCountVar = componentsListComponent.getRowCountVar(facesContext);

            List children = componentsListComponent.getChildren();
            List cols = null;
            for (Iterator it = children.iterator(); it.hasNext();) {
                UIComponent child = (UIComponent) it.next();

                if ((child instanceof UIColumn) == false) {
                    continue;
                }

                if (cols == null) {
                    cols = new ArrayList(children.size());
                }

                cols.add(child);
            }

            if (cols != null) {
                columns = (UIColumn[]) cols.toArray(new UIColumn[cols.size()]);

            } else {
                UIColumn column = new UIColumn();

                column.getChildren().addAll(children);

                children.clear();
                children.add(column);

                columns = new UIColumn[] { column };
            }

            if (columnNumber < 1) {
                columnNumber = columns.length;
            }

            rows = componentsListComponent.getRows();
            rowCount = componentsListComponent.getRowCount();

            /*
             * Object value = componentsListComponent.getCachedValue(); if
             * (value instanceof DataModel) { dataModel = (DataModel) value; }
             */
        }

        public UIColumn[] listColumns() {
            return columns;
        }

        public void updateRowCount() {
            rowCount = -2;
        }

        public int getRowCount() {
            if (rowCount == -2) {
                rowCount = componentsListComponent.getRowCount();
            }

            return rowCount;
        }

        public void setInteractiveShow(boolean interactiveShow) {
            this.interactiveShow = interactiveShow;
        }

        public boolean isInteractiveShow() {
            return interactiveShow;
        }

        /*
         * public DataModel getDataModel() { return dataModel; }
         */

        public int getColumnNumber() {
            return columnNumber;
        }

        public int getFirst() {
            return first;
        }

        public int getRows() {
            return rows;
        }

        /*
         * public IFilterProperties getFiltersMap() { return filtersMap; }
         */

    }

    public ListContext createListContext(FacesContext facesContext,
            ComponentsListComponent dgc, int rowIndex,
            ISortedComponent[] sortedComponents, String filterExpression) {
        return new ListContext(facesContext, dgc, rowIndex, filterExpression);
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        ComponentsListComponent componentsListComponent = (ComponentsListComponent) writer
                .getComponentRenderContext().getComponent();

        IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                writer).getJavaScriptRenderContext();

        IMenuIterator menuIterator = componentsListComponent.listMenus();
        if (menuIterator.hasNext()) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.COMPONENTS_LIST, "menu");
        }

        if (componentsListComponent.getRows() > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.COMPONENTS_LIST, "ajax");
        }
    }
}
