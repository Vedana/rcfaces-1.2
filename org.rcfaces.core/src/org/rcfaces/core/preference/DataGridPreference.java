/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.core.preference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataGridPreference extends AbstractComponentPreference {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -1760014871350310345L;

    private static final int SAVE_COLUMNS_ORDER = 0x0001;

    private static final int SAVE_SORTED_COLUMN_IDS = 0x0002;

    private static final int SAVE_COLUMN_SIZES = 0x0004;

    private static final int SAVE_FILTER_PROPERTIES = 0x0008;

    private static final int SAVE_POSITION = 0x0010;

    private int saveMask = SAVE_COLUMN_SIZES | SAVE_SORTED_COLUMN_IDS
            | SAVE_COLUMN_SIZES;

    private String columnsOrder;

    private String sortedColumnIds;

    private int position = -1;

    private Map columnSizes;

    private IFilterProperties filterProperties;

    // Constructeur public pour le StateHolder !
    public DataGridPreference() {
    }

    public void loadPreference(FacesContext facesContext, UIComponent component) {
        if ((component instanceof DataGridComponent) == false) {
            throw new FacesException("Can not load dataGrid preferences !");
        }

        DataGridComponent dataGridComponent = (DataGridComponent) component;

        if (columnsOrder != null) {
            dataGridComponent.setColumnsOrder(columnsOrder);
        }

        if (sortedColumnIds != null) {
            dataGridComponent.setSortedColumnIds(sortedColumnIds);
        }

        if (columnSizes != null) {
            IDataColumnIterator dataColumnIterator = dataGridComponent
                    .listColumns();

            if (dataColumnIterator.count() > 0) {
                Map cols = new HashMap(dataColumnIterator.count());
                for (; dataColumnIterator.hasNext();) {
                    DataColumnComponent columnComponent = dataColumnIterator
                            .next();

                    String columnId = columnComponent.getId();
                    if (columnId == null) {
                        continue;
                    }

                    cols.put(columnId, columnComponent);
                }

                for (Iterator it = columnSizes.entrySet().iterator(); it
                        .hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String columnId = (String) entry.getKey();
                    String columnWidth = (String) entry.getValue();

                    DataColumnComponent columnComponent = (DataColumnComponent) cols
                            .get(columnId);
                    if (columnId == null) {
                        continue;
                    }

                    columnComponent.setWidth(columnWidth);
                }
            }
        }

        if (filterProperties != null) {
            dataGridComponent.setFilterProperties(filterProperties);
        }

        if (position >= 0) {
            dataGridComponent.setFirst(position);
        }
    }

    public void savePreference(FacesContext facesContext, UIComponent component) {
        if ((component instanceof DataGridComponent) == false) {
            throw new FacesException("Can not save dataGrid preferences !");
        }

        DataGridComponent dataGridComponent = (DataGridComponent) component;

        if (isSaveColumnsOrder()) {
            columnsOrder = dataGridComponent.getColumnsOrder(facesContext);
        }

        if (isSaveSortedColumnIds()) {
            sortedColumnIds = dataGridComponent
                    .getSortedColumnIds(facesContext);
        }

        if (isSaveColumnSizes()) {
            columnSizes = null;

            IDataColumnIterator dataColumnIterator = dataGridComponent
                    .listColumns();

            if (dataColumnIterator.count() > 0) {
                columnSizes = new HashMap(dataColumnIterator.count());
                for (; dataColumnIterator.hasNext();) {
                    DataColumnComponent columnComponent = dataColumnIterator
                            .next();

                    String columnId = columnComponent.getId();
                    if (columnId == null) {
                        continue;
                    }

                    String columnWidth = columnComponent.getWidth(facesContext);
                    if (columnWidth == null) {
                        continue;
                    }

                    columnSizes.put(columnId, columnWidth);
                }
            }
        }

        if (isSaveFilterProperties()) {
            filterProperties = dataGridComponent
                    .getFilterProperties(facesContext);
        }

        if (isSavePosition()) {
            position = dataGridComponent.getFirst();
        }
    }

    public Object saveState(FacesContext context) {
        return new Object[] { new Integer(saveMask), columnsOrder,
                sortedColumnIds, columnSizes, filterProperties,
                new Integer(position) };
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;

        saveMask = ((Integer) values[0]).intValue();
        columnsOrder = (String) values[1];
        sortedColumnIds = (String) values[2];
        columnSizes = (Map) values[3];
        filterProperties = (IFilterProperties) values[4];
        position = ((Integer) values[5]).intValue();
    }

    public final boolean isSaveColumnSizes() {
        return (saveMask & SAVE_COLUMN_SIZES) > 0;
    }

    public final void setSaveColumnSizes(boolean saveColumnSizes) {
        saveMask = (saveMask & ~SAVE_COLUMN_SIZES);
        saveMask |= (saveColumnSizes) ? SAVE_COLUMN_SIZES : 0;

        columnSizes = null;
    }

    public final boolean isSaveColumnsOrder() {
        return (saveMask & SAVE_COLUMNS_ORDER) > 0;
    }

    public final void setSaveColumnsOrder(boolean saveColumnsOrder) {
        saveMask = (saveMask & ~SAVE_COLUMNS_ORDER);
        saveMask |= (saveColumnsOrder) ? SAVE_COLUMNS_ORDER : 0;

        columnsOrder = null;
    }

    public final boolean isSaveSortedColumnIds() {
        return (saveMask & SAVE_SORTED_COLUMN_IDS) > 0;
    }

    public final void setSaveSortedColumnIds(boolean saveSortedColumnIds) {
        saveMask = (saveMask & ~SAVE_SORTED_COLUMN_IDS);
        saveMask |= (saveSortedColumnIds) ? SAVE_SORTED_COLUMN_IDS : 0;

        sortedColumnIds = null;
    }

    public final boolean isSaveFilterProperties() {
        return (saveMask & SAVE_FILTER_PROPERTIES) > 0;
    }

    public final void setSaveFilterProperties(boolean saveFilterProperties) {
        saveMask = (saveMask & ~SAVE_FILTER_PROPERTIES);
        saveMask |= (saveFilterProperties) ? SAVE_FILTER_PROPERTIES : 0;

        filterProperties = null;
    }

    public final boolean isSavePosition() {
        return (saveMask & SAVE_POSITION) > 0;
    }

    public final void setSavePosition(boolean savePosition) {
        saveMask = (saveMask & ~SAVE_POSITION);
        saveMask |= (savePosition) ? SAVE_POSITION : 0;

        position = -1;
    }

}
