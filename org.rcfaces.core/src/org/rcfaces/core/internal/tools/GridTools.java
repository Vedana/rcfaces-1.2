/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsColumnComponent;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.iterator.IComponentsColumnIterator;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.lang.provider.ISelectionProvider;
import org.rcfaces.core.model.DefaultSortedComponent;
import org.rcfaces.core.model.IDataModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.ISortedComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GridTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(GridTools.class);

    public static final int SELECTION_VALUES_TYPE = 1;

    public static final int CHECK_VALUES_TYPE = 2;

    private static final IDataColumnIterator EMPTY_DATA_COLUMN_ITERATOR = new DataColumnListIterator(
            Collections.EMPTY_LIST);

    private static final IColumnIterator EMPTY_COLUMNS_ITERATOR = new ColumnListIterator(
            Collections.EMPTY_LIST);

    private static final IComponentsColumnIterator EMPTY_COMPONENTS_COLUMN_ITERATOR = new ComponentsColumnListIterator(
            Collections.EMPTY_LIST);

    private static final String ALL_INDEX = "all";

    private static final ISortedComponent[] SORTED_COMPONENTS_EMPTY_ARRAY = new ISortedComponent[0];

    private static final boolean SORT_INDICES = true;

    private static final UIColumn[] COLUMN_EMPTY_ARRAY = new UIColumn[0];

    public static IColumnIterator listColumns(IGridComponent component,
            Class filter) {
        List list = ComponentIterators.list((UIComponent) component, filter);
        if (list.isEmpty()) {
            return EMPTY_COLUMNS_ITERATOR;
        }

        return new ColumnListIterator(list);
    }

    public static IDataColumnIterator listDataColumns(IGridComponent component) {
        List list = ComponentIterators.list((UIComponent) component,
                DataColumnComponent.class);
        if (list.isEmpty()) {
            return EMPTY_DATA_COLUMN_ITERATOR;
        }

        return new DataColumnListIterator(list);
    }

    public static IComponentsColumnIterator listComponentsColumns(
            IGridComponent component) {
        List list = ComponentIterators.list((UIComponent) component,
                ComponentsColumnComponent.class);
        if (list.isEmpty()) {
            return EMPTY_COMPONENTS_COLUMN_ITERATOR;
        }

        return new ComponentsColumnListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class DataColumnListIterator extends
            ComponentIterators.ComponentListIterator implements
            IDataColumnIterator {
        private static final String REVISION = "$Revision$";

        public DataColumnListIterator(List list) {
            super(list);
        }

        public final DataColumnComponent next() {
            return (DataColumnComponent) nextComponent();
        }

        public DataColumnComponent[] toArray() {
            return (DataColumnComponent[]) toArray(new DataColumnComponent[count()]);
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ComponentsColumnListIterator extends
            ComponentIterators.ComponentListIterator implements
            IComponentsColumnIterator {
        private static final String REVISION = "$Revision$";

        public ComponentsColumnListIterator(List list) {
            super(list);
        }

        public final ComponentsColumnComponent next() {
            return (ComponentsColumnComponent) nextComponent();
        }

        public ComponentsColumnComponent[] toArray() {
            return (ComponentsColumnComponent[]) toArray(new ComponentsColumnComponent[count()]);
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ColumnListIterator extends
            ComponentIterators.ComponentListIterator implements IColumnIterator {
        private static final String REVISION = "$Revision$";

        public ColumnListIterator(List list) {
            super(list);
        }

        public final UIColumn next() {
            return (UIColumn) nextComponent();
        }

        public UIColumn[] toArray() {
            return (UIColumn[]) toArray(new UIColumn[count()]);
        }

    }

    /*
     * public static final IIndexesModel getIndexesModel(Object object,
     * DataModel dataModel) { if (object == null) { return new
     * ArrayIndexesModel(); }
     * 
     * if (object instanceof IIndexesModel) { return (IIndexesModel) object; }
     * 
     * if (ALL_INDEX.equals(object)) { Object value = dataModel;
     * 
     * if (value == null) { return new ArrayIndexesModel(); }
     * 
     * Class valueClass = value.getClass(); if (valueClass.isArray()) { int size =
     * Array.getLength(value);
     * 
     * return IndexesModels.selectAll(size); }
     * 
     * if (value instanceof Collection) { Collection collection = (Collection)
     * value;
     * 
     * return IndexesModels.selectAll(collection.size()); }
     * 
     * if (value instanceof Map) { Map collection = (Map) value;
     * 
     * return IndexesModels.selectAll(collection.size()); }
     * 
     * throw new FacesException( "'all' keyword for index model, does not
     * support value type: '" + value.getClass() + "'."); }
     * 
     * if (object instanceof Collection) { return new
     * CollectionIndexesModel((Collection) object); }
     * 
     * if (object instanceof Map) { return new MapIndexesModel((Map) object); }
     * 
     * List l = new ArrayList(1); l.add(object);
     * 
     * return new CollectionIndexesModel(l); }
     */

    public static ISortedComponent[] listSortedComponents(FacesContext context,
            ISortedChildrenCapability dataGridComponent) {

        UIComponent columns[] = dataGridComponent.getSortedChildren();
        if (columns.length < 1) {
            return SORTED_COMPONENTS_EMPTY_ARRAY;
        }

        List l = new ArrayList(columns.length);
        for (int i = 0; i < columns.length; i++) {

            UIColumn column = (UIColumn) columns[i];

            if ((column instanceof IOrderCapability) == false) {
                continue;
            }

            l.add(new DefaultSortedComponent(column, i,
                    ((IOrderCapability) column).isAscending()));

        }

        return (ISortedComponent[]) l.toArray(new ISortedComponent[l.size()]);
    }

    public static DataModel getDataModel(Object current, UIComponent component,
            FacesContext facesContext) {

        if (current == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: null value type '" + current
                        + "' for component '" + component.getId()
                        + ", return a ListDataModel.");
            }

            return new ListDataModel(Collections.EMPTY_LIST);
        }

        if (current instanceof DataModel) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: DataModel value type '"
                        + current + "' for component '" + component.getId()
                        + ", return the value.");
            }

            return (DataModel) current;
        }

        if (current instanceof List) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: List value type '" + current
                        + "' for component '" + component.getId()
                        + ", return a ListDataModel.");
            }

            return new ListDataModel((List) current);
        }

        if (Object[].class.isAssignableFrom(current.getClass())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: Object array value type '"
                        + current + "' for component '" + component.getId()
                        + ", return an ArrayDataModel.");
            }

            return new ArrayDataModel((Object[]) current);
        }

        if (current instanceof ResultSet) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: ResultSet value type '"
                        + current + "' for component '" + component.getId()
                        + ", return a ResultSetDataModel.");
            }

            return new ResultSetDataModel((ResultSet) current);
        }

        if (current instanceof Result) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: Result value type '" + current
                        + "' for component '" + component.getId()
                        + ", return a ResultDataModel.");
            }

            return new ResultDataModel((Result) current);
        }

        if (Constants.COLLECTION_DATAMODEL_SUPPORT) {
            if (current instanceof Collection) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("DataModel conversion: Collection value type '"
                            + current + "' for component '" + component.getId()
                            + ", return an ArrayDataModel.");
                }

                return new ArrayDataModel(((Collection) current).toArray());
            }
        }

        if (current instanceof IDataModel) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("DataModel conversion: IDataModel value type '"
                        + current + "' for component '" + component.getId()
                        + ", return a DataModelWrapper.");
            }

            return new DataModelWrapper((IDataModel) current);
        }

        if (Constants.ADAPTABLE_DATAMODEL_SUPPORT) {
            if (current instanceof IAdaptable) {
                DataModel dataModel = (DataModel) ((IAdaptable) current)
                        .getAdapter(DataModel.class, component);
                if (dataModel != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("DataModel conversion: IAdaptable value type '"
                                        + current
                                        + "' for component '"
                                        + component.getId()
                                        + ", return a DataModel.");
                    }

                    return dataModel;
                }
            }

            RcfacesContext rcfacesContext = RcfacesContext
                    .getInstance(facesContext);

            DataModel dataModel = (DataModel) rcfacesContext
                    .getAdapterManager().getAdapter(current, DataModel.class,
                            component);

            if (dataModel != null) {
                if (LOG.isDebugEnabled()) {
                    LOG
                            .debug("DataModel conversion: AdaptableFactory response for type '"
                                    + current
                                    + "' for component '"
                                    + component.getId()
                                    + ", return a DataModel.");
                }

                return dataModel;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("DataModel conversion: Unknown value type '" + current
                    + "' for component '" + component.getId()
                    + ", return a ScalarDataModel.");
        }

        return new ScalarDataModel(current);
    }

    public static void select(FacesContext facesContext,
            ISelectionProvider component, Object rowValue) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            selectedValues = new ArrayIndexesModel();
            component.setSelectedValues(selectedValues);
        }

        if (selectedValues instanceof IIndexesModel) {
            // Il nous faut l'index ...

            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            int index = searchIndexIntoDataModel(facesContext,
                    (IGridComponent) component, rowValue);

            if (index < 0) {
                return;
            }

            ((IIndexesModel) selectedValues).addIndex(index);

            return;
        }

        select(component, selectedValues, Collections.singletonList(rowValue));
    }

    public static void select(FacesContext facesContext,
            ISelectionProvider component, int index) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            selectedValues = new ArrayIndexesModel();
            component.setSelectedValues(selectedValues);
        }

        if (selectedValues instanceof IIndexesModel) {
            ((IIndexesModel) selectedValues).addIndex(index);
            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(index, 1);
        }

        Object rowData = null;
        gridComponent.setRowIndex(index);
        try {
            rowData = gridComponent.getRowData();

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowData == null) {
            LOG.error("No rowData for index='" + index + "'.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Select index=" + index + " => " + rowData
                    + "   selectedValues=" + selectedValues);
        }

        select(component, selectedValues, Collections.singleton(rowData));
    }

    public static void select(FacesContext facesContext,
            ISelectionProvider component, int indices[]) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            selectedValues = new ArrayIndexesModel();
            component.setSelectedValues(selectedValues);
        }

        if (selectedValues instanceof IIndexesModel) {
            for (int i = 0; i < indices.length; i++) {
                ((IIndexesModel) selectedValues).addIndex(indices[i]);
            }

            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        int rowCount = gridComponent.getRowCount();
        if (rowCount > 0) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            DataModel dataModel = gridComponent.getDataModelValue();

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }
        }

        if (SORT_INDICES) {
            indices = (int[]) indices.clone();
            Arrays.sort(indices);
        }

        List rowDatas = new ArrayList(indices.length);
        try {
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                gridComponent.setRowIndex(index);

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("No rowData for index='" + index + "'.");
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Select index=" + index + " => " + rowData);
                }

                rowDatas.add(rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, selectedValues, rowDatas);
    }

    public static void select(FacesContext facesContext,
            ISelectionProvider component, int start, int end) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            selectedValues = new ArrayIndexesModel();
            component.setSelectedValues(selectedValues);
        }

        if (selectedValues instanceof IIndexesModel) {
            for (; start < end; start++) {
                ((IIndexesModel) selectedValues).addIndex(start);
            }

            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(start, end - start + 1);
        }

        List rowDatas = new ArrayList(end - start + 1);
        try {
            for (int index = start; index <= end; index++) {
                gridComponent.setRowIndex(index);

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("No rowData for index='" + index + "'.");
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Select index=" + index + " => " + rowData);
                }
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, selectedValues, rowDatas);
    }

    public static void selectAll(FacesContext facesContext,
            ISelectionProvider component) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            selectedValues = new ArrayIndexesModel();
            component.setSelectedValues(selectedValues);
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (selectedValues instanceof IIndexesModel) {
            int index = gridComponent.getRowCount();

            if (index < 0) {
                try {
                    for (;;) {
                        gridComponent.setRowIndex(index);
                        Object rowData = gridComponent.getRowData();

                        if (rowData == null) {
                            break;
                        }

                        index++;
                    }
                } finally {
                    gridComponent.setRowIndex(-1);
                }
            }

            if (index > 0) {
                for (int i = 0; i < index; i++) {
                    ((IIndexesModel) selectedValues).addIndex(i);
                }
            }

            return;
        }

        int rowCount = gridComponent.getRowCount();
        if (rowCount > 0) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            DataModel dataModel = gridComponent.getDataModelValue();

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }
        }

        if (rowCount < 8) {
            rowCount = 8;
        }

        List rowDatas = new ArrayList(rowCount);
        try {
            for (int index = 0;; index++) {
                gridComponent.setRowIndex(index);

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Select index=" + index + " => " + rowData);
                }
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, selectedValues, rowDatas);
    }

    public static void deselectAll(FacesContext facesContext,
            ISelectionProvider component) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            return;
        }

        if (selectedValues instanceof IIndexesModel) {
            ((IIndexesModel) selectedValues).clearIndexes();

            return;
        }

        if (selectedValues instanceof Collection) {
            ((Collection) selectedValues).clear();
            return;
        }

        if (selectedValues instanceof Object[]) {
            Class type = selectedValues.getClass().getComponentType();

            selectedValues = Array.newInstance(type, 0);

            component.setSelectedValues(selectedValues);
            return;
        }

        throw new FacesException(
                "Deselect all is not implemented for selectedValues="
                        + selectedValues);
    }

    public static void deselect(FacesContext facesContext,
            ISelectionProvider component, Object rowValue) {
        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            return;
        }

        if (selectedValues instanceof IIndexesModel) {
            // IndexesModels.select((IIndexesModel) selectedValues);

            // Il nous faut l'index ...

            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            int index = searchIndexIntoDataModel(facesContext,
                    (IGridComponent) component, rowValue);

            if (index < 0) {
                return;
            }

            ((IIndexesModel) selectedValues).removeIndex(index);

            return;
        }

        deselect(component, selectedValues, Collections.singletonList(rowValue));
    }

    private static int searchIndexIntoDataModel(FacesContext facesContext,
            IGridComponent component, Object rowValue) {
        int rowCount = component.getRowCount();
        if (rowCount > 0) {
            DataModel dataModel = component.getDataModelValue();

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }
        }

        try {
            for (int index = 0;; index++) {
                component.setRowIndex(index);

                Object rowData = component.getRowData();

                if (rowData == null) {
                    LOG.info("No rowData for index='" + index + "'.");
                    break;
                }

                if (rowData.equals(rowValue) == false) {
                    continue;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Find " + rowData + " => index=" + index);
                }

                return index;
            }

        } finally {
            component.setRowIndex(-1);
        }

        return -1;
    }

    public static void deselect(FacesContext facesContext,
            ISelectionProvider component, int index) {

        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            return;
        }

        if (selectedValues instanceof IIndexesModel) {
            ((IIndexesModel) selectedValues).removeIndex(index);
            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(index, 1);
        }

        Object rowData = null;
        gridComponent.setRowIndex(index);
        try {
            rowData = gridComponent.getRowData();

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowData == null) {
            LOG.error("No rowData for index='" + index + "'.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Select index=" + index + " => " + rowData
                    + "   selectedValues=" + selectedValues);
        }

        deselect(component, selectedValues, Collections.singleton(rowData));
    }

    public static void deselect(FacesContext facesContext,
            ISelectionProvider component, int indices[]) {

        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            return;
        }

        if (selectedValues instanceof IIndexesModel) {
            for (int i = 0; i < indices.length; i++) {
                ((IIndexesModel) selectedValues).removeIndex(indices[i]);
            }

            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        int rowCount = gridComponent.getRowCount();
        if (rowCount > 0) {
            DataModel dataModel = gridComponent.getDataModelValue();

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }
        }

        if (SORT_INDICES) {
            indices = (int[]) indices.clone();
            Arrays.sort(indices);
        }

        List rowDatas = new ArrayList(indices.length);
        try {
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                gridComponent.setRowIndex(index);

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("No rowData for index='" + index + "'.");
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Select index=" + index + " => " + rowData);
                }

                rowDatas.add(rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas.isEmpty()) {
            return;
        }

        deselect(component, selectedValues, rowDatas);
    }

    public static void deselect(FacesContext facesContext,
            ISelectionProvider component, int start, int end) {

        Object selectedValues = component.getSelectedValues();

        if (selectedValues == null) {
            return;
        }

        if (selectedValues instanceof IIndexesModel) {
            for (; start < end; start++) {
                ((IIndexesModel) selectedValues).removeIndex(start);
            }

            return;
        }

        IGridComponent gridComponent = (IGridComponent) component;

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(start, end - start + 1);
        }

        List rowDatas = new ArrayList(end - start + 1);
        try {
            for (int index = start; index <= end; index++) {
                gridComponent.setRowIndex(index);

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("No rowData for index='" + index + "'.");
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Select index=" + index + " => " + rowData);
                }
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas.isEmpty()) {
            return;
        }

        deselect(component, selectedValues, rowDatas);
    }

    private static void select(ISelectionProvider component,
            Object selectedValues, Collection rowDatas) {

        if (selectedValues instanceof Object[]) {
            List l = Arrays.asList((Object[]) selectedValues);

            boolean changed = false;

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                if (l.contains(rowData)) {
                    continue;
                }

                if (changed == false) {
                    l = new ArrayList(l);
                    changed = true;
                }

                l.add(rowData);
            }

            if (changed == false) {
                return;
            }

            Class type = selectedValues.getClass().getComponentType();

            selectedValues = l.toArray((Object[]) Array.newInstance(type, l
                    .size()));

            component.setSelectedValues(selectedValues);

            return;
        }

        if (selectedValues instanceof Collection) {
            Collection collection = (Collection) selectedValues;

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                if (collection.contains(rowData)) {
                    return;
                }

                collection.add(rowData);
            }

            return;
        }

        throw new FacesException(
                "Select index is not implemented for selectedValues="
                        + selectedValues);
    }

    private static void deselect(ISelectionProvider component,
            Object selectedValues, Collection rowDatas) {

        if (selectedValues instanceof Object[]) {
            List l = Arrays.asList((Object[]) selectedValues);

            boolean changed = false;

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                if (l.contains(rowData)) {
                    continue;
                }

                if (changed == false) {
                    l = new ArrayList(l);
                    changed = true;
                }

                l.remove(rowData);
            }

            if (changed == false) {
                return;
            }

            Class type = selectedValues.getClass().getComponentType();

            selectedValues = l.toArray((Object[]) Array.newInstance(type, l
                    .size()));

            component.setSelectedValues(selectedValues);

            return;
        }

        if (selectedValues instanceof Collection) {
            Collection collection = (Collection) selectedValues;

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                if (collection.contains(rowData)) {
                    return;
                }

                collection.remove(rowData);
            }

            return;
        }

        throw new FacesException(
                "Deselect index is not implemented for selectedValues="
                        + selectedValues);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    static final class DataModelWrapper extends DataModel implements IDataModel {
        private static final String REVISION = "$Revision$";

        private final IDataModel dataModel;

        public DataModelWrapper(IDataModel dataModel) {
            this.dataModel = dataModel;
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

        public void setRowIndex(int rowIndex) {
            dataModel.setRowIndex(rowIndex);
        }

        public void setWrappedData(Object data) {
            dataModel.setWrappedData(data);
        }

    }

    public static void setOrderIds(IGridComponent gridComponent,
            String columnsOrder) {
        // TODO Auto-generated method stub

    }

    public static void setSortIds(IGridComponent gridComponent,
            String sortedColumnIds) {
        // TODO Auto-generated method stub

    }

    public static String getOrderIds(IGridComponent dataGridComponent) {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getSortIds(IGridComponent dataGridComponent) {
        // TODO Auto-generated method stub
        return null;
    }
}
