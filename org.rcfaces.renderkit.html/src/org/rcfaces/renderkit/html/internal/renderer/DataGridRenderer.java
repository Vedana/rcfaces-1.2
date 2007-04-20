/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.listener.IServerActionListener;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.DataGridServerSort;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.lang.provider.ICursorProvider;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.core.model.ITransactionalDataModel;
import org.rcfaces.renderkit.html.internal.EventsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
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
        SORT_ALIASES.put(ISortEventCapability.SORT_SERVER,
                "f_dataGrid.Sort_Server");
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATA_GRID;
    }

    protected void writeGridColumnProperties(IObjectLiteralWriter objectWriter,
            AbstractGridRenderContext tableContext, UIColumn columnComponent,
            int columnIndex) throws WriterException {

        Object sort = tableContext.getSortCommand(columnIndex);
        if (sort != null) {
            if (sort instanceof String) {
                String aliasCommand = (String) sort;

                if (tableContext.getSortClientSide(columnIndex) == false) {
                    aliasCommand = (String) SORT_ALIASES
                            .get(ISortEventCapability.SORT_SERVER);

                    if (aliasCommand != null) {
                        aliasCommand = tableContext
                                .translateJavascriptMethod(aliasCommand);
                    }
                }

                IJavaScriptWriter jsWriter = objectWriter
                        .writeSymbol("_sorter");

                String delimiters = " (),;:";
                StringTokenizer st = new StringTokenizer(aliasCommand,
                        delimiters, true);
                if (st.countTokens() == 1
                        && delimiters.indexOf(st.nextToken()) < 0) {
                    jsWriter.write(aliasCommand);

                } else {
                    jsWriter.writeString(aliasCommand);
                }

            } else if (sort instanceof IScriptListener) {
                IScriptListener scriptListener = (IScriptListener) sort;

                IJavaScriptWriter jsWriter = objectWriter
                        .writeSymbol("_sorter");

                EventsRenderer.encodeJavaScriptCommmand(jsWriter,
                        scriptListener);

            } else if (sort instanceof IServerActionListener) {
                // Le tri se fait cot� serveur !

                String aliasCommand = (String) SORT_ALIASES
                        .get(ISortEventCapability.SORT_SERVER);

                objectWriter.writeSymbol("_sorter").writeString(aliasCommand);
            }
        }

    }

    protected void encodeBodyBegin(IHtmlWriter htmlWriter,
            AbstractGridRenderContext data) throws WriterException {

        encoreBodyTableEnd(htmlWriter, data);

        super.encodeBodyBegin(htmlWriter, data);
    }

    protected void writeFullStates(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext context, String jsCommand, Set objects)
            throws WriterException {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        DataGridRenderContext dataGridRenderContext = (DataGridRenderContext) context;

        FacesContext facesContext = jsWriter.getFacesContext();
        DataColumnComponent dataColumnComponent = dataGridRenderContext
                .getRowValueColumn();

        jsWriter.writeMethodCall(jsCommand).write('[');
        int i = 0;
        for (Iterator it = objects.iterator(); it.hasNext();) {
            Object value = it.next();

            String convert = convertValue(facesContext, dataColumnComponent,
                    value);

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
            for (IDataColumnIterator it = dataGridComponent.listDataColumns(); it
                    .hasNext();) {
                DataColumnComponent column = it.next();
                if (rowValueColumnId.equals(column.getId()) == false) {
                    continue;
                }

                return column;
            }
        }

        return null;
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, false);
    }

    protected void encodeJsColumns(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext, boolean ignoreUI)
            throws WriterException {

        super.encodeJsColumns(jsWriter, tableContext, ignoreUI);

        UIColumn rowValueColumn = ((DataGridRenderContext) tableContext)
                .getRowValueColumn();
        if (rowValueColumn != null) {
            UIColumn columns[] = tableContext.listColumns();

            for (int i = 0; i < columns.length; i++) {
                UIColumn columnComponent = columns[i];

                if (rowValueColumn == columnComponent) {
                    jsWriter.writeMethodCall("f_setRowValueColumn").write(
                            String.valueOf(i)).writeln(");");

                    break;
                }
            }
        }
    }

    protected void encodeJsBody(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext tableContext) throws WriterException {

        super.encodeJsBody(htmlWriter, tableContext);

        IJavaScriptRenderContext javascriptRenderContext = ((IHtmlRenderContext) htmlWriter
                .getHtmlComponentRenderContext().getRenderContext())
                .getJavaScriptRenderContext();

        String rowVarName = javascriptRenderContext.allocateVarName();
        tableContext.setRowVarName(rowVarName);

        encodeJsTransactionalRows(htmlWriter,
                (DataGridRenderContext) tableContext, true);
    }

    public void encodeJsTransactionalRows(IJavaScriptWriter htmlWriter,
            DataGridRenderContext tableContext, boolean sendFullStates)
            throws WriterException {

        DataModel dataModel = tableContext.getDataModel();

        if ((dataModel instanceof ITransactionalDataModel) == false) {
            encodeJsRows(htmlWriter, tableContext, sendFullStates);
            return;
        }

        ITransactionalDataModel transactionalDataModel = (ITransactionalDataModel) dataModel;

        try {
            transactionalDataModel.enableTransactionalObjects(true);

            encodeJsRows(htmlWriter, tableContext, sendFullStates);

            return;

        } finally {
            transactionalDataModel.enableTransactionalObjects(false);
        }
    }

    private void encodeJsRows(IJavaScriptWriter jsWriter,
            DataGridRenderContext tableContext, boolean sendFullStates)
            throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();

        DataGridComponent dataGridComponent = tableContext
                .getDataGridComponent();
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
                        dataGridComponent, sortedComponents);
            } else {
                // Il faut faire le tri à la main !

                sortTranslations = DataGridServerSort.computeSortedTranslation(
                        jsWriter.getFacesContext(), dataGridComponent,
                        dataModel, sortedComponents);
            }

            // Apres le tri, on connait peu etre la taille
            tableContext.updateRowCount();
        } else {

            if (dataModel instanceof ISortedDataModel) {
                // Reset des parametres de tri !
                ((ISortedDataModel) dataModel).setSortParameters(
                        dataGridComponent, null);
            }
        }

        int rowIndex = tableContext.getFirst();

        int selectedIndexes[] = null;
        int selectedOffset = -1;
        Set selectedObjects = null;

        if (tableContext.isSelectable()
                && (tableContext.isClientSelectionFullState() == false || sendFullStates)) {

            Object selectionModel = dataGridComponent
                    .getSelectedValues(facesContext);

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
                    selectedObjects = ValuesTools
                            .convertSelection(selectionModel);

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

            Object checkModel = dataGridComponent
                    .getCheckedValues(facesContext);
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
                    checkedObjects = ValuesTools.convertSelection(checkModel);

                    if (tableContext.isClientCheckFullState()) {
                        writeFullStates(jsWriter, tableContext,
                                "f_setCheckStates", checkedObjects);
                        checkedObjects = null;
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
            String rowId = null;

            DataColumnComponent rowValueColumn = (DataColumnComponent) tableContext
                    .getRowValueColumn();

            int rowValueColumnIndex = -1;
            if (designerMode && rowValueColumn != null) {
                for (int i = 0; i < columns.length; i++) {
                    DataColumnComponent dataColumnComponent = (DataColumnComponent) columns[i];
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
                    }
                }

                dataGridComponent.setRowIndex(translatedRowIndex);
                if (dataGridComponent.isRowAvailable() == false) {
                    count = rowIndex;
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(i));
                }

                if (rowValueColumn != null) {
                    Object value;

                    if (designerMode) {
                        String sd[] = (String[]) dataGridComponent.getRowData();
                        if (sd != null && sd.length > rowValueColumnIndex) {
                            value = sd[rowValueColumnIndex];

                        } else {
                            value = String.valueOf(i);
                        }

                        rowId = (String) value;

                    } else {
                        value = rowValueColumn.getValue(facesContext);

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
                    }

                    if (rowId == null) {
                        throw new FacesException("Value of row #" + rowIndex
                                + " is null !");
                    }
                }

                if (searchEnd) {
                    // On teste juste la validité de la fin !
                    if (rows > 0 && i >= rows) {
                        break;
                    }
                }

                encodeJsRow(jsWriter, tableContext, i, rowId,
                        translatedRowIndex, selected, checked);

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
                }

                rowIndex++;
            }

        } finally {
            dataGridComponent.setRowIndex(-1);

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
        if (rows > 0) {
            if (count > firstCount
                    || (dataGridComponent.getFirst() == 0 && count == 0)) {
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

    protected void encodeJsRowCount(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext tableContext, int count)
            throws WriterException {
        htmlWriter.writeMethodCall("f_setRowCount").writeInt(count).writeln(
                ");");
    }

    protected void encodeJsRow(IJavaScriptWriter jsWriter,
            DataGridRenderContext tableContext, int index, String rowId,
            int iRowId, boolean selected, boolean checked)
            throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();
        DataGridComponent dataGridComponent = tableContext
                .getDataGridComponent();
        UIColumn dcs[] = tableContext.listColumns();
        int columnNumber = dcs.length;

        String trClassName=null; // Pas d'évaluation pour chaque ligne 
        
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

            if (trClassName != null) {
                objectLiteralWriter.writeSymbol("_styleClass").write(
                        trClassName);
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
            DataColumnComponent dc = (DataColumnComponent) dcs[i];

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

                } else {
                    Object value = dc.getValue(facesContext);

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
                String imageURL = dc.getCellImageURL(facesContext);
                if (imageURL != null) {
                    if (images == null) {
                        images = new String[columnNumber];
                    }
                    images[i] = imageURL;
                }
            }

            if (tableContext.isCellStyleClass(i)) {
                String cs = dc.getCellStyleClass(facesContext);
                if (cs != null) {
                    if (cellStyleClasses == null) {
                        cellStyleClasses = new String[columnNumber];
                    }

                    cellStyleClasses[i] = cs;
                }
            }

            if (tableContext.isCellToolTipText(i)) {
                String ct = dc.getCellToolTipText(facesContext);
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
            IScriptRenderContext scriptRenderContext, DataGridComponent dg,
            int rowIndex, int forcedRows, ISortedComponent sortedComponents[],
            String filterExpression) {
        DataGridRenderContext tableContext = new DataGridRenderContext(
                processContext, scriptRenderContext, dg, rowIndex, forcedRows,
                sortedComponents, filterExpression);

        return tableContext;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class DataGridRenderContext extends AbstractGridRenderContext {

        private static final String REVISION = "$Revision$";

        private DataColumnComponent rowValueColumn;

        public DataGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext, DataGridComponent dg,
                int rowIndex, int forcedRows,
                ISortedComponent[] sortedComponents, String filterExpression) {
            super(processContext, scriptRenderContext, dg, rowIndex,
                    forcedRows, sortedComponents, filterExpression);
        }

        public DataGridRenderContext(
                IHtmlComponentRenderContext componentRenderContext) {
            super(componentRenderContext);
        }

        protected void initialize(boolean checkTitleImages) {
            super.initialize(checkTitleImages);

            String rowValueColumnId = ((DataGridComponent) gridComponent)
                    .getRowValueColumnId();

            for (int i = 0; i < columns.length; i++) {
                UIColumn column = columns[i];

                if (rowValueColumnId != null && rowValueColumn == null) {
                    if (rowValueColumnId.equals(column.getId())) {
                        rowValueColumn = (DataColumnComponent) column;
                    }
                }
            }

            if (rowValueColumnId != null && rowValueColumn == null) {
                throw new FacesException("Can not find column '"
                        + rowValueColumnId
                        + "' specified by 'rowValueColumnId' attribute.");
            }
        }

        public DataGridComponent getDataGridComponent() {
            return (DataGridComponent) getGridComponent();
        }

        protected String convertAliasCommand(String command) {
            return (String) SORT_ALIASES.get(command);
        }

        public DataColumnComponent getRowValueColumn() {
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
                htmlWriter.writeAttribute("v:asyncRender", "true");
            }
        }

        if (dg instanceof ICursorProvider) {

            Object cursorValue = ((ICursorProvider) dg).getCursorValue();
            String clientCursorValue = null;

            if (cursorValue instanceof String) {
                clientCursorValue = (String) cursorValue;

            } else if (cursorValue != null) {
                DataColumnComponent rowValueColumn = ((DataGridRenderContext) tableContext)
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

    }
}
