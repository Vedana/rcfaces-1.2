/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.ICommitableObject;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CheckTools.class);

    protected static final Object[] EMPTY_VALUES = new Object[0];

    private static final boolean SORT_INDICES = true;

    private static final Map IMPLEMENTATION_TYPES = new HashMap(64);
    static {
        IMPLEMENTATION_TYPES.put(Collection.class, ArrayList.class);
        IMPLEMENTATION_TYPES.put(List.class, ArrayList.class);
        IMPLEMENTATION_TYPES.put(Set.class, HashSet.class);
        IMPLEMENTATION_TYPES.put(Map.class, HashMap.class);

        IMPLEMENTATION_TYPES.put(IIndexesModel.class, ArrayIndexesModel.class);
    }

    private static final IValuesAccessor NULL_VALUES_ACCESSOR = new AbstractValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object value) {
            return 0;
        }

        public Object getFirst(Object value, Object refValues) {
            return null;
        }

        public Object[] listValues(Object value, Object refValues) {
            return EMPTY_VALUES;
        }

        public Object adaptValue(Object value) {
            return null;
        }

    };

    private static final IValuesAccessor ARRAY_VALUES_ACCESSOR = new AbstractValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object array) {
            return Array.getLength(array);
        }

        public Object getFirst(Object array, Object refValues) {
            if (getCount(array) < 1) {
                return null;
            }

            return Array.get(array, 0);
        }

        public Object[] listValues(Object array, Object refValues) {
            return (Object[]) array;
        }

        public Object adaptValue(Object value) {
            return value;
        }

    };

    private static final IValuesAccessor COLLECTION_VALUES_ACCESSOR = new AbstractValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object collection) {
            return ((Collection) collection).size();
        }

        public Object getFirst(Object collection, Object refValues) {
            if (collection instanceof List) {
                return ((List) collection).get(0);
            }

            return ((Collection) collection).iterator().next();
        }

        public Object[] listValues(Object collection, Object refValues) {
            return ((Collection) collection).toArray();
        }

        public Object adaptValue(Object value) {
            return value;
        }
    };

    private static final IValuesAccessor MAP_VALUES_ACCESSOR = new AbstractValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object map) {
            return ((Map) map).size();
        }

        public Object getFirst(Object map, Object refValues) {
            return ((Map) map).keySet().iterator().next();
        }

        public Object[] listValues(Object map, Object refValues) {
            return ((Map) map).keySet().toArray();
        }

        public Object adaptValue(Object value) {
            return value;
        }
    };

    private static final IValuesAccessor INDEXES_MODEL_VALUES_ACCESSOR = new AbstractValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object indexesModel) {
            return ((IIndexesModel) indexesModel).countIndexes();
        }

        public Object getFirst(Object indexesModel, Object refValues) {
            return ((IIndexesModel) indexesModel)
                    .getFirstSelectedObject(refValues);
        }

        public Object[] listValues(Object indexesModel, Object refValues) {
            return ((IIndexesModel) indexesModel).listSelectedObjects(null,
                    refValues);
        }

        public Object adaptValue(Object value) {
            return value;
        }

    };

    protected static IValuesAccessor getValuesAccessor(Object values,
            Class providerClass, IValuesAccessor providerValuesAccessor,
            boolean useValue) {
        if (values == null) {
            if (useValue == false) {
                return null;
            }
            return NULL_VALUES_ACCESSOR;
        }

        if (values.getClass().isArray()) {
            if (useValue == false) {
                return null;
            }
            return ARRAY_VALUES_ACCESSOR;
        }

        if (values instanceof Collection) {
            if (useValue == false) {
                return null;
            }
            return COLLECTION_VALUES_ACCESSOR;
        }

        if (values instanceof Map) {
            if (useValue == false) {
                return null;
            }
            return MAP_VALUES_ACCESSOR;
        }

        if (values instanceof IIndexesModel) {
            if (useValue == false) {
                return null;
            }
            return INDEXES_MODEL_VALUES_ACCESSOR;
        }

        if (values.getClass().isAssignableFrom(providerClass)) {
            return providerValuesAccessor;
        }

        if (values instanceof IAdaptable) {
            Object provider = ((IAdaptable) values).getAdapter(providerClass,
                    null);

            if (provider != null) {
                return new ProviderValuesAccessor(providerValuesAccessor,
                        provider);
            }
        }

        Object provider = RcfacesContext.getCurrentInstance()
                .getAdapterManager().getAdapter(values, providerClass, null);

        if (provider != null) {
            return new ProviderValuesAccessor(providerValuesAccessor, provider);
        }

        return null;
    }

    public static Object getEmptyValues() {
        return EMPTY_VALUES;
    }

    protected static Object[] convertToObjectArray(Object values) {
        if (values == null) {
            return null;
        }

        if (values instanceof Object[]) {
            return (Object[]) values;
        }

        if (values instanceof Collection) {
            return ((Collection) values).toArray();
        }

        if (values instanceof Map) {
            return ((Map) values).keySet().toArray();
        }

        throw new FacesException("Can not convert object '" + values
                + "' to object array.");
    }

    private static Object createNewValues(UIComponent component,
            IValuesAccessor valuesAccessor) {
        Class type = valuesAccessor.getComponentValuesType(null, component);
        if (type == null) {
            return new ArrayIndexesModel();
        }

        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), 0);
        }

        Class implementationType = (Class) IMPLEMENTATION_TYPES.get(type);
        if (implementationType != null) {
            type = implementationType;
        }

        try {
            return type.newInstance();

        } catch (Throwable th) {
            throw new FacesException("Can not instanciate values for type '"
                    + type + "'", th);
        }
    }

    public static void select(UIComponent component,
            IValuesAccessor valuesAccessor, int indices[]) {
        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            values = createNewValues(component, valuesAccessor);
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            for (int i = 0; i < indices.length; i++) {
                indexesModel.addIndex(indices[i]);
            }

            return;
        }

        List rowDatas = getRowDatas((IGridComponent) component, indices);
        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, valuesAccessor, values, rowDatas);
    }

    public static void select(UIComponent component,
            IValuesAccessor valuesAccessor, int start, int end) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            values = createNewValues(component, valuesAccessor);
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            for (; start < end; start++) {
                indexesModel.addIndex(start);
            }

            return;
        }

        List rowDatas = getRowDatas((IGridComponent) component, start, end);
        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, valuesAccessor, values, rowDatas);
    }

    public static void select(UIComponent component,
            IValuesAccessor valuesAccessor, int index) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            values = createNewValues(component, valuesAccessor);
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            indexesModel.addIndex(index);
            return;
        }

        Object rowData = getRowData((IGridComponent) component, index);
        if (rowData == null) {
            LOG.error("No rowData for index='" + index + "'.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Select index=" + index + " => " + rowData
                    + "   selectedValues=" + values);
        }

        select(component, valuesAccessor, values, Collections
                .singleton(rowData));
    }

    public static void selectAll(UIComponent component,
            IValuesAccessor valuesAccessor) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            values = createNewValues(component, valuesAccessor);
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            int rowCount = getRowCount(component);
            for (int i = 0; i < rowCount; i++) {
                indexesModel.addIndex(i);
            }

            return;
        }

        List rowDatas = getRowDatas((IGridComponent) component);
        if (rowDatas.isEmpty()) {
            return;
        }

        select(component, valuesAccessor, values, rowDatas);
    }

    public static void select(UIComponent component,
            IValuesAccessor valuesAccessor, Object rowValue) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            values = createNewValues(component, valuesAccessor);
        }

        if (values instanceof IIndexesModel) {
            int index = searchIndexIntoDataModel((IGridComponent) component,
                    rowValue);
            if (index < 0) {
                return;
            }

            select(component, valuesAccessor, index);

            return;
        }

        select(component, valuesAccessor, values, Collections
                .singletonList(rowValue));
    }

    private static void select(UIComponent component,
            IValuesAccessor valuesAccessor, Object values, Collection rowDatas) {

        if (values.getClass().isArray()) {

            int length = Array.getLength(values);

            List l = null;

            next_data: for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                for (int i = 0; i < length; i++) {
                    if (Array.get(values, i).equals(rowData) == false) {
                        continue;
                    }

                    continue next_data;
                }

                if (l == null) {
                    l = new ArrayList();

                } else if (l.contains(rowData)) {
                    continue;
                }

                l.add(rowData);
            }

            if (l == null) {
                return;
            }

            Class type = values.getClass().getComponentType();

            Object newValues = Array.newInstance(type, length + l.size());

            System.arraycopy(values, 0, newValues, 0, length);

            for (Iterator it = l.iterator(); it.hasNext(); length++) {
                Array.set(newValues, length, it.next());
            }

            valuesAccessor.setComponentValues(component, newValues);

            return;
        }

        if (values instanceof Collection) {
            Collection collection = cloneCollection(component, valuesAccessor,
                    (Collection) values);

            if (collection instanceof Set) {
                collection.addAll(rowDatas);
                return;
            }

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                if (collection.contains(rowData)) {
                    return;
                }

                collection.add(rowData);
            }

            return;
        }

        throw new FacesException("Select index is not implemented for values="
                + values);
    }

    private static Collection cloneCollection(UIComponent component,
            IValuesAccessor valuesAccessor, Collection collection) {

        boolean copy = true;

        if (collection instanceof ICommitableObject) {
            copy = ((ICommitableObject) collection).isCommited();
        }

        if (copy == false) {
            return collection;
        }

        try {
            Method method = collection.getClass().getMethod("clone", null);

            collection = (Collection) method.invoke(collection, null);

        } catch (Throwable th) {
            LOG.error("Can not copy the collection ! ("
                    + collection.getClass().getName() + ")", th);
        }

        valuesAccessor.setComponentValues(component, collection);

        return collection;
    }

    private static int getRowCount(UIComponent component) {

        IGridComponent gridComponent = (IGridComponent) component;

        int index = gridComponent.getRowCount();

        if (index >= 0) {
            return index;
        }

        try {
            for (index = 0;; index++) {
                gridComponent.setRowIndex(index);

                if (gridComponent.isRowAvailable()) {
                    return index;
                }
            }
        } finally {
            gridComponent.setRowIndex(-1);
        }
    }

    public static void deselectAll(UIComponent component,
            IValuesAccessor valuesAccessor) {

        Object values = valuesAccessor.getComponentValues(component);
        if (values == null) {
            return;
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            indexesModel.clearIndexes();
            return;
        }

        if (values instanceof Collection) {
            Collection collection = cloneCollection(component, valuesAccessor,
                    (Collection) values);

            collection.clear();
            return;
        }

        if (values instanceof Object[]) {
            if (Array.getLength(values) == 0) {
                return;
            }

            Class type = values.getClass().getComponentType();

            values = Array.newInstance(type, 0);

            valuesAccessor.setComponentValues(component, values);
            return;
        }

        throw new FacesException("Deselect all is not implemented for values="
                + values);
    }

    public static void deselect(UIComponent component,
            IValuesAccessor valuesAccessor, Object rowValue) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            return;
        }

        if (values instanceof IIndexesModel) {
            int index = searchIndexIntoDataModel((IGridComponent) component,
                    rowValue);

            if (index < 0) {
                return;
            }

            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            indexesModel.removeIndex(index);

            return;
        }

        deselect(component, valuesAccessor, values, Collections
                .singletonList(rowValue));
    }

    public static void deselect(UIComponent component,
            IValuesAccessor valuesAccessor, int index) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            return;
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            indexesModel.removeIndex(index);
            return;
        }

        Object rowData = getRowData((IGridComponent) component, index);

        if (rowData == null) {
            LOG.error("No rowData for index='" + index + "'.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Deselect index=" + index + " => " + rowData
                    + "   selectedValues=" + values);
        }

        deselect(component, valuesAccessor, values, Collections
                .singleton(rowData));
    }

    public static void deselect(UIComponent component,
            IValuesAccessor valuesAccessor, int indices[]) {

        if (indices == null || indices.length < 1) {
            return;
        }

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            return;
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            for (int i = 0; i < indices.length; i++) {
                indexesModel.removeIndex(indices[i]);
            }

            return;
        }

        List rowDatas = getRowDatas((IGridComponent) component, indices);
        if (rowDatas.isEmpty()) {
            return;
        }

        deselect(component, valuesAccessor, values, rowDatas);
    }

    public static void deselect(UIComponent component,
            IValuesAccessor valuesAccessor, int start, int end) {

        Object values = valuesAccessor.getComponentValues(component);

        if (values == null) {
            return;
        }

        if (values instanceof IIndexesModel) {
            IIndexesModel indexesModel = cloneIndexModel(component,
                    valuesAccessor, (IIndexesModel) values);

            for (; start < end; start++) {
                indexesModel.removeIndex(start);
            }

            return;
        }

        List rowDatas = getRowDatas((IGridComponent) component, start, end);
        if (rowDatas.isEmpty()) {
            return;
        }

        deselect(component, valuesAccessor, values, rowDatas);
    }

    private static void deselect(UIComponent component,
            IValuesAccessor valuesAccessor, Object values, Collection rowDatas) {

        if (values.getClass().isArray()) {

            int length = Array.getLength(values);
            if (length == 0) {
                return;
            }

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                for (int i = 0; i < length;) {
                    if (Array.get(values, i).equals(rowData) == false) {
                        i++;
                        continue;
                    }

                    length--;
                    if (i >= length) {
                        break;
                    }

                    System.arraycopy(values, i + 1, values, i, length - i);
                }
            }

            if (Array.getLength(values) == length) {
                // On a pas touché au tableau !
                return;
            }

            Class type = values.getClass().getComponentType();

            Object newValues = Array.newInstance(type, length);
            if (length > 0) {
                System.arraycopy(values, 0, newValues, 0, length);
            }

            valuesAccessor.setComponentValues(component, newValues);

            return;
        }

        if (values instanceof Collection) {
            Collection collection = cloneCollection(component, valuesAccessor,
                    (Collection) values);

            if (collection instanceof Set) {
                collection.removeAll(rowDatas);
                return;
            }

            for (Iterator it = rowDatas.iterator(); it.hasNext();) {
                Object rowData = it.next();

                collection.remove(rowData);
            }

            return;
        }

        throw new FacesException(
                "Deselect index is not implemented for selectedValues="
                        + values);
    }

    private static IIndexesModel cloneIndexModel(UIComponent component,
            IValuesAccessor valuesAccessor, IIndexesModel indexesModel) {

        boolean copy = true;

        if (indexesModel instanceof ICommitableObject) {
            copy = ((ICommitableObject) indexesModel).isCommited();
        }

        if (copy == false) {
            return indexesModel;
        }

        indexesModel = indexesModel.copy();
        valuesAccessor.setComponentValues(component, indexesModel);

        return indexesModel;
    }

    private static List getRowDatas(IGridComponent gridComponent, int[] indices) {

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

        List rowDatas = null;
        try {
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                gridComponent.setRowIndex(index);
                if (gridComponent.isRowAvailable() == false) {
                    LOG.error("Row not available for index='" + index + "'.");
                    continue;
                }

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("No rowData for index='" + index + "'.");
                    continue;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Get row at index=" + index + " => " + rowData);
                }

                if (rowDatas == null) {
                    rowDatas = new ArrayList(indices.length - i);
                }
                rowDatas.add(rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas == null) {
            return Collections.EMPTY_LIST;
        }

        return rowDatas;
    }

    private static Object getRowData(IGridComponent gridComponent, int index) {

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(index, 1);
        }

        Object rowData = null;
        gridComponent.setRowIndex(index);
        try {
            if (gridComponent.isRowAvailable() == false) {
                LOG.error("Row not available for index='" + index + "'.");
                return null;
            }

            rowData = gridComponent.getRowData();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get row at index=" + index + " => " + rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowData == null) {
            LOG.error("RowData is null for index='" + index + "'.");
        }

        return rowData;
    }

    private static List getRowDatas(IGridComponent gridComponent) {

        int rowCount = gridComponent.getRowCount();
        if (rowCount > 0) {
            DataModel dataModel = gridComponent.getDataModelValue();

            if (dataModel instanceof IRangeDataModel) {
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }
        }

        List rowDatas = null;
        try {
            for (int index = 0;; index++) {
                gridComponent.setRowIndex(index);

                if (gridComponent.isRowAvailable() == false) {
                    LOG.debug("Row not available for index='" + index + "'.");
                    break;
                }

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.debug("RowData is null for index='" + index + "'.");
                    continue;
                }

                if (rowDatas == null) {
                    int size = rowCount - index;

                    if (size < 8) {
                        size = 8;
                    }

                    rowDatas = new ArrayList(size);
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Get row at index=" + index + " => " + rowData);
                }

                rowDatas.add(rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas == null) {
            return Collections.EMPTY_LIST;
        }

        return rowDatas;
    }

    private static List getRowDatas(IGridComponent gridComponent, int start,
            int end) {

        DataModel dataModel = gridComponent.getDataModelValue();

        if (dataModel instanceof IRangeDataModel) {
            ((IRangeDataModel) dataModel).setRowRange(start, end - start + 1);
        }

        List rowDatas = null;
        try {
            for (int index = start; index <= end; index++) {
                gridComponent.setRowIndex(index);

                if (gridComponent.isRowAvailable() == false) {
                    LOG.error("Row not available for index='" + index + "'.");
                    break;
                }

                Object rowData = gridComponent.getRowData();

                if (rowData == null) {
                    LOG.error("RowData is null for index='" + index + "'.");
                    break;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Get index=" + index + " => " + rowData);
                }

                if (rowDatas == null) {
                    rowDatas = new ArrayList(end - index + 1);
                }

                rowDatas.add(rowData);
            }

        } finally {
            gridComponent.setRowIndex(-1);
        }

        if (rowDatas == null) {
            return Collections.EMPTY_LIST;
        }

        return rowDatas;
    }

    private static int searchIndexIntoDataModel(IGridComponent component,
            Object rowValue) {
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
                if (component.isRowAvailable() == false) {
                    break;
                }

                Object rowData = component.getRowData();

                if (rowData == null) {
                    LOG.error("RowData is null for index='" + index + "'.");
                    break;
                }

                if (rowData.equals(rowValue) == false) {
                    continue;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Find row " + rowData + " => index=" + index);
                }

                return index;
            }

        } finally {
            component.setRowIndex(-1);
        }

        return -1;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ProviderValuesAccessor implements IValuesAccessor {

        private final IValuesAccessor providerValuesAccessor;

        private final Object provider;

        public ProviderValuesAccessor(IValuesAccessor providerValuesAccessor,
                Object provider) {
            this.providerValuesAccessor = providerValuesAccessor;
            this.provider = provider;
        }

        public int getCount(Object value) {
            return providerValuesAccessor.getCount(provider);
        }

        public Object getFirst(Object value, Object refValues) {
            return providerValuesAccessor.getFirst(provider, null);
        }

        public Object[] listValues(Object value, Object refValues) {
            return providerValuesAccessor.listValues(provider, null);
        }

        public Object adaptValue(Object value) {
            return provider;
        }

        public Object getComponentValues(UIComponent component) {
            return providerValuesAccessor.getComponentValues(component);
        }

        public void setComponentValues(UIComponent component, Object values) {
            providerValuesAccessor.setComponentValues(component, values);
        }

        public Class getComponentValuesType(FacesContext facesContext,
                UIComponent component) {
            return providerValuesAccessor.getComponentValuesType(null,
                    component);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected interface IValuesAccessor {
        Object getFirst(Object value, Object refValues);

        int getCount(Object value);

        Object[] listValues(Object value, Object refValues);

        Object adaptValue(Object value);

        Object getComponentValues(UIComponent component);

        void setComponentValues(UIComponent component, Object values);

        Class getComponentValuesType(FacesContext facesContext,
                UIComponent component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class AbstractValuesAccessor implements
            IValuesAccessor {
        private static final String REVISION = "$Revision$";

        public Object getComponentValues(UIComponent component) {
            throw new IllegalStateException("Not implemented !");
        }

        public void setComponentValues(UIComponent component, Object values) {
            throw new IllegalStateException("Not implemented !");
        }

        public Class getComponentValuesType(FacesContext facesContext,
                UIComponent component) {
            throw new IllegalStateException("Not implemented !");
        }
    }
}
