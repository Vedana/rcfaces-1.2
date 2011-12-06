/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataModelWrapper extends DataModel {

	private DataModel dataModel;

	protected void setWrappedDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	public void addDataModelListener(DataModelListener listener) {
		dataModel.addDataModelListener(listener);
	}

	public DataModelListener[] getDataModelListeners() {
		return dataModel.getDataModelListeners();
	}

	public int getRowCount() {
		return dataModel.getRowCount();
	}

	public Object getRowData() {
		return dataModel.getRowData();
	}

	public int getRowIndex() {
		return dataModel.getRowIndex();
	}

	public Object getWrappedData() {
		return dataModel.getWrappedData();
	}

	public boolean isRowAvailable() {
		return dataModel.isRowAvailable();
	}

	public void removeDataModelListener(DataModelListener listener) {
		dataModel.removeDataModelListener(listener);
	}

	public void setRowIndex(int rowIndex) {
		dataModel.setRowIndex(rowIndex);
	}

	public void setWrappedData(Object data) {
		dataModel.setWrappedData(data);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataModel == null) ? 0 : dataModel.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DataModelWrapper other = (DataModelWrapper) obj;
		if (dataModel == null) {
			if (other.dataModel != null)
				return false;
		} else if (!dataModel.equals(other.dataModel))
			return false;
		return true;
	}

}
