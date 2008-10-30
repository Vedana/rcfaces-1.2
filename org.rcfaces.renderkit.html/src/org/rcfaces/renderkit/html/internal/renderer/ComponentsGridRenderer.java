/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.component.capability.IClientFullStateCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CollectionTools;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.Convertor;
import org.rcfaces.core.lang.provider.ISelectionProvider;
import org.rcfaces.core.model.IComponentRefModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.IRangeDataModel2;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
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

    private static final Map SORT_ALIASES = new HashMap(2);
    static {
        SORT_ALIASES.put(ISortEventCapability.SORT_SERVER, SORT_SERVER_COMMAND);
    }

    private static final String DEFAULT_ROW_CLASSNAMES[] = { "f_grid_row_even",
            "f_grid_row_odd" };

    private static final boolean NOT_SUPPORTED_SERVER_SORT = false;

    private static final String ROWCOUNT_PROPERTY = "org.rcfaces.html.componentsGrid.ROWCOUNT";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMPONENTS_GRID;
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        return new ComponentsGridRenderContext(componentRenderContext);
    }

    protected boolean serverTitleGeneration() {
        return true;
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
                htmlWriter.writeAttribute("v:asyncRender", true);
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

        if (dg instanceof IShowValueCapability) {
            Object showValue = ((IShowValueCapability) dg).getShowValue();
            String clientShowValue = null;

            if (showValue != null) {
                Converter converter = ((ComponentsGridRenderContext) tableContext)
                        .getRowValueConverter();

                clientShowValue = ValuesTools.convertValueToString(showValue,
                        converter, (UIComponent) dg, htmlWriter
                                .getComponentRenderContext().getFacesContext());
            }

            if (clientShowValue != null) {
                htmlWriter.writeAttribute("v:showValue", clientShowValue);
            }
        }
    }

    public ComponentsGridRenderContext createComponentsGridContext(
            IProcessContext processContext,
            IScriptRenderContext scriptRenderContext,
            ComponentsGridComponent dgc, int rowIndex, int forcedRow,
            ISortedComponent[] sortedComponents, String filterExpression,
            String showAdditionals, String hideAdditionals) {
        return new ComponentsGridRenderContext(processContext,
                scriptRenderContext, dgc, rowIndex, forcedRow,
                sortedComponents, filterExpression, showAdditionals,
                hideAdditionals);
    }

    protected void encodeBodyEnd(IHtmlWriter writer,
            AbstractGridRenderContext tableContext) throws WriterException {

        encodeBodyTableEnd(writer, tableContext);

        writer.getJavaScriptEnableMode().enableOnInit();

        super.encodeBodyEnd(writer, tableContext);
    }

    public void encodeChildren1(FacesContext facesContext, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter();

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        ComponentsGridRenderContext tableContext = (ComponentsGridRenderContext) getGridRenderContext(htmlWriter
                .getHtmlComponentRenderContext());

        // Dans tous les cas il faut positionner le renderContext !
        ComponentsListService componentsListServer = ComponentsListService
                .getInstance(facesContext);
        if (componentsListServer != null) {
            componentsListServer.setupComponent(componentRenderContext);
        }

        if (tableContext.isInteractiveShow()) {
            return;
        }

        int rowCount = encodeChildren(htmlWriter, tableContext, false, false);
        if (rowCount > 0) {
            componentRenderContext.setAttribute(ROWCOUNT_PROPERTY, new Integer(
                    rowCount));
        }
    }

    protected void encodeJsColumns(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        Integer rowCount = (Integer) jsWriter.getComponentRenderContext()
                .getAttribute(ROWCOUNT_PROPERTY);
        if (rowCount != null) {
            jsWriter.writeMethodCall("f_setRowCount").writeInt(
                    rowCount.intValue()).writeln(");");
        }

        encodeJsColumns(jsWriter, gridRenderContext, GENERATE_CELL_STYLE_CLASS);

        if (gridRenderContext.isSelectable()
                && gridRenderContext.getClientSelectionFullState() != IClientFullStateCapability.NONE_CLIENT_FULL_STATE) {

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
                    Set selectedObjects = CollectionTools.valuesToSet(
                            selectionModel, true);

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
            ComponentsGridRenderContext gridRenderContext, boolean encodeJs,
            boolean unknownRowCount) throws WriterException {
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

        int firstRowCount = gridRenderContext.getRowCount();

        boolean searchEnd = (rows > 0);
        // int firstCount = -1;
        int count = -1;

        if (searchEnd) {
            count = firstRowCount;
        }

        int sortTranslations[] = null;

        ISortedComponent sortedComponents[] = gridRenderContext
                .listSortedComponents();

        DataModel dataModel = componentsGridComponent.getDataModelValue();

        if (dataModel instanceof IComponentRefModel) {
            ((IComponentRefModel) dataModel)
                    .setComponent(componentsGridComponent);
        }

        boolean filtred = false;

        IFilterProperties filtersMap = gridRenderContext.getFiltersMap();
        if (filtersMap != null) {
            if (dataModel instanceof IFiltredModel) {
                IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

                filtredDataModel.setFilter(filtersMap);
                gridRenderContext.updateRowCount();

                filtred = true;

            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
                gridRenderContext.updateRowCount();
            }

        } else if (dataModel instanceof IFiltredModel) {
            IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
            gridRenderContext.updateRowCount();

            filtred = true;
        }

        if (sortedComponents != null && sortedComponents.length > 0) {

            if (NOT_SUPPORTED_SERVER_SORT) {
                throw new FacesException(
                        "Can not sort dataModel in server side !");
            }

            if (dataModel instanceof ISortedDataModel) {
                // On delegue au modele, le tri !

                // Nous devons êctre OBLIGATOIREMENT en mode rowValueColumnId
                if (gridRenderContext.isRowValueSetted() == false) {
                    throw new FacesException(
                            "Can not sort dataModel without attribute rowValue attribute specified !");
                }

                ((ISortedDataModel) dataModel).setSortParameters(
                        componentsGridComponent, sortedComponents);
            } else {
                throw new FacesException(
                        "ComponentsGrid can not be sorted automatically ! (the dataModel must implement ISortedDataModel)");
            }

            // Apres le tri, on connait peu etre la taille
            gridRenderContext.updateRowCount();

        } else if (dataModel instanceof ISortedDataModel) {
            // Reset des parametres de tri !
            ((ISortedDataModel) dataModel).setSortParameters(
                    componentsGridComponent, null);
        }

        // Initializer le IRandgeDataModel avant la selection/check/additionnal
        // informations !
        if (sortTranslations == null
                && rows > 0
                && ((dataModel instanceof IRangeDataModel) || (dataModel instanceof IRangeDataModel2))) {
            // Specifie le range que si il n'y a pas de tri !

            int rangeLength = rows;
            if (searchEnd) {
                // On regardera si il y a bien une suite ...
                rangeLength++;
            }

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel)
                        .setRowRange(rowIndex, rangeLength);
            }

            if (dataModel instanceof IRangeDataModel2) {
                ((IRangeDataModel2) dataModel).setRowRange(rowIndex,
                        rangeLength, searchEnd);
            }
        }

        int selectedIndexes[] = null;
        int selectedOffset = -1;
        Set selectedObjects = null;

        if (gridRenderContext.isSelectable()
                && gridRenderContext.getClientSelectionFullState() == IClientFullStateCapability.NONE_CLIENT_FULL_STATE) {

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
                    selectedObjects = CollectionTools.valuesToSet(
                            selectionModel, true);
                }
            }
        }

        // On recherche la taille ?
        if (searchEnd) {
            count = gridRenderContext.getRowCount();

            // Le tri a été fait coté serveur,
            // On connait peut être le nombre d'elements !
            if (count < 0 && sortTranslations != null) {
                count = sortTranslations.length;
            }

            if (count >= 0) {
                searchEnd = false;
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
        if (componentsGridComponent.getClientSelectionFullState(facesContext) != IClientFullStateCapability.NONE_CLIENT_FULL_STATE) {
            writeSelected = false;
        }

        try {
            // int i = 0;
            boolean selected = false;

            for (int i = 0;; i++) {
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
                boolean available = componentsGridComponent.isRowAvailable();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Set row index " + translatedRowIndex
                            + " returns " + available + " (rowIndexVar="
                            + rowIndexVar + ")");
                }

                if (available == false) {
                    count = rowIndex;
                    break;
                }

                if (searchEnd) {
                    // On teste juste la validité de la fin !
                    if (rows > 0 && i >= rows) {
                        break;
                    }
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
                    // NON: C'est géré coté client desormais (on fait transité
                    // le rowIndex !)
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
                            defaultCellStyleClass, i, translatedRowIndex);

                } else {
                    encodeRow((IHtmlWriter) writer, gridRenderContext,
                            rowValue, sa.toString(), selected, writeSelected,
                            defaultCellStyleClass, i, translatedRowIndex);
                }

                if (sortTranslations == null) {
                    if (selectedOffset >= 0
                            && selectedOffset >= selectedIndexes.length) {
                        selectedOffset = -1;
                        selected = false;
                    }
                }

                rowIndex++;
            }

            if (unknownRowCount && firstRowCount >= 0) {
                return count;

            } else if (rows > 0) {
                if (count > firstRowCount
                        || (componentsGridComponent.getFirst() == 0 && count == 0)) {
                    return count;
                }

            } else if (gridRenderContext.getRowCount() < 0) {
                return rowIndex;

            } else if (filtred) {
                if (searchEnd && count == 0) {
                    return count;
                }
            }

            return -1;

        } finally {
            componentsGridComponent.setRowIndex(-1);
        }
    }

    private void encodeRow(IHtmlWriter htmlWriter,
            ComponentsGridRenderContext gridRenderContext, String rowValue,
            String rowClassName, boolean selected, boolean writeSelected,
            String defaultCellStyleClass, int index, int rowIndex)
            throws WriterException {

        htmlWriter.startElement(IHtmlWriter.TR);

        if (rowValue != null) {
            htmlWriter.writeAttribute("v:rowValue", rowValue);
        }

        htmlWriter.writeAttribute("v:rowIndex", rowIndex);

        htmlWriter.writeClass(rowClassName);

        htmlWriter.writeId(computeRowId(htmlWriter));

        if (selected && writeSelected) {
            htmlWriter.writeAttribute("v:selected", true);
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

            if (gridRenderContext.getColumnState(columnIndex) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

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

                    htmlWriter.writeAttribute("v:className", csc);
                }
            }

            if (styleClass == null && defaultCellStyleClasses != null) {
                String csc[] = defaultCellStyleClasses[columnIndex];
                if (csc != null && csc.length > 0) {
                    styleClass = csc[index % csc.length];
                }
            }

            StringAppender sa = new StringAppender(defaultCellStyleClass, 32);
            if (selected) {
                sa.append(' ').append("f_grid_cell_selected");

                if (styleClass != null) {
                    StringTokenizer st = new StringTokenizer(styleClass, " ");
                    for (; st.hasMoreTokens();) {
                        String cellStyleClass = st.nextToken();

                        sa.append(' ').append(cellStyleClass).append(
                                "_selected");
                    }
                }

            } else if (styleClass != null) {
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
            String defaultCellStyleClass, int index, int rowIndex)
            throws WriterException {

        jsWriter.writeMethodCall("f_addRow2").writeString(
                computeRowId(jsWriter));

        IObjectLiteralWriter oWriter = jsWriter.write(',').writeObjectLiteral(
                true);

        if (rowClassName != null) {
            oWriter.writeSymbol("_styleClass").writeString(rowClassName);
        }
        if (rowValue != null) {
            oWriter.writeSymbol("_value").writeString(rowValue);
        }
        oWriter.writeSymbol("_rowIndex").writeInt(rowIndex);

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

    private String computeRowId(IComponentWriter jsWriter) {
        IComponentRenderContext componentRenderContext = jsWriter
                .getComponentRenderContext();
        IRenderContext renderContext = componentRenderContext
                .getRenderContext();

        String rowId = renderContext
                .getComponentClientId(componentRenderContext.getComponent());

        String namingSeparator = renderContext.getProcessContext()
                .getNamingSeparator();
        if (namingSeparator == null) {
            return rowId;
        }

        int idx = rowId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx >= 0) {
            rowId = rowId.substring(0, idx) + namingSeparator
                    + rowId.substring(idx + 1);
        }

        return rowId;

    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        ComponentsGridComponent componentsListComponent = (ComponentsGridComponent) writer
                .getComponentRenderContext().getComponent();

        try {
            encodeChildren1(writer.getComponentRenderContext()
                    .getFacesContext(), componentsListComponent);

        } catch (IOException e) {
            throw new WriterException("Can not encode children", e,
                    componentsListComponent);

        }

        componentsListComponent.setRowIndex(-1);

        super.encodeEnd(writer);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        FacesContext facesContext = context.getFacesContext();

        ComponentsGridComponent componentsGridComponent = (ComponentsGridComponent) component;

        boolean rowValueSetted = componentsGridComponent.isRowValueSetted();

        String selectedRows = componentData.getStringProperty("selectedItems");
        String deselectedRows = componentData
                .getStringProperty("deselectedItems");
        if (selectedRows != null || deselectedRows != null) {
            if (rowValueSetted) {

                Set selectedValues = SelectionTools.selectionValuesToSet(
                        facesContext, componentsGridComponent, false);

                Set newSelectedValues = updateSelection(facesContext,
                        selectedValues, selectedRows, deselectedRows,
                        componentsGridComponent);

                SelectionTools.setSelectionValues(facesContext,
                        componentsGridComponent, newSelectedValues);

            } else {
                int indexes[] = parseIndexes(selectedRows);
                int dindexes[] = null;
                boolean all = false;

                if (HtmlTools.ALL_VALUE.equals(deselectedRows)) {
                    all = true;
                    dindexes = EMPTY_INDEXES;
                } else {
                    dindexes = parseIndexes(deselectedRows);
                }

                if (indexes.length > 0 || all || dindexes.length > 0) {
                    setSelectedIndexes(facesContext, componentsGridComponent,
                            indexes, dindexes, all);
                }
            }
        }

        super.decode(context, component, componentData);
    }

    private Set updateSelection(FacesContext facesContext, Set set,
            String selectedRows, String deselectedRows,
            ComponentsGridComponent componentsGridComponent) {

        if (HtmlTools.ALL_VALUE.equals(deselectedRows)) {
            set.clear();

        } else if (set.size() > 0 && deselectedRows != null
                && deselectedRows.length() > 0) {
            List deselect = parseValues(facesContext, componentsGridComponent,
                    deselectedRows);

            if (deselect.isEmpty() == false) {
                set.removeAll(deselect);
            }
        }

        if (selectedRows != null && selectedRows.length() > 0) {
            List select = parseValues(facesContext, componentsGridComponent,
                    selectedRows);

            if (select.isEmpty() == false) {
                set.addAll(select);
            }

        }

        return set;
    }

    public static List parseValues(FacesContext facesContext,
            ComponentsGridComponent componentsGridComponent, String values) {
        StringTokenizer st = new StringTokenizer(values,
                HtmlTools.LIST_SEPARATORS);
        if (st.hasMoreTokens() == false) {
            return Collections.EMPTY_LIST;
        }

        List tokens = new ArrayList(st.countTokens());
        for (; st.hasMoreTokens();) {
            tokens.add(st.nextToken());
        }

        Object vs[] = convertStringsToValues(facesContext,
                componentsGridComponent, (String[]) tokens
                        .toArray(new String[tokens.size()]));

        return Arrays.asList(vs);
    }

    protected static final Object[] convertStringsToValues(
            FacesContext facesContext, ComponentsGridComponent component,
            String values[]) throws ConverterException {

        if (values == null || values.length < 1) {
            return values;
        }

        Converter converter = component.getRowValueConverter(facesContext);

        if (converter != null) {
            Object converted[] = new Object[values.length];

            for (int i = 0; i < converted.length; i++) {
                converted[i] = converter.getAsObject(facesContext, component,
                        values[i]);
            }

            return converted;
        }

        return values;
    }

    protected void addAjaxRequiredClasses(
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addAjaxRequiredClasses(javaScriptRenderContext);

        javaScriptRenderContext.appendRequiredClass(
                JavaScriptClasses.COMPONENTS_GRID, "asyncRender");
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
                String filterExpression, String showAdditionals,
                String hideAdditionals) {
            super(processContext, scriptRenderContext, gridComponent, rowIndex,
                    forcedRows, sortedComponents, filterExpression,
                    showAdditionals, hideAdditionals);
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

            rowValueConverter = getComponentsGridComponent()
                    .getRowValueConverter(facesContext);
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
            return (String) SORT_ALIASES.get(command);
        }

        public boolean isRowValueSetted() {
            return rowValueSetted;
        }
    }
}
