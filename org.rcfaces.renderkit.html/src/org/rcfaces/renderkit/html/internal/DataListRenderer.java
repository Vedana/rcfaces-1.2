/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.13  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.12  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.11  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.10  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.9  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.8  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.7  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.6  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.5  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.4  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.3  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
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
import org.rcfaces.core.component.DataListComponent;
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
import org.rcfaces.renderkit.html.internal.service.DataListService;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataListRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(DataListRenderer.class);

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    private static final boolean ENABLE_SERVER_REQUEST = true;

    private static final String LIST_CONTEXT = "dataList.listContext";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        DataListComponent dataListComponent = (DataListComponent) componentRenderContext
                .getComponent();

        dataListComponent.setRowIndex(-1);

        ListContext listContext = new ListContext(facesContext,
                dataListComponent);
        componentRenderContext.setAttribute(LIST_CONTEXT, listContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV", dataListComponent);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (ENABLE_SERVER_REQUEST) {
            DataListService dataListServer = DataListService
                    .getInstance(facesContext);
            if (dataListServer != null) {
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

        String w = dataListComponent.getWidth(facesContext);
        if (w != null) {
            htmlWriter.writeAttribute("width", "100%");
        }

        String ccls = dataListComponent.getColumnStyleClass(facesContext);
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
                    continue;
                }
                int rs = i % columnClasses.length;

                htmlWriter.writeAttribute("class", columnClasses[rs]);
            }
        }

        htmlWriter.startElement("TBODY");
        String className = getStyleClassName(componentRenderContext,
                dataListComponent);
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
        DataListService dataListServer = DataListService
                .getInstance(facesContext);
        dataListServer.setupComponent(componentRenderContext);

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

        DataListComponent dataListComponent = (DataListComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        boolean filtered = false;
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

        String rcls = dataListComponent.getRowStyleClass(facesContext);
        String rowClasses[] = parseClasses(rcls);

        // String ccls =
        // dataListComponent.getColumnStyleClass(facesContext);
        // String columnClasses[] = parseClasses(ccls);

        String tdClass = getStyleClassName(componentRenderContext,
                dataListComponent)
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

                dataListComponent.setRowIndex(rowIndex);
                if (dataListComponent.isRowAvailable() == false) {
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(processed));
                }

                if ((processed % columnNumber) == 0) {
                    // Render the beginning of this row
                    htmlWriter.startElement("TR");

                    String rowId = htmlRenderContext.getComponentId(
                            facesContext, dataListComponent);
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
            dataListComponent.setRowIndex(-1);
        }
    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        DataListComponent dataListComponent = (DataListComponent) writer
                .getComponentRenderContext().getComponent();

        dataListComponent.setRowIndex(-1);

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
        return JavaScriptClasses.DATA_LIST;
    }

    protected void writeCustomCss(IHtmlWriter componentWriter,
            ICssWriter cssWriter) {
        super.writeCustomCss(componentWriter, cssWriter);

        IComponentRenderContext componentRenderContext = componentWriter
                .getComponentRenderContext();
        DataListComponent dataListComponent = (DataListComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (dataListComponent.isBorder(facesContext) == false) {
            cssWriter.writeProperty("border-style", "none");
        }

        String w = dataListComponent.getWidth(facesContext);
        String h = dataListComponent.getHeight(facesContext);
        if (w != null || h != null) {
            cssWriter.writeSize(dataListComponent);
            if (h != null) {
                cssWriter.writeProperty("overflow", "scroll");
            }
        }

        cssWriter.writeMargin(dataListComponent);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        // FacesContext facesContext = context.getFacesContext();

        DataListComponent dg = (DataListComponent) component;

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

        private final DataListComponent dataListComponent;

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
                DataListComponent dataListComponent) {
            this(facesContext, dataListComponent, false);

            first = dataListComponent.getFirst();

            // filtersMap = dataListComponent.getFilterProperties(facesContext);
        }

        public String getRowIndexVar() {
            return rowIndexVar;
        }

        public String getRowCountVar() {
            return rowCountVar;
        }

        ListContext(FacesContext facesContext,
                DataListComponent dataListComponent, int rowIndex,
                String filterExpression) {
            this(facesContext, dataListComponent, true);

            first = rowIndex;
            // this.filtersMap =
            // HtmlTools.decodeFilterExpression(filterExpression);
        }

        private ListContext(FacesContext facesContext,
                DataListComponent dataListComponent, boolean dummy) {
            this.facesContext = facesContext;
            this.dataListComponent = dataListComponent;

            columnNumber = dataListComponent.getColumnNumber(facesContext);

            rowIndexVar = dataListComponent.getRowIndexVar(facesContext);

            rowCountVar = dataListComponent.getRowCountVar(facesContext);

            List children = dataListComponent.getChildren();
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

            rows = dataListComponent.getRows();
            rowCount = dataListComponent.getRowCount();

            /*
             * Object value = dataListComponent.getCachedValue(); if (value
             * instanceof DataModel) { dataModel = (DataModel) value; }
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
                rowCount = dataListComponent.getRowCount();
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
            DataListComponent dgc, int rowIndex,
            ISortedComponent[] sortedComponents, String filterExpression) {
        return new ListContext(facesContext, dgc, rowIndex, filterExpression);
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        DataListComponent dataListComponent = (DataListComponent) writer
                .getComponentRenderContext().getComponent();

        IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                writer).getJavaScriptRenderContext();

        IMenuIterator menuIterator = dataListComponent.listMenus();
        if (menuIterator.hasNext()) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.DATA_LIST, "menu");
        }

        if (dataListComponent.getRows() > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.DATA_LIST, "ajax");
        }
    }
}
