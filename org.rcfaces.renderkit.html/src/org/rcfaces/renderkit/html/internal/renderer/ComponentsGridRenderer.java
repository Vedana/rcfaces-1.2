/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.GridServerSort;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.Convertor;
import org.rcfaces.core.lang.provider.ISelectionProvider;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.service.ComponentsGridService;
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
            ComponentsGridService componentsGridServer = ComponentsGridService
                    .getInstance(htmlWriter.getComponentRenderContext()
                            .getFacesContext());
            if (componentsGridServer != null) {
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

    public ComponentsGridRenderContext createComponentsGridContext(
            IProcessContext processContext,
            IScriptRenderContext scriptRenderContext,
            ComponentsGridComponent dgc, int rowIndex, int forcedRow,
            ISortedComponent[] sortedComponents, String filterExpression) {
        return new ComponentsGridRenderContext(processContext,
                scriptRenderContext, dgc, rowIndex, forcedRow,
                sortedComponents, filterExpression);
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

        encodeChildren(htmlWriter, tableContext, false);
    }

    protected void encodeJsColumns(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(jsWriter, gridRenderContext, GENERATE_CELL_STYLE_CLASS);

        if (gridRenderContext.isSelectable()
                && gridRenderContext.isClientSelectionFullState()) {

            ISelectionProvider selectionProvider = (ISelectionProvider) gridRenderContext
                    .getGridComponent();

            Object selectionModel = selectionProvider.getSelectedValues();

            if (selectionModel != null) {
                if (selectionModel instanceof IIndexesModel) {
                    int selectedIndexes[] = ((IIndexesModel) selectionModel)
                            .listSortedIndexes();

                    writeFullStates(jsWriter, "f_setSelectionStates",
                            selectedIndexes);
                } else {
                    Set selectedObjects = ValuesTools
                            .convertSelection(selectionModel);

                    writeFullStates(jsWriter, gridRenderContext,
                            "f_setSelectionStates", selectedObjects);
                }
            }
        }
    }

    protected void writeFullStates(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext context, String jsCommand, Set objects)
            throws WriterException {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        ComponentsGridRenderContext componentsGridRenderContext = (ComponentsGridRenderContext) context;

        ComponentsGridComponent componentsGridComponent = componentsGridRenderContext
                .getComponentsGridComponent();

        FacesContext facesContext = jsWriter.getFacesContext();

        jsWriter.writeMethodCall(jsCommand).write('[');
        int i = 0;
        for (Iterator it = objects.iterator(); it.hasNext();) {
            Object value = it.next();

            if (value != null) {
                Converter converter = componentsGridRenderContext
                        .getRowValueConverter();

                if (converter != null) {
                    value = converter.getAsString(facesContext,
                            componentsGridComponent, value);
                }
            }

            if (value == null) {
                continue;
            }

            if ((value instanceof String) == false) {
                value = Convertor.convert(value, String.class);
            }

            if (i > 0) {
                jsWriter.write(',');
            }

            jsWriter.writeString((String) value);

            i++;
        }

        jsWriter.writeln("]);");
    }

    public int encodeChildren(IComponentWriter writer,
            ComponentsGridRenderContext gridRenderContext, boolean encodeJs)
            throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        ComponentsGridComponent componentsGridComponent = (ComponentsGridComponent) componentRenderContext
                .getComponent();

        // Debut
        int rowIndex = gridRenderContext.getFirst();

        // Nombre de ligne a lire !
        int rows = gridRenderContext.getRows();

        int firstCount = gridRenderContext.getRowCount();

        boolean searchEnd = (rows > 0);
        // int firstCount = -1;
        int count = -1;

        if (searchEnd) {
            count = firstCount;
        }

        int sortTranslations[] = null;

        ISortedComponent sortedComponents[] = gridRenderContext
                .listSortedComponents();

        DataModel dataModel = componentsGridComponent.getDataModelValue();

        if (sortedComponents != null && sortedComponents.length > 0) {
            if (dataModel instanceof ISortedDataModel) {
                // On delegue au modele, le tri !

                // Nous devons �tre OBLIGATOIREMENT en mode rowValueColumnId
                if (gridRenderContext.isRowValueSetted()) {
                    throw new FacesException(
                            "Can not sort dataModel without attribute rowValue attribute specified !");
                }

                ((ISortedDataModel) dataModel).setSortParameters(
                        componentsGridComponent, sortedComponents);
            } else {
                // Il faut faire le tri à la main !

                sortTranslations = GridServerSort.computeSortedTranslation(
                        facesContext, componentsGridComponent, dataModel,
                        sortedComponents);
            }

            // Apres le tri, on connait peu etre la taille
            gridRenderContext.updateRowCount();

        } else if (dataModel instanceof ISortedDataModel) {
            // Reset des parametres de tri !
            ((ISortedDataModel) dataModel).setSortParameters(
                    componentsGridComponent, null);
        }

        int selectedIndexes[] = null;
        int selectedOffset = -1;
        Set selectedObjects = null;

        if (gridRenderContext.isSelectable()
                && gridRenderContext.isClientSelectionFullState() == false) {

            Object selectionModel = componentsGridComponent
                    .getSelectedValues(facesContext);

            if (selectionModel != null) {
                if (selectionModel instanceof IIndexesModel) {
                    selectedIndexes = ((IIndexesModel) selectionModel)
                            .listSortedIndexes();

                    if (sortTranslations == null) {
                        // Dans le cas ou il n'y a pas de tri
                        // Les indexes sont lineaires ...

                        if (selectedIndexes != null
                                && selectedIndexes.length > 0) {
                            // Recherche du premier RowOffset
                            for (int i = 0; i < selectedIndexes.length; i++) {
                                if (selectedIndexes[i] >= rowIndex) {
                                    selectedOffset = i;
                                    break;
                                }
                            }
                        }
                    }

                } else {
                    selectedObjects = ValuesTools
                            .convertSelection(selectionModel);
                }
            }
        }

        Map varContext = facesContext.getExternalContext().getRequestMap();
        String rowCountVar = gridRenderContext.getRowCountVar();
        if (rowCountVar != null) {
            varContext.put(rowCountVar, new Integer(count));
        }

        String rowIndexVar = gridRenderContext.getRowIndexVar();

        String rowStyleClasses[] = gridRenderContext.getRowStyleClasses();
        if (rowStyleClasses == null) {
            rowStyleClasses = DEFAULT_ROW_CLASSNAMES;
        }

        boolean writeSelected = true;
        if (componentsGridComponent.isClientSelectionFullState(facesContext)) {
            writeSelected = false;
        }

        try {
            int i = 0;
            boolean selected = false;

            for (; rows <= 0 || i < rows; i++, rowIndex++) {
                if (searchEnd == false) {
                    // Pas de recherche de la fin !
                    // On peut sortir tout de suite ...
                    if (rows > 0 && i >= rows) {
                        break;
                    }
                }

                int translatedRowIndex = rowIndex;

                if (gridRenderContext.isRowValueSetted()) {
                    if (sortTranslations != null) {
                        if (rowIndex >= sortTranslations.length) {
                            break;
                        }

                        translatedRowIndex = sortTranslations[rowIndex];
                    }

                } else {

                    if (sortTranslations == null) {
                        if (selectedOffset >= 0) {
                            if (selectedIndexes[selectedOffset] == rowIndex) {
                                selected = true;

                                selectedOffset++;

                            } else {
                                selected = false;
                            }
                        }
                    } else {
                        if (rowIndex >= sortTranslations.length) {
                            break;
                        }

                        translatedRowIndex = sortTranslations[rowIndex];

                        if (selectedIndexes != null) {
                            selected = false;

                            for (int j = 0; j < selectedIndexes.length; j++) {
                                if (selectedIndexes[j] != translatedRowIndex) {
                                    continue;
                                }

                                selected = true;
                                break;
                            }
                        }
                    }
                }

                componentsGridComponent.setRowIndex(translatedRowIndex);
                if (componentsGridComponent.isRowAvailable() == false) {
                    count = rowIndex;
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(i));
                }

                String rowValue = null;

                if (gridRenderContext.isRowValueSetted()) {
                    Object value = componentsGridComponent
                            .getRowValue(facesContext);

                    if (value != null) {
                        Converter converter = gridRenderContext
                                .getRowValueConverter();

                        if (converter != null) {
                            value = converter.getAsString(facesContext,
                                    componentsGridComponent, value);
                        }

                        if (value instanceof String) {
                            rowValue = (String) value;
                        }
                    }

                    if (value != null) {
                        if (selectedObjects != null) {
                            selected = selectedObjects.contains(value);
                        }
                    }
                }

                if (rowValue == null) {
                    rowValue = String.valueOf(rowIndex);
                }

                StringAppender sa = new StringAppender("f_cGrid_row", 32);

                String trClassName = rowStyleClasses[rowIndex
                        % rowStyleClasses.length];
                if (trClassName != null) {
                    sa.append(' ').append(trClassName);
                }

                String suffix = "";
                if (gridRenderContext.isDisabled()) {
                    suffix += "_disabled";
                }
                if (selected) {
                    suffix += "_selected";
                }

                if (suffix.length() > 0) {
                    sa.append(" f_grid_row").append(suffix);
                    if (trClassName != null) {
                        sa.append(' ').append(trClassName).append(suffix);
                    }
                }

                String defaultCellStyleClass = "f_cGrid_cell";

                if (encodeJs) {
                    encodeRowJs((IJavaScriptWriter) writer, gridRenderContext,
                            rowValue, sa.toString(), selected, writeSelected,
                            defaultCellStyleClass, i);

                } else {
                    encodeRow((IHtmlWriter) writer, gridRenderContext,
                            rowValue, sa.toString(), selected, writeSelected,
                            defaultCellStyleClass, i);
                }

                if (sortTranslations == null) {
                    if (selectedOffset >= 0
                            && selectedOffset >= selectedIndexes.length) {
                        selectedOffset = -1;
                        selected = false;
                    }
                }
            }

            return i;

        } finally {
            componentsGridComponent.setRowIndex(-1);
        }
    }

    private void encodeRow(IHtmlWriter htmlWriter,
            ComponentsGridRenderContext gridRenderContext, String rowValue,
            String rowClassName, boolean selected, boolean writeSelected,
            String defaultCellStyleClass, int rowIndex) throws WriterException {

        htmlWriter.startElement(IHtmlWriter.TR);

        htmlWriter.writeAttribute("v:index", rowValue);

        htmlWriter.writeClass(rowClassName);

        String rowId = htmlWriter.getHtmlComponentRenderContext()
                .getHtmlRenderContext().getComponentClientId(
                        htmlWriter.getComponentRenderContext().getComponent());
        if (rowId != null) {
            htmlWriter.writeId(rowId);
        }

        htmlWriter.writeAttribute("v:nc", "true");

        if (selected && writeSelected) {
            htmlWriter.writeAttribute("v:selected", "true");
        }

        UIColumn columns[] = gridRenderContext.listColumns();

        boolean hasCellStyleClass[] = gridRenderContext.getCellStyleClass();
        String defaultCellStyleClasses[][] = gridRenderContext
                .getDefaultCellStyleClasses();

        boolean hasCellToolTipText[] = gridRenderContext.getCellToolTipText();
        String defaultCellToolTipTexts[] = gridRenderContext
                .getDefaultCellToolTipTexts();

        String cellHorizontalAligments[] = gridRenderContext
                .getDefaultCellHorizontalAlignments();

        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {

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
            if (hasCellStyleClass[columnIndex]) {
                String csc = ((ICellStyleClassCapability) column)
                        .getCellStyleClass();
                if (csc != null) {
                    styleClass = csc;

                    htmlWriter.writeAttribute("v:class", csc);
                }
            }

            if (styleClass == null && defaultCellStyleClasses != null) {
                String csc[] = defaultCellStyleClasses[columnIndex];
                if (csc != null && csc.length > 0) {
                    styleClass = csc[rowIndex % csc.length];
                }
            }

            StringAppender sa = new StringAppender(defaultCellStyleClass, 32);
            if (selected) {
                sa.append(' ').append("f_grid_cell_selected");
            }
            if (styleClass != null) {
                sa.append(' ').append(styleClass);
            }

            htmlWriter.writeClass(sa.toString());

            if (cellHorizontalAligments != null) {
                String alignment = cellHorizontalAligments[columnIndex];
                if (alignment != null) {
                    htmlWriter.writeAlign(alignment);
                }
            }
            htmlWriter.endComponent();

            htmlWriter.writeln();

            ComponentTools.encodeChildrenRecursive(htmlWriter
                    .getComponentRenderContext().getFacesContext(), column);

            htmlWriter.writeln();

            htmlWriter.endElement(IHtmlWriter.TD);
        }

        htmlWriter.endElement(IHtmlWriter.TR);
    }

    private void encodeRowJs(IJavaScriptWriter jsWriter,
            ComponentsGridRenderContext gridRenderContext, String rowValue,
            String rowClassName, boolean selected, boolean writeSelected,
            String defaultCellStyleClass, int rowIndex) throws WriterException {

        String rowId = jsWriter.getHtmlRenderContext().getComponentClientId(
                gridRenderContext.getComponentsGridComponent());

        jsWriter.writeMethodCall("f_addRow2").writeString(rowId);

        IObjectLiteralWriter oWriter = jsWriter.write(',').writeObjectLiteral(
                true);

        if (rowClassName != null) {
            oWriter.writeSymbol("_styleClass").writeString(rowClassName);
        }
        if (rowValue != null) {
            oWriter.writeSymbol("_value").writeString(rowValue);
        }

        if (selected && writeSelected) {
            oWriter.writeSymbol("_selected").writeBoolean(true);
        }

        oWriter.end();

        UIColumn columns[] = gridRenderContext.listColumns();

        boolean hasCellStyleClass[] = gridRenderContext.getCellStyleClass();

        boolean hasCellToolTipText[] = gridRenderContext.getCellToolTipText();
        String defaultCellToolTipTexts[] = gridRenderContext
                .getDefaultCellToolTipTexts();

        String cellHorizontalAligments[] = gridRenderContext
                .getDefaultCellHorizontalAlignments();

        CharArrayWriter bufWriter = new CharArrayWriter(16000);

        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {

            int rowState = gridRenderContext.getColumnState(columnIndex);
            if (rowState == AbstractGridRenderContext.SERVER_HIDDEN) {
                continue;
            }

            UIColumn column = columns[columnIndex];

            oWriter = jsWriter.write(',').writeObjectLiteral(true);

            if (rowState == AbstractGridRenderContext.VISIBLE) {
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
                    oWriter.writeSymbol("_toolTipText").writeString(toolTip);
                }

                if (hasCellStyleClass[columnIndex]) {
                    String csc = ((ICellStyleClassCapability) column)
                            .getCellStyleClass();
                    if (csc != null) {
                        oWriter.writeSymbol("_styleClass").writeString(csc);
                    }
                }

                if (cellHorizontalAligments != null) {
                    String alignment = cellHorizontalAligments[columnIndex];
                    if (alignment != null) {
                        oWriter.writeSymbol("_align").writeString(alignment);
                    }
                }
            }

            oWriter.end();

            if (rowState != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            FacesContext facesContext = jsWriter.getFacesContext();

            ResponseWriter oldResponseWriter = facesContext.getResponseWriter();
            try {
                bufWriter.reset();

                ResponseWriter newResponseWriter = oldResponseWriter
                        .cloneWithWriter(bufWriter);

                facesContext.setResponseWriter(newResponseWriter);

                ComponentTools.encodeChildrenRecursive(facesContext, column);

                try {
                    newResponseWriter.flush();

                } catch (IOException e) {
                    LOG.error(e);
                }
            } finally {
                facesContext.setResponseWriter(oldResponseWriter);
            }

            jsWriter.write(',').writeString(bufWriter.toString());
        }
        bufWriter.reset();

        jsWriter.writeln(");");
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

        private Converter rowValueConverter;

        public ComponentsGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext,
                ComponentsGridComponent gridComponent, int rowIndex,
                int forcedRows, ISortedComponent[] sortedComponents,
                String filterExpression) {
            super(processContext, scriptRenderContext, gridComponent, rowIndex,
                    forcedRows, sortedComponents, filterExpression);
        }

        public ComponentsGridRenderContext(
                IHtmlComponentRenderContext htmlComponentRenderContext) {
            super(htmlComponentRenderContext);
        }

        protected void initialize(boolean checkTitleImages) {
            super.initialize(checkTitleImages);

            this.rowValueSetted = getComponentsGridComponent()
                    .isRowValueSetted();

            FacesContext facesContext = processContext.getFacesContext();

            String converterId = getComponentsGridComponent()
                    .getRowValueConverter(facesContext);

            if (converterId != null) {
                rowValueConverter = facesContext.getApplication()
                        .createConverter(converterId);
            }
        }

        public Converter getRowValueConverter() {
            return rowValueConverter;
        }

        public void setInteractiveShow(boolean interactiveShow) {
            this.interactiveShow = interactiveShow;
        }

        public boolean isInteractiveShow() {
            return interactiveShow;
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
