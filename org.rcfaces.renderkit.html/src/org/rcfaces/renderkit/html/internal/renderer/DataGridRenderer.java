/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AdditionalInformationComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.capability.IAdditionalInformationValuesCapability;
import org.rcfaces.core.component.capability.ICellImageCapability;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.capability.IAdditionalInformationComponent;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.ICheckComponent;
import org.rcfaces.core.internal.capability.ICheckRangeComponent;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.ISelectionComponent;
import org.rcfaces.core.internal.capability.ISelectionRangeComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.AdditionalInformationTools;
import org.rcfaces.core.internal.tools.ArrayIndexesModel;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.internal.tools.CollectionTools;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.GridServerSort;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.lang.provider.ICursorProvider;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.core.model.ITransactionalDataModel;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.HtmlValuesTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.service.DataGridService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataGridRenderer extends AbstractGridRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(DataGridRenderer.class);

    private static final Map SORT_ALIASES = new HashMap(8);

    static {
        SORT_ALIASES.put(ISortEventCapability.SORT_INTEGER,
                "f_dataGrid.Sort_Integer");
        SORT_ALIASES.put(ISortEventCapability.SORT_NUMBER,
                "f_dataGrid.Sort_Number");
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA,
                "f_dataGrid.Sort_Alpha");
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA_IGNORE_CASE,
                "f_dataGrid.Sort_AlphaIgnoreCase");
        SORT_ALIASES
                .put(ISortEventCapability.SORT_TIME, "f_dataGrid.Sort_Time");
        SORT_ALIASES
                .put(ISortEventCapability.SORT_DATE, "f_dataGrid.Sort_Date");
        SORT_ALIASES.put(ISortEventCapability.SORT_SERVER, SORT_SERVER_COMMAND);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATA_GRID;
    }

    protected boolean needAdditionalInformationContextState() {
        return true;
    }

    protected void encodeBodyBegin(IHtmlWriter htmlWriter,
            AbstractGridRenderContext data) throws WriterException {

        if (serverTitleGeneration() == false) {
            encodeBodyTableEnd(htmlWriter, data);
        }
        super.encodeBodyBegin(htmlWriter, data);
    }

    protected void encodeBodyEnd(IHtmlWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeBodyTableEnd(htmlWriter, gridRenderContext);
    }

    protected void writeFullStates(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext context, String jsCommand, Set objects)
            throws WriterException {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        DataGridRenderContext dataGridRenderContext = (DataGridRenderContext) context;

        FacesContext facesContext = jsWriter.getFacesContext();
        UIColumn rowValueColumnComponent = dataGridRenderContext
                .getRowValueColumn();

        jsWriter.writeMethodCall(jsCommand).write('[');
        int i = 0;
        for (Iterator it = objects.iterator(); it.hasNext();) {
            Object value = it.next();

            String convert = convertValue(facesContext,
                    rowValueColumnComponent, value);

            if (convert == null) {
                continue;
            }

            if (i > 0) {
                jsWriter.write(',');
            }

            jsWriter.writeString(convert);

            i++;
        }

        jsWriter.writeln("]);");
    }

    protected UIColumn getRowValueColumn(IGridComponent dg) {
        DataGridComponent dataGridComponent = (DataGridComponent) dg;

        String rowValueColumnId = dataGridComponent.getRowValueColumnId();
        if (rowValueColumnId != null) {
            for (IColumnIterator it = dg.listColumns(); it.hasNext();) {
                UIColumn column = it.next();
                if (rowValueColumnId.equals(column.getId()) == false) {
                    continue;
                }

                return column;
            }

            throw new FacesException("Can not find column '" + rowValueColumnId
                    + "'.");
        }

        return null;
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, GENERATE_CELL_IMAGES);
    }

    protected void encodeJsColumns(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext, int generatorMask)
            throws WriterException {

        if (serverTitleGeneration() == false) {
            generatorMask |= GENERATE_CELL_TEXT | GENERATE_CELL_WIDTH;
        }

        super.encodeJsColumns(jsWriter, tableContext, generatorMask);
    }

    protected void writeGridColumnProperties(IObjectLiteralWriter objectWriter,
            AbstractGridRenderContext tableContext, UIColumn columnComponent,
            int columnIndex) throws WriterException {

        super.writeGridColumnProperties(objectWriter, tableContext,
                columnComponent, columnIndex);

        UIColumn rowValueColumn = ((DataGridRenderContext) tableContext)
                .getRowValueColumn();

        if (rowValueColumn == columnComponent) {
            objectWriter.writeSymbol("_valueColumn").writeBoolean(true);
        }
    }

    protected void encodeJsBody(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext) throws WriterException {

        super.encodeJsBody(jsWriter, tableContext);

        if (ENABLE_SERVER_REQUEST) {
            String interactiveComponentClientId = jsWriter
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getCurrentInteractiveRenderComponentClientId();

            if (interactiveComponentClientId != null) {
                return;
            }
        }

        encodeJsBodyRows(jsWriter, tableContext);
    }

    protected void encodeJsBodyRows(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext) throws WriterException {

        IJavaScriptRenderContext javascriptRenderContext = jsWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

        String rowVarName = javascriptRenderContext.allocateVarName();
        tableContext.setRowVarName(rowVarName);

        encodeJsTransactionalRows(jsWriter,
                (DataGridRenderContext) tableContext, true, false);
    }

    public void encodeJsTransactionalRows(IJavaScriptWriter jsWriter,
            DataGridRenderContext tableContext, boolean sendFullStates,
            boolean unknownRowCount) throws WriterException {

        DataModel dataModel = tableContext.getDataModel();

        if ((dataModel instanceof ITransactionalDataModel) == false) {
            encodeJsRows(jsWriter, tableContext, sendFullStates,
                    unknownRowCount);
            return;
        }

        ITransactionalDataModel transactionalDataModel = (ITransactionalDataModel) dataModel;

        try {
            transactionalDataModel.enableTransactionalObjects(true);

            encodeJsRows(jsWriter, tableContext, sendFullStates,
                    unknownRowCount);

        } finally {
            transactionalDataModel.enableTransactionalObjects(false);
        }
    }

    private void encodeJsRows(IJavaScriptWriter jsWriter,
            DataGridRenderContext tableContext, boolean sendFullStates,
            boolean unknownRowCount) throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();

        IGridComponent gridComponent = tableContext.getGridComponent();
        DataModel dataModel = tableContext.getDataModel();

        boolean filtred = false;
        int firstCount = tableContext.getRowCount();

        IFilterProperties filtersMap = tableContext.getFiltersMap();
        if (filtersMap != null) {
            if (dataModel instanceof IFiltredModel) {
                IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

                filtredDataModel.setFilter(filtersMap);
                tableContext.updateRowCount();

                filtred = true;

            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
                tableContext.updateRowCount();
            }

        } else if (dataModel instanceof IFiltredModel) {
            IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
            tableContext.updateRowCount();

            filtred = true;
        }

        int rows = tableContext.getForcedRows();
        if (rows < 1) {
            rows = tableContext.getRows();
        }

        boolean searchEnd = (rows > 0);
        // int firstCount = -1;
        int count = -1;

        if (searchEnd) {
            count = firstCount;
        }

        int sortTranslations[] = null;

        ISortedComponent sortedComponents[] = tableContext
                .listSortedComponents();
        if (sortedComponents != null && sortedComponents.length > 0) {
            if (dataModel instanceof ISortedDataModel) {
                // On delegue au modele, le tri !

                // Nous devons �tre OBLIGATOIREMENT en mode rowValueColumnId
                if (tableContext.getRowValueColumn() == null) {
                    throw new FacesException(
                            "Can not sort dataModel without attribute rowValueColumnId specified !");
                }

                ((ISortedDataModel) dataModel).setSortParameters(
                        (UIComponent) gridComponent, sortedComponents);
            } else {
                // Il faut faire le tri à la main !

                sortTranslations = GridServerSort.computeSortedTranslation(
                        facesContext, gridComponent, dataModel,
                        sortedComponents);
            }

            // Apres le tri, on connait peu etre la taille
            tableContext.updateRowCount();
        } else {

            if (dataModel instanceof ISortedDataModel) {
                // Reset des parametres de tri !
                ((ISortedDataModel) dataModel).setSortParameters(
                        (UIComponent) gridComponent, null);
            }
        }

        int rowIndex = tableContext.getFirst();

        int selectedIndexes[] = null;
        int selectedOffset = -1;
        Set selectedObjects = null;

        if (tableContext.isSelectable()
                && (tableContext.isClientSelectionFullState() == false || sendFullStates)) {

            Object selectionModel = ((ISelectedValuesCapability) gridComponent)
                    .getSelectedValues();

            if (selectionModel != null) {
                if (selectionModel instanceof IIndexesModel) {
                    selectedIndexes = ((IIndexesModel) selectionModel)
                            .listSortedIndexes();

                    if (tableContext.isClientSelectionFullState()) {
                        writeFullStates(jsWriter, "f_setSelectionStates",
                                selectedIndexes);
                        selectedIndexes = null;

                    } else {
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
                    }
                } else {
                    selectedObjects = CollectionTools.valuesToSet(
                            selectionModel, true);

                    if (tableContext.isClientSelectionFullState()) {
                        writeFullStates(jsWriter, tableContext,
                                "f_setSelectionStates", selectedObjects);
                        selectedObjects = null;
                    }
                }
            }
        }

        int checkedIndexes[] = null;
        int checkedOffset = -1;
        Set checkedObjects = null;

        if (tableContext.isCheckable()
                && (tableContext.isClientCheckFullState() == false || sendFullStates)) {

            Object checkModel = ((ICheckedValuesCapability) gridComponent)
                    .getCheckedValues();
            if (checkModel != null) {
                if (checkModel instanceof IIndexesModel) {
                    checkedIndexes = ((IIndexesModel) checkModel)
                            .listSortedIndexes();

                    if (tableContext.isClientCheckFullState()) {
                        writeFullStates(jsWriter, "f_setCheckStates",
                                checkedIndexes);
                        checkedIndexes = null;

                    } else {
                        if (sortTranslations == null) {
                            // Dans le cas ou il n'y a pas de tri
                            // Les indexes sont donc lineaires ...

                            if (checkedIndexes != null
                                    && checkedIndexes.length > 0) {
                                // Recherche du premier RowOffset
                                for (int i = 0; i < checkedIndexes.length; i++) {
                                    if (checkedIndexes[i] >= rowIndex) {
                                        checkedOffset = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                } else {
                    checkedObjects = CollectionTools.valuesToSet(checkModel,
                            true);

                    if (tableContext.isClientCheckFullState()) {
                        writeFullStates(jsWriter, tableContext,
                                "f_setCheckStates", checkedObjects);
                        checkedObjects = null;
                    }
                }
            }
        }

        int additionalIndexes[] = null;
        int additionalOffset = -1;
        Set additionalObjects = null;

        UIColumn rowValueColumn = tableContext.getRowValueColumn();

        if (tableContext.hasAdditionalInformations()) {
            // Meme si c'est en fullstate il faut envoyer le body du cartouche !

            Object additionalModel = ((IAdditionalInformationValuesCapability) gridComponent)
                    .getAdditionalInformationValues();

            String showAdditionalValues = tableContext
                    .getRequestShowAdditionalValues();
            String hideAdditionalValues = tableContext
                    .getRequestHideAdditionalValues();
            if (showAdditionalValues != null || hideAdditionalValues != null) {
                additionalModel = updateAdditionalValues(facesContext,
                        gridComponent, rowValueColumn, additionalModel,
                        showAdditionalValues, hideAdditionalValues);
            }

            if (additionalModel != null) {
                if (additionalModel instanceof IIndexesModel) {
                    additionalIndexes = ((IIndexesModel) additionalModel)
                            .listSortedIndexes();

                    if (tableContext.isClientAdditionalFullState()) {
                        writeFullStates(jsWriter, "f_setAdditionalStates",
                                additionalIndexes);
                        additionalIndexes = null;

                    } else {
                        if (sortTranslations == null) {
                            // Dans le cas ou il n'y a pas de tri
                            // Les indexes sont donc lineaires ...

                            if (additionalIndexes != null
                                    && additionalIndexes.length > 0) {
                                // Recherche du premier RowOffset
                                for (int i = 0; i < additionalIndexes.length; i++) {
                                    if (additionalIndexes[i] >= rowIndex) {
                                        additionalOffset = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                } else {
                    additionalObjects = CollectionTools.valuesToSet(
                            additionalModel, true);

                    if (tableContext.isClientAdditionalFullState()) {
                        writeFullStates(jsWriter, tableContext,
                                "f_setAdditionalStates", additionalObjects);
                        additionalObjects = null;
                    }
                }
            }
        }

        UIColumn columns[] = tableContext.listColumns();
        boolean testImageUrls[] = new boolean[columns.length];
        for (int i = 0; i < columns.length; i++) {
            UIColumn column = columns[i];

            if (column instanceof ICellImageSettings) {
                testImageUrls[i] = ((ICellImageSettings) column)
                        .isCellImageURLSetted();
            }
        }

        if (sortTranslations == null && rows > 0
                && (dataModel instanceof IRangeDataModel)) {
            // Specifie le range que si il n'y a pas de tri !

            int rangeLength = rows;
            if (searchEnd) {
                // On regardera si il y a bien une suite ...
                rangeLength++;
            }

            ((IRangeDataModel) dataModel).setRowRange(rowIndex, rangeLength);
        }

        // On recherche la taille ?
        if (searchEnd) {
            count = tableContext.getRowCount();

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
        String rowCountVar = tableContext.getRowCountVar();
        if (rowCountVar != null) {
            varContext.put(rowCountVar, new Integer(count));
        }

        String rowIndexVar = tableContext.getRowIndexVar();

        boolean designerMode = tableContext.isDesignerMode();
        try {
            boolean selected = false;
            boolean checked = false;
            boolean additional = false;
            String rowId = null;

            int rowValueColumnIndex = -1;
            if (designerMode && rowValueColumn != null) {
                for (int i = 0; i < columns.length; i++) {
                    UIColumn dataColumnComponent = columns[i];
                    if (dataColumnComponent != rowValueColumn) {
                        continue;
                    }

                    rowValueColumnIndex = i;
                    break;
                }

            }

            for (int i = 0;; i++) {
                if (searchEnd == false) {
                    // Pas de recherche de la fin !
                    // On peut sortir tout de suite ...
                    if (rows > 0 && i >= rows) {
                        break;
                    }
                }

                int translatedRowIndex = rowIndex;

                if (rowValueColumn != null) {
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
                        if (checkedOffset >= 0) {
                            if (checkedIndexes[checkedOffset] == rowIndex) {
                                checked = true;

                                checkedOffset++;

                            } else {
                                checked = false;
                            }
                        }
                        if (additionalOffset >= 0) {
                            if (additionalIndexes[additionalOffset] == rowIndex) {
                                additional = true;

                                additionalOffset++;

                            } else {
                                additional = false;
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
                        if (checkedIndexes != null) {
                            checked = false;
                            for (int j = 0; j < checkedIndexes.length; j++) {
                                if (checkedIndexes[j] != translatedRowIndex) {
                                    continue;
                                }

                                checked = true;
                                break;
                            }
                        }
                        if (additionalIndexes != null) {
                            additional = false;
                            for (int j = 0; j < additionalIndexes.length; j++) {
                                if (additionalIndexes[j] != translatedRowIndex) {
                                    continue;
                                }

                                additional = true;
                                break;
                            }
                        }
                    }
                }

                gridComponent.setRowIndex(translatedRowIndex);
                boolean available = gridComponent.isRowAvailable();
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

                if (rowValueColumn != null) {
                    Object value;

                    if (designerMode) {
                        String sd[] = (String[]) gridComponent.getRowData();
                        if (sd != null && sd.length > rowValueColumnIndex) {
                            value = sd[rowValueColumnIndex];

                        } else {
                            value = String.valueOf(i);
                        }

                        rowId = (String) value;

                    } else {
                        value = ((ValueHolder) rowValueColumn).getValue();

                        rowId = convertValue(facesContext, rowValueColumn,
                                value);
                    }

                    if (value != null) {
                        if (checkedObjects != null) {
                            checked = checkedObjects.contains(value);
                        }
                        if (selectedObjects != null) {
                            selected = selectedObjects.contains(value);
                        }
                        if (additionalObjects != null) {
                            additional = additionalObjects.contains(value);
                        }
                    }

                    if (rowId == null) {
                        throw new FacesException(
                                "Value associated to the row at index "
                                        + rowIndex + " is null !");
                    }
                }

                encodeJsRow(jsWriter, tableContext, i, rowId, rowIndex,
                        selected, checked, additional, translatedRowIndex);

                if (sortTranslations == null) {
                    if (selectedOffset >= 0
                            && selectedOffset >= selectedIndexes.length) {
                        selectedOffset = -1;
                        selected = false;
                    }
                    if (checkedOffset >= 0
                            && checkedOffset >= checkedIndexes.length) {
                        checkedOffset = -1;
                        checked = false;
                    }
                    if (additionalOffset >= 0
                            && additionalOffset >= additionalIndexes.length) {
                        additionalOffset = -1;
                        additional = false;
                    }
                }

                rowIndex++;
            }

        } finally {
            gridComponent.setRowIndex(-1);

            if (rowCountVar != null) {
                varContext.remove(rowCountVar);
            }

            if (rowIndexVar != null) {
                varContext.remove(rowIndexVar);
            }
        }

        // Le count a évolué ?
        // 2 solutions:
        // * mode page par page, nous sommes à la fin, ou il y a eu un tri
        // * en mode liste, le dataModel ne pouvait pas encore donner le nombre
        // de rows

        if (unknownRowCount && firstCount >= 0) {
            encodeJsRowCount(jsWriter, tableContext, count);

        } else if (rows > 0) {
            if (count > firstCount
                    || (gridComponent.getFirst() == 0 && count == 0)) {
                encodeJsRowCount(jsWriter, tableContext, count);
            }

        } else if (tableContext.getRowCount() < 0) {
            encodeJsRowCount(jsWriter, tableContext, rowIndex);

        } else if (filtred) {
            if (searchEnd && count == 0) {
                encodeJsRowCount(jsWriter, tableContext, count);
            }
        }
    }

    private Object updateAdditionalValues(FacesContext facesContext,
            IGridComponent gridComponent, UIColumn rowValueColumn,
            Object additionalModel, String showAdditionalRows,
            String hideAdditionalRows) {

        if (rowValueColumn != null) {
            Set additionalValues = AdditionalInformationTools
                    .additionalInformationValuesToSet(facesContext,
                            (IAdditionalInformationComponent) gridComponent,
                            false);

            Set newAdditionalValues = updateValues(facesContext,
                    rowValueColumn, additionalValues, showAdditionalRows,
                    hideAdditionalRows);

            return newAdditionalValues;

        }

        IIndexesModel indexesModel = (IIndexesModel) additionalModel;
        if (indexesModel == null) {
            indexesModel = new ArrayIndexesModel();

        } else {
            indexesModel = indexesModel.copy();
        }

        if (HtmlTools.ALL_VALUE.equals(hideAdditionalRows)) {
            indexesModel.clearIndexes();

        } else {
            int uindexes[] = parseIndexes(hideAdditionalRows);
            for (int i = 0; i < uindexes.length; i++) {
                indexesModel.removeIndex(uindexes[i]);
            }
        }

        int cindexes[] = parseIndexes(showAdditionalRows);
        for (int i = 0; i < cindexes.length; i++) {
            indexesModel.addIndex(cindexes[i]);
        }

        return indexesModel;
    }

    protected void encodeJsRowCount(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext tableContext, int count)
            throws WriterException {
        htmlWriter.writeMethodCall("f_setRowCount").writeInt(count).writeln(
                ");");
    }

    protected void encodeJsRow(IJavaScriptWriter jsWriter,
            DataGridRenderContext tableContext, int index, String rowId,
            int iRowId, boolean selected, boolean checked, boolean additional,
            int rowIndex) throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();
        IGridComponent dataGridComponent = tableContext.getGridComponent();
        UIColumn dcs[] = tableContext.listColumns();
        int columnNumber = dcs.length;

        String trClassName = null; // Pas d'évaluation pour chaque ligne

        String rowVarName = tableContext.getRowVarName();

        String values[] = null;
        if (ALLOCATE_ROW_STRINGS) {
            values = new String[columnNumber];
        }

        if (ALLOCATE_ROW_STRINGS == false) {
            if (index < 1) {
                jsWriter.write("var ");
            }
            jsWriter.write(rowVarName).write('=').writeMethodCall("f_addRow2");

            if (rowId != null) {
                jsWriter.writeString(rowId);

            } else {
                jsWriter.writeInt(iRowId);
            }

            jsWriter.write(',');

            IObjectLiteralWriter objectLiteralWriter = jsWriter
                    .writeObjectLiteral(true);

            if (selected && tableContext.isSelectable()
                    && tableContext.isClientSelectionFullState() == false) {
                objectLiteralWriter.writeSymbol("_selected").writeBoolean(true);
            }

            if (checked && tableContext.isCheckable()
                    && tableContext.isClientCheckFullState() == false) {
                objectLiteralWriter.writeSymbol("_checked").writeBoolean(true);
            }

            if (additional && tableContext.hasAdditionalInformations()
                    && tableContext.isClientAdditionalFullState() == false) {
                objectLiteralWriter.writeSymbol("_additional").writeBoolean(
                        true);
            }

            if (trClassName != null) {
                objectLiteralWriter.writeSymbol("_styleClass").write(
                        trClassName);
            }

            if (rowIndex >= 0) {
                objectLiteralWriter.writeSymbol("_rowIndex").writeInt(rowIndex);
            }

            objectLiteralWriter.end();
        }

        String images[] = null;
        String cellStyleClasses[] = null;
        String cellToolTipTexts[] = null;
        int visibleColumns = 0;

        boolean designerMode = tableContext.isDesignerMode();
        String designerData[] = null;
        if (designerMode) {
            designerData = (String[]) dataGridComponent.getRowData();
        }

        for (int i = 0; i < columnNumber; i++) {
            UIColumn dc = dcs[i];

            int rowState = tableContext.getColumnState(i);
            if (rowState == AbstractGridRenderContext.SERVER_HIDDEN) {
                continue;
            }

            if (rowId == null || tableContext.getRowValueColumn() != dc) {
                String svalue = null;

                if (designerMode) {
                    if (designerData.length > i) {
                        svalue = designerData[i];
                    }

                } else if (dc instanceof ValueHolder) {
                    Object value = ((ValueHolder) dc).getValue();

                    if (value != null) {
                        svalue = convertValue(facesContext, dc, value);
                    }
                }

                if (ALLOCATE_ROW_STRINGS) {
                    if (svalue == null) {
                        svalue = NULL_VALUE;
                    }

                    values[i] = svalue;

                } else {
                    jsWriter.write(',').writeString(svalue);
                }
            }

            if (rowState != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            if (tableContext.isColumnImageURL(i)) {
                String imageURL = ((ICellImageCapability) dc).getCellImageURL();
                if (imageURL != null) {
                    if (images == null) {
                        images = new String[columnNumber];
                    }
                    images[i] = imageURL;
                }
            }

            if (tableContext.isCellStyleClass(i)) {
                String cs = ((ICellStyleClassCapability) dc)
                        .getCellStyleClass();
                if (cs != null) {
                    if (cellStyleClasses == null) {
                        cellStyleClasses = new String[columnNumber];
                    }

                    cellStyleClasses[i] = cs;
                }
            }

            if (tableContext.isCellToolTipText(i)) {
                String ct = ((ICellToolTipTextCapability) dc)
                        .getCellToolTipText();
                if (ct != null) {
                    if (cellToolTipTexts == null) {
                        cellToolTipTexts = new String[columnNumber];
                    }

                    cellToolTipTexts[i] = ct;
                }
            }

            visibleColumns++;
        }

        if (ALLOCATE_ROW_STRINGS) {
            for (int i = 0; i < values.length; i++) {
                String v = values[i];
                if (v == null || v == NULL_VALUE) {
                    continue;
                }

                values[i] = jsWriter.allocateString(v);
            }

            if (index < 1) {
                jsWriter.write("var ");
            }
            jsWriter.write(rowVarName).write('=').writeMethodCall("f_addRow2");

            if (rowId != null) {
                jsWriter.writeString(rowId);

            } else {
                jsWriter.writeInt(iRowId);
            }

            jsWriter.write(',');

            IObjectLiteralWriter objectLiteralWriter = jsWriter
                    .writeObjectLiteral(true);

            if (selected && tableContext.isSelectable()
                    && tableContext.isClientSelectionFullState() == false) {
                objectLiteralWriter.writeSymbol("_selected").writeBoolean(true);
            }

            if (checked && tableContext.isCheckable()
                    && tableContext.isClientCheckFullState() == false) {
                objectLiteralWriter.writeSymbol("_checked").writeBoolean(true);
            }

            if (rowIndex >= 0) {
                objectLiteralWriter.writeSymbol("_rowIndex").writeInt(rowIndex);
            }

            if (tableContext.hasAdditionalInformations()) {
                AdditionalInformationComponent additionalInformationComponents[] = tableContext
                        .listAdditionalInformations();

                AdditionalInformationComponent additionalInformationComponent = null;

                for (int i = 0; i < additionalInformationComponents.length; i++) {
                    AdditionalInformationComponent add = additionalInformationComponents[i];

                    if (add.isRendered() == false) {
                        continue;
                    }

                    additionalInformationComponent = add;
                    break;
                }

                if (additionalInformationComponent != null) {
                    if (additional) {
                        String content = encodeAdditionalInformation(jsWriter,
                                additionalInformationComponent);

                        if (content != null) {
                            objectLiteralWriter.writeSymbol(
                                    "_additionalContent").writeString(content);
                        }

                    }

                    String addtionalInformationHeight = additionalInformationComponent
                            .getHeight(facesContext);
                    if (addtionalInformationHeight != null) {
                        objectLiteralWriter.writeSymbol("_additionalHeight")
                                .writeString(addtionalInformationHeight);
                    }

                } else {
                    // Pas d'additional information ...
                    objectLiteralWriter.writeSymbol("_additionalContent")
                            .writeBoolean(false);
                }
            }

            if (trClassName != null) {
                objectLiteralWriter.writeSymbol("_styleClass").write(
                        trClassName);
            }

            objectLiteralWriter.end();

            for (int i = 0; i < values.length; i++) {
                String v = values[i];
                if (v == null) {
                    continue;
                }

                jsWriter.write(',');
                if (v == NULL_VALUE) {
                    jsWriter.writeNull();
                    continue;
                }

                jsWriter.write(v);
            }

        }
        jsWriter.writeln(");");

        if (images != null || cellStyleClasses != null
                || cellToolTipTexts != null) {

            allocateStrings(jsWriter, images, images);
            allocateStrings(jsWriter, cellStyleClasses, cellStyleClasses);
            allocateStrings(jsWriter, cellToolTipTexts, cellToolTipTexts);

            jsWriter.writeMethodCall("f_setCells2").write(rowVarName);
            int pred = 0;

            for (int i = 0; i < columnNumber; i++) {
                if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                    continue;
                }

                String imageURL = null;
                if (images != null) {
                    imageURL = images[i];
                }

                String cellStyleClass = null;
                if (cellStyleClasses != null) {
                    cellStyleClass = cellStyleClasses[i];
                }

                String toolTipText = null;
                if (cellToolTipTexts != null) {
                    toolTipText = cellToolTipTexts[i];
                }

                if (imageURL == null && cellStyleClass == null
                        && toolTipText == null) {
                    pred++;
                    continue;
                }

                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }

                jsWriter.write(',');

                IObjectLiteralWriter objWriter = jsWriter
                        .writeObjectLiteral(true);

                if (imageURL != null) {
                    objWriter.writeSymbol("_imageURL").write(imageURL);
                }

                if (cellStyleClass != null) {
                    objWriter.writeSymbol("_styleClass").write(cellStyleClass);
                }

                if (toolTipText != null) {
                    objWriter.writeSymbol("_toolTipText").write(toolTipText);
                }

                objWriter.end();
            }

            jsWriter.writeln(");");

        }
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        DataGridRenderContext tableContext = new DataGridRenderContext(
                componentRenderContext);

        return tableContext;
    }

    public DataGridRenderContext createTableContext(
            IProcessContext processContext,
            IScriptRenderContext scriptRenderContext, IGridComponent dg,
            int rowIndex, int forcedRows, ISortedComponent sortedComponents[],
            String filterExpression, String showAdditional,
            String hideAdditional) {

        DataGridRenderContext tableContext = new DataGridRenderContext(
                processContext, scriptRenderContext, dg, rowIndex, forcedRows,
                sortedComponents, filterExpression, showAdditional,
                hideAdditional);

        return tableContext;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class DataGridRenderContext extends AbstractGridRenderContext {

        private static final String REVISION = "$Revision$";

        private UIColumn rowValueColumn;

        public DataGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext, IGridComponent dg,
                int rowIndex, int forcedRows,
                ISortedComponent[] sortedComponents, String filterExpression,
                String showAdditionals, String hideAdditionals) {
            super(processContext, scriptRenderContext, dg, rowIndex,
                    forcedRows, sortedComponents, filterExpression,
                    showAdditionals, hideAdditionals);

            initializeDataGrid();
        }

        public DataGridRenderContext(
                IHtmlComponentRenderContext componentRenderContext) {
            super(componentRenderContext);

            initializeDataGrid();
        }

        protected void initializeDataGrid() {
            rowValueColumn = DataGridRenderer.this
                    .getRowValueColumn(getGridComponent());
        }

        protected String convertAliasCommand(String command) {
            return (String) SORT_ALIASES.get(command);
        }

        public UIColumn getRowValueColumn() {
            return rowValueColumn;
        }
    }

    protected void writeGridComponentAttributes(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, IGridComponent dg)
            throws WriterException {

        if (ENABLE_SERVER_REQUEST) {
            DataGridService dataGridServer = DataGridService
                    .getInstance(htmlWriter.getComponentRenderContext()
                            .getFacesContext());
            if (dataGridServer != null) {
                htmlWriter.writeAttribute("v:asyncRender", true);
            }
        }

        if (dg instanceof ICursorProvider) {
            Object cursorValue = ((ICursorProvider) dg).getCursorValue();
            String clientCursorValue = null;

            if (cursorValue != null) {
                UIColumn rowValueColumn = ((DataGridRenderContext) tableContext)
                        .getRowValueColumn();

                if (rowValueColumn != null) {
                    clientCursorValue = ValuesTools.convertValueToString(
                            cursorValue, rowValueColumn, htmlWriter
                                    .getComponentRenderContext()
                                    .getFacesContext());
                }
            }

            if (clientCursorValue != null) {
                htmlWriter.writeAttribute("v:cursorValue", clientCursorValue);
            }

        }

        if (dg instanceof IShowValueCapability) {
            Object showValue = ((IShowValueCapability) dg).getShowValue();
            String clientShowValue = null;

            if (showValue != null) {
                UIColumn rowValueColumn = ((DataGridRenderContext) tableContext)
                        .getRowValueColumn();

                if (rowValueColumn != null) {
                    clientShowValue = ValuesTools.convertValueToString(
                            showValue, rowValueColumn, htmlWriter
                                    .getComponentRenderContext()
                                    .getFacesContext());

                } else {
                    clientShowValue = String.valueOf(showValue);
                }
            }

            if (clientShowValue != null) {
                htmlWriter.writeAttribute("v:showValue", clientShowValue);
            }

        }
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        FacesContext facesContext = context.getFacesContext();

        IGridComponent gridComponent = (IGridComponent) component;

        UIColumn rowValueColumn = getRowValueColumn(gridComponent);

        if (gridComponent instanceof ISelectionComponent) {
            String selectedRows = componentData
                    .getStringProperty("selectedItems");
            String deselectedRows = componentData
                    .getStringProperty("deselectedItems");
            if (selectedRows != null || deselectedRows != null) {
                if (rowValueColumn != null) {
                    Set selectedValues = SelectionTools.selectionValuesToSet(
                            facesContext, (ISelectionComponent) gridComponent,
                            false);

                    Set newSelectedValues = updateValues(facesContext,
                            rowValueColumn, selectedValues, selectedRows,
                            deselectedRows);

                    SelectionTools.setSelectionValues(facesContext,
                            (ISelectionComponent) gridComponent,
                            newSelectedValues);

                } else if (gridComponent instanceof ISelectionRangeComponent) {
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
                        setSelectedIndexes(facesContext,
                                (ISelectionRangeComponent) gridComponent,
                                indexes, dindexes, all);
                    }
                }
            }
        }

        if (gridComponent instanceof ICheckComponent) {
            String checkedRows = componentData
                    .getStringProperty("checkedItems");
            String uncheckedRows = componentData
                    .getStringProperty("uncheckedItems");
            if (checkedRows != null || uncheckedRows != null) {
                if (rowValueColumn != null) {

                    Set checkedValues = CheckTools.checkValuesToSet(
                            facesContext, (ICheckComponent) gridComponent,
                            false);

                    Set newCheckedValues = updateValues(facesContext,
                            rowValueColumn, checkedValues, checkedRows,
                            uncheckedRows);

                    CheckTools.setCheckValues(facesContext,
                            (ICheckComponent) gridComponent, newCheckedValues);

                } else if (gridComponent instanceof ICheckRangeComponent) {
                    int cindexes[] = parseIndexes(checkedRows);
                    int uindexes[] = null;
                    boolean all = false;

                    if (HtmlTools.ALL_VALUE.equals(uncheckedRows)) {
                        all = true;
                        uindexes = EMPTY_INDEXES;

                    } else {
                        uindexes = parseIndexes(uncheckedRows);
                    }

                    if (cindexes.length > 0 || uindexes.length > 0 || all) {
                        setCheckedIndexes(facesContext,
                                (ICheckRangeComponent) gridComponent, cindexes,
                                uindexes, all);
                    }
                }
            }
        }

        if (gridComponent instanceof IAdditionalInformationComponent) {
            String showAdditionalRows = componentData
                    .getStringProperty("showAdditional");
            String hideAdditionalRows = componentData
                    .getStringProperty("hideAdditional");
            if (showAdditionalRows != null || hideAdditionalRows != null) {
                if (rowValueColumn != null) {

                    Set additionalValues = AdditionalInformationTools
                            .additionalInformationValuesToSet(
                                    facesContext,
                                    (IAdditionalInformationComponent) gridComponent,
                                    false);

                    Set newAdditionalValues = updateValues(facesContext,
                            rowValueColumn, additionalValues,
                            showAdditionalRows, hideAdditionalRows);

                    AdditionalInformationTools.setAdditionalInformationValues(
                            facesContext,
                            (IAdditionalInformationComponent) gridComponent,
                            newAdditionalValues);

                } else {
                    int cindexes[] = parseIndexes(showAdditionalRows);
                    int uindexes[] = null;
                    boolean all = false;

                    if (HtmlTools.ALL_VALUE.equals(hideAdditionalRows)) {
                        all = true;
                        uindexes = EMPTY_INDEXES;

                    } else {
                        uindexes = parseIndexes(hideAdditionalRows);
                    }

                    if (cindexes.length > 0 || uindexes.length > 0 || all) {
                        setAdditionalIndexes(
                                facesContext,
                                (IAdditionalInformationComponent) gridComponent,
                                cindexes, uindexes, all);
                    }
                }
            }
        }

        String cursorValue = componentData.getStringProperty("cursor");
        if (cursorValue != null) {
            Object cursorValueObject = ValuesTools.convertStringToValue(
                    facesContext, rowValueColumn, cursorValue, false);

            Object oldCursorValueObject = ((ICursorProvider) gridComponent)
                    .getCursorValue();
            if (isEquals(oldCursorValueObject, cursorValueObject) == false) {
                ((ICursorProvider) gridComponent)
                        .setCursorValue(cursorValueObject);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.CURSOR_VALUE, oldCursorValueObject,
                        cursorValueObject));
            }
        }

        super.decode(context, component, componentData);
    }

    private Set updateValues(FacesContext facesContext,
            UIColumn columnComponent, Set values, String selectedRows,
            String deselectedRows) {

        if (HtmlTools.ALL_VALUE.equals(deselectedRows)) {
            values.clear();

        } else if (values.isEmpty() == false && deselectedRows != null
                && deselectedRows.length() > 0) {
            List deselect = HtmlValuesTools.parseValues(facesContext,
                    columnComponent, true, false, deselectedRows);

            if (deselect.isEmpty() == false) {
                values.removeAll(deselect);
            }
        }

        if (selectedRows != null && selectedRows.length() > 0) {
            List select = HtmlValuesTools.parseValues(facesContext,
                    columnComponent, true, false, selectedRows);

            if (select.isEmpty() == false) {
                values.addAll(select);
            }

        }

        return values;
    }
}
