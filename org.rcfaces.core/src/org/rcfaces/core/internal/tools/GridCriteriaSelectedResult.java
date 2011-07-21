package org.rcfaces.core.internal.tools;

import java.util.List;
import java.util.Map;

import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.model.ISelectedCriteria;

/**
 * 
 * @author Olivier Oeuillot
 */
public class GridCriteriaSelectedResult extends AbstractCriteriaSelectedResult {

	public GridCriteriaSelectedResult(IGridComponent gridComponent,
			ISelectedCriteria[] configs) {
		super((ICriteriaManagerCapability) gridComponent, configs);
	}

	@Override
	protected void fillDatas(List<?> result,
			Map<ICriteriaConfiguration, CriteriaItem[]> criteriaItemsByContainer) {

		IGridComponent gridComponent = (IGridComponent) getCriteriaManager();

	}
	/*
	 * private void encodeJsRows(IGridComponent gridComponent) throws
	 * WriterException {
	 * 
	 * DataModel dataModel = tableContext.getDataModel();
	 * 
	 * boolean filtred = false; int firstRowCount = tableContext.getRowCount();
	 * 
	 * IComponentRefModel componentRefModel = (IComponentRefModel) getAdapter(
	 * IComponentRefModel.class, dataModel);
	 * 
	 * if (componentRefModel != null) {
	 * componentRefModel.setComponent((UIComponent) gridComponent); }
	 * 
	 * IFilterProperties filtersMap = tableContext.getFiltersMap();
	 * IFiltredModel filtredDataModel = (IFiltredModel) getAdapter(
	 * IFiltredModel.class, dataModel); if (filtersMap != null) { if
	 * (filtredDataModel != null) {
	 * 
	 * filtredDataModel.setFilter(filtersMap); tableContext.updateRowCount();
	 * 
	 * filtred = true;
	 * 
	 * } else { dataModel = FilteredDataModel.filter(dataModel, filtersMap);
	 * tableContext.updateRowCount(); }
	 * 
	 * } else if (filtredDataModel != null) {
	 * 
	 * filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
	 * tableContext.updateRowCount();
	 * 
	 * filtred = true; }
	 * 
	 * int rows = tableContext.getForcedRows(); if (rows < 1) { rows =
	 * tableContext.getRows(); }
	 * 
	 * boolean searchEnd = (rows > 0); // int firstCount = -1; int count = -1;
	 * 
	 * if (searchEnd) { count = firstRowCount; }
	 * 
	 * int sortTranslations[] = null;
	 * 
	 * ISortedComponent sortedComponents[] = tableContext
	 * .listSortedComponents(); ISortedDataModel sortedDataModel =
	 * (ISortedDataModel) getAdapter( ISortedDataModel.class, dataModel,
	 * sortedComponents); if (sortedComponents != null &&
	 * sortedComponents.length > 0) {
	 * 
	 * if (sortedDataModel != null) { // On delegue au modele, le tri !
	 * 
	 * // Nous devons être OBLIGATOIREMENT en mode rowValueColumnId if
	 * (tableContext.getRowValueColumn() == null) { throw new FacesException(
	 * "Can not sort dataModel without attribute rowValueColumnId specified !");
	 * }
	 * 
	 * sortedDataModel.setSortParameters((UIComponent) gridComponent,
	 * sortedComponents); } else { // Il faut faire le tri à la main !
	 * 
	 * sortTranslations = GridServerSort.computeSortedTranslation( facesContext,
	 * gridComponent, dataModel, sortedComponents); }
	 * 
	 * // Apres le tri, on connait peu etre la taille
	 * tableContext.updateRowCount(); } else {
	 * 
	 * if (sortedDataModel != null) { // Reset des parametres de tri !
	 * sortedDataModel.setSortParameters((UIComponent) gridComponent, null); } }
	 * 
	 * int rowIndex = tableContext.getFirst();
	 * 
	 * // Initializer le IRandgeDataModel avant la selection/check/additionnal
	 * // informations ! if (sortTranslations == null && rows > 0 && ((dataModel
	 * instanceof IRangeDataModel) || (dataModel instanceof IRangeDataModel2)))
	 * { // Specifie le range que si il n'y a pas de tri !
	 * 
	 * int rangeLength = rows; if (searchEnd) { // On regardera si il y a bien
	 * une suite ... rangeLength++; }
	 * 
	 * if (LOG.isDebugEnabled()) { LOG.debug("Encode set range rowIndex='" +
	 * rowIndex + "' rangeLength='" + rangeLength + "'."); }
	 * 
	 * if (dataModel instanceof IRangeDataModel) { ((IRangeDataModel) dataModel)
	 * .setRowRange(rowIndex, rangeLength); }
	 * 
	 * if (dataModel instanceof IRangeDataModel2) { ((IRangeDataModel2)
	 * dataModel).setRowRange(rowIndex, rangeLength, searchEnd); } }
	 * 
	 * 
	 * UIColumn columns[] = tableContext.listColumns(); boolean testImageUrls[]
	 * = new boolean[columns.length]; for (int i = 0; i < columns.length; i++) {
	 * UIColumn column = columns[i];
	 * 
	 * if (column instanceof ICellImageSettings) { testImageUrls[i] =
	 * ((ICellImageSettings) column) .isCellImageURLSetted(); } }
	 * 
	 * Map varContext = facesContext.getExternalContext().getRequestMap();
	 * String rowCountVar = tableContext.getRowCountVar(); if (rowCountVar !=
	 * null) { varContext.put(rowCountVar, new Integer(count)); }
	 * 
	 * String rowIndexVar = tableContext.getRowIndexVar();
	 * 
	 * boolean designerMode = tableContext.isDesignerMode();
	 * 
	 * if (LOG.isTraceEnabled()) { LOG.trace("Encode grid componentId='" +
	 * ((UIComponent) gridComponent).getId() + "' rowIndexVar='" + rowIndexVar +
	 * "' designerMode='" + designerMode + "' rowCountVar='" + rowCountVar +
	 * "' searchEnd=" + searchEnd + " count=" + count + " rows='" + rows +
	 * "'."); }
	 * 
	 * try { String rowId = null;
	 * 
	 * int rowValueColumnIndex = -1; if (designerMode && rowValueColumn != null)
	 * { for (int i = 0; i < columns.length; i++) { UIColumn dataColumnComponent
	 * = columns[i]; if (dataColumnComponent != rowValueColumn) { continue; }
	 * 
	 * rowValueColumnIndex = i; break; }
	 * 
	 * }
	 * 
	 * for (int i = 0;; i++) { if (searchEnd == false) { // Pas de recherche de
	 * la fin ! // On peut sortir tout de suite ... if (rows > 0 && i >= rows) {
	 * break; } }
	 * 
	 * int translatedRowIndex = rowIndex;
	 * 
	 * if (rowValueColumn != null) { if (sortTranslations != null) { if
	 * (rowIndex >= sortTranslations.length) { break; }
	 * 
	 * translatedRowIndex = sortTranslations[rowIndex]; }
	 * 
	 * } else {
	 * 
	 * if (sortTranslations == null) { if (selectedOffset >= 0) { if
	 * (selectedIndexes[selectedOffset] == rowIndex) { selected = true;
	 * 
	 * selectedOffset++;
	 * 
	 * } else { selected = false; } } if (checkedOffset >= 0) { if
	 * (checkedIndexes[checkedOffset] == rowIndex) { checked = true;
	 * 
	 * checkedOffset++;
	 * 
	 * } else { checked = false; } } if (additionalOffset >= 0) { if
	 * (additionalIndexes[additionalOffset] == rowIndex) { additional = true;
	 * 
	 * additionalOffset++;
	 * 
	 * } else { additional = false; } } } else { if (rowIndex >=
	 * sortTranslations.length) { break; }
	 * 
	 * translatedRowIndex = sortTranslations[rowIndex];
	 * 
	 * if (selectedIndexes != null) { selected = false;
	 * 
	 * for (int j = 0; j < selectedIndexes.length; j++) { if (selectedIndexes[j]
	 * != translatedRowIndex) { continue; }
	 * 
	 * selected = true; break; } } if (checkedIndexes != null) { checked =
	 * false; for (int j = 0; j < checkedIndexes.length; j++) { if
	 * (checkedIndexes[j] != translatedRowIndex) { continue; }
	 * 
	 * checked = true; break; } } if (additionalIndexes != null) { additional =
	 * false; for (int j = 0; j < additionalIndexes.length; j++) { if
	 * (additionalIndexes[j] != translatedRowIndex) { continue; }
	 * 
	 * additional = true; break; } } } }
	 * 
	 * gridComponent.setRowIndex(translatedRowIndex); boolean available =
	 * gridComponent.isRowAvailable(); if (LOG.isDebugEnabled()) {
	 * LOG.debug("Set row index " + translatedRowIndex + " returns " + available
	 * + " (rowIndexVar=" + rowIndexVar + ")"); }
	 * 
	 * if (available == false) { count = rowIndex; break; }
	 * 
	 * if (searchEnd) { // On teste juste la validité de la fin ! if (rows > 0
	 * && i >= rows) { break; } }
	 * 
	 * if (rowIndexVar != null) { varContext.put(rowIndexVar, new Integer(i)); }
	 * 
	 * if (rowValueColumn != null) { Object value;
	 * 
	 * if (designerMode) { String sd[] = (String[]) gridComponent.getRowData();
	 * if (sd != null && sd.length > rowValueColumnIndex) { value =
	 * sd[rowValueColumnIndex];
	 * 
	 * } else { value = String.valueOf(i); }
	 * 
	 * rowId = (String) value;
	 * 
	 * } else { value = ((ValueHolder) rowValueColumn).getValue();
	 * 
	 * rowId = convertValue(facesContext, rowValueColumn, value); }
	 * 
	 * if (value != null) { if (checkedObjects != null) { checked =
	 * checkedObjects.contains(value); } if (selectedObjects != null) { selected
	 * = selectedObjects.contains(value); } if (additionalObjects != null) {
	 * additional = additionalObjects.contains(value); } }
	 * 
	 * if (rowId == null) { throw new FacesException(
	 * "Value associated to the row at index " + rowIndex + " is null !"); } }
	 * 
	 * encodeJsRow(jsWriter, tableContext, i, rowId, rowIndex, selected,
	 * checked, additional, translatedRowIndex);
	 * 
	 * if (sortTranslations == null) { if (selectedOffset >= 0 && selectedOffset
	 * >= selectedIndexes.length) { selectedOffset = -1; selected = false; } if
	 * (checkedOffset >= 0 && checkedOffset >= checkedIndexes.length) {
	 * checkedOffset = -1; checked = false; } if (additionalOffset >= 0 &&
	 * additionalOffset >= additionalIndexes.length) { additionalOffset = -1;
	 * additional = false; } }
	 * 
	 * rowIndex++; }
	 * 
	 * } finally { gridComponent.setRowIndex(-1);
	 * 
	 * if (rowCountVar != null) { varContext.remove(rowCountVar); }
	 * 
	 * if (rowIndexVar != null) { varContext.remove(rowIndexVar); }
	 * 
	 * jsWriter.writeMethodCall("f_postAddRow2").writeln(");"); }
	 * 
	 * // Le count a évolué ? // 2 solutions: // * mode page par page, nous
	 * sommes à la fin, ou il y a eu un tri // * en mode liste, le dataModel ne
	 * pouvait pas encore donner le nombre // de rows
	 * 
	 * if (unknownRowCount && firstRowCount >= 0) { encodeJsRowCount(jsWriter,
	 * tableContext, count);
	 * 
	 * } else if (rows > 0) { if (count > firstRowCount ||
	 * (gridComponent.getFirst() == 0 && count == 0)) {
	 * encodeJsRowCount(jsWriter, tableContext, count); }
	 * 
	 * } else if (tableContext.getRowCount() < 0) { encodeJsRowCount(jsWriter,
	 * tableContext, rowIndex);
	 * 
	 * } else if (filtred) { if (searchEnd && count == 0) {
	 * encodeJsRowCount(jsWriter, tableContext, count); } } }
	 */

}
