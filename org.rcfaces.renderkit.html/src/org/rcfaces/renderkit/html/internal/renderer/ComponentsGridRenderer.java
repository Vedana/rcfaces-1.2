/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.service.ComponentsListService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentsGridRenderer extends AbstractGridRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ComponentsGridRenderer.class);

    private static final String DEFAULT_ROW_CLASSNAMES[] = { "f_grid_row_even",
            "f_grid_row_odd" };

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMPONENTS_GRID;
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        return new ComponentsGridRenderContext(componentRenderContext);
    }

    protected void writeGridComponentAttributes(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, IGridComponent dg)
            throws WriterException {
        super.writeGridComponentAttributes(htmlWriter, tableContext, dg);

        if (ENABLE_SERVER_REQUEST) {
            ComponentsListService componentsListServer = ComponentsListService
                    .getInstance(htmlWriter.getComponentRenderContext()
                            .getFacesContext());
            if (componentsListServer != null) {
                htmlWriter.writeAttribute("v:asyncRender", "true");
            }

            /* Si le tableau n'est pas visible ! */

            String interactiveComponentClientId = htmlWriter
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getCurrentInteractiveRenderComponentClientId();

            if (interactiveComponentClientId != null) {
                // Pas de donn�es si nous sommes dans un scope interactif !
                htmlWriter.writeAttribute("v:interactiveShow",
                        interactiveComponentClientId);

                ((ComponentsGridRenderContext) tableContext)
                        .setInteractiveShow(true);
            }
        }
    }

    protected void encodeBodyEnd(IHtmlWriter writer,
            AbstractGridRenderContext tableContext) throws WriterException {

        encoreBodyTableEnd(writer, tableContext);

        writer.enableJavaScript();

        super.encodeBodyEnd(writer, tableContext);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter();

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        ComponentsGridRenderContext tableContext = (ComponentsGridRenderContext) getGridRenderContext(htmlWriter);

        // Dans tous les cas il faut positionner le renderContext !
        ComponentsListService componentsListServer = ComponentsListService
                .getInstance(facesContext);
        if (componentsListServer != null) {
            componentsListServer.setupComponent(componentRenderContext);
        }

        if (tableContext.isInteractiveShow()) {
            return;
        }

        encodeChildren(htmlWriter, tableContext);
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, true);
    }

    public void encodeChildren(IComponentWriter writer,
            ComponentsGridRenderContext gridRenderContext)
            throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        ComponentsGridComponent componentsGridComponent = (ComponentsGridComponent) componentRenderContext
                .getComponent();

        boolean hasCellStyleClass[] = gridRenderContext.getCellStyleClass();
        String defaultCellStyleClasses[][] = gridRenderContext
                .getDefaultCellStyleClasses();

        boolean hasCellToolTipText[] = gridRenderContext.getCellToolTipText();
        String defaultCellToolTipTexts[] = gridRenderContext
                .getDefaultCellToolTipTexts();

        String cellHorizontalAligments[] = gridRenderContext
                .getDefaultCellHorizontalAlignments();

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

        UIColumn columns[] = gridRenderContext.listColumns();
        int columnNumber = columns.length;

        // Debut
        int rowIndex = gridRenderContext.getFirst();

        // Nombre de ligne a lire !
        int rows = gridRenderContext.getRows();

        // String ccls =
        // componentsListComponent.getColumnStyleClass(facesContext);
        // String columnClasses[] = parseClasses(ccls);

        int count = gridRenderContext.getRowCount();

        Map varContext = facesContext.getExternalContext().getRequestMap();
        String rowCountVar = gridRenderContext.getRowCountVar();
        if (rowCountVar != null) {
            varContext.put(rowCountVar, new Integer(count));
        }

        String rowIndexVar = gridRenderContext.getRowIndexVar();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) htmlWriter
                .getComponentRenderContext().getRenderContext();

        String rowStyleClasses[] = gridRenderContext.getRowStyleClasses();
        if (rowStyleClasses == null) {
            rowStyleClasses = DEFAULT_ROW_CLASSNAMES;
        }

        try {
            int processed = 0;

            for (; rows <= 0 || processed < rows; processed++, rowIndex++) {

                componentsGridComponent.setRowIndex(rowIndex);
                if (componentsGridComponent.isRowAvailable() == false) {
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(processed));
                }

                htmlWriter.startElement(IHtmlWriter.TR);

                htmlWriter.writeAttribute("v:index", rowIndex);

                String trClassName = rowStyleClasses[rowIndex
                        % rowStyleClasses.length];

                if (trClassName != null) {
                    htmlWriter.writeClass(trClassName);
                }

                String rowId = htmlRenderContext
                        .getComponentClientId(componentsGridComponent);
                if (rowId != null) {
                    htmlWriter.writeId(rowId);
                }

                htmlWriter.writeAttribute("v:nc", "true");

                /*
                 * if (rowClasses.length > 0) { int rs = (processed /
                 * columnNumber) % rowClasses.length;
                 * 
                 * htmlWriter.writeClass(rowClasses[rs]); }
                 */

                String defaultCellStyleClass = getComponentStyleClassName()
                        + "_cell2";
                for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {

                    UIColumn column = columns[columnIndex];

                    htmlWriter.startElement(IHtmlWriter.TD);

                    htmlWriter.writeAttribute("noWrap");

                    String toolTip = null;
                    if (defaultCellToolTipTexts != null) {
                        toolTip = defaultCellToolTipTexts[columnIndex];
                    }
                    if (hasCellToolTipText[columnIndex]) {
                        String cellToolTip = ((ICellToolTipTextCapability) column)
                                .getCellToolTipText();
                        if (cellToolTip != null) {
                            toolTip = cellToolTip;
                        }
                    }
                    if (toolTip != null) {
                        htmlWriter.writeTitle(toolTip);
                    }

                    String styleClass = null;
                    if (defaultCellStyleClasses != null) {
                        String csc[] = defaultCellStyleClasses[columnIndex];
                        if (csc != null && csc.length > 0) {
                            styleClass = csc[processed % csc.length];
                        }
                    }
                    if (hasCellStyleClass[columnIndex]) {
                        String csc = ((ICellStyleClassCapability) column)
                                .getCellStyleClass();
                        if (csc != null) {
                            styleClass = csc;
                        }
                    }

                    if (styleClass != null) {
                        styleClass = defaultCellStyleClass + " " + styleClass;

                    } else {
                        styleClass = defaultCellStyleClass;
                    }

                    htmlWriter.writeClass(styleClass);

                    htmlWriter.writeln();

                    /*
                     * if (columnClasses.length > 0) { int rs = (processed %
                     * columnNumber) % columnClasses.length;
                     * 
                     * htmlWriter.writeAttribute("class", columnClasses[rs]); }
                     */

                    htmlWriter.endComponent();

                    ComponentTools
                            .encodeChildrenRecursive(facesContext, column);

                    htmlWriter.writeln();

                    htmlWriter.endElement(IHtmlWriter.TD);
                }

                htmlWriter.endElement(IHtmlWriter.TR);
            }

        } finally {
            componentsGridComponent.setRowIndex(-1);
        }
    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        ComponentsGridComponent componentsListComponent = (ComponentsGridComponent) writer
                .getComponentRenderContext().getComponent();

        componentsListComponent.setRowIndex(-1);

        super.encodeEnd(writer);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class ComponentsGridRenderContext extends AbstractGridRenderContext {

        private static final String REVISION = "$Revision$";

        private boolean rowValueSetted;

        private boolean interactiveShow;

        public ComponentsGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext,
                ComponentsGridComponent gridComponent, int rowIndex,
                int forcedRows, ISortedComponent[] sortedComponents,
                String filterExpression) {
            super(processContext, scriptRenderContext, gridComponent, rowIndex,
                    forcedRows, sortedComponents, filterExpression);
        }

        public void setInteractiveShow(boolean interactiveShow) {
            this.interactiveShow = interactiveShow;
        }

        public boolean isInteractiveShow() {
            return interactiveShow;
        }

        public ComponentsGridRenderContext(
                IHtmlComponentRenderContext htmlComponentRenderContext) {
            super(htmlComponentRenderContext);
        }

        protected void initialize(boolean checkTitleImages) {
            super.initialize(checkTitleImages);

            this.rowValueSetted = getComponentsGridComponent()
                    .isRowValueSetted();
        }

        public ComponentsGridComponent getComponentsGridComponent() {
            return (ComponentsGridComponent) getGridComponent();
        }

        protected String convertAliasCommand(String command) {
            return null;
        }

        public boolean isRowValueSetted() {
            return rowValueSetted;
        }
    }
}
