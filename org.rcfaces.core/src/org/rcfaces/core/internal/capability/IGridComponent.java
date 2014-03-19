/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.faces.model.DataModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGridComponent extends IColumnsContainer {

    /**
     * Returns a DataModel if the value of the component is a DataModel
     */
    DataModel getDataModelValue();

    void setRowIndex(int index);

    Object getRowData();

    int getRowCount();

    int getRows();

    int getFirst();

    String getVar();

    String getRowCountVar();

    String getRowIndexVar();

    void setFirst(int position);

    boolean isRowAvailable();
}
