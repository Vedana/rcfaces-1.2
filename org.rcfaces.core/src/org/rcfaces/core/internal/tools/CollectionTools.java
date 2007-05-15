/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.IIndexesModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CheckTools.class);

    protected static final Object[] EMPTY_VALUES = new Object[0];

    private static final IValuesAccessor NULL_VALUES_ACCESSOR = new IValuesAccessor() {
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

    private static final IValuesAccessor ARRAY_VALUES_ACCESSOR = new IValuesAccessor() {
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

    private static final IValuesAccessor COLLECTION_VALUES_ACCESSOR = new IValuesAccessor() {
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

    private static final IValuesAccessor MAP_VALUES_ACCESSOR = new IValuesAccessor() {
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

    private static final IValuesAccessor INDEXES_MODEL_VALUES_ACCESSOR = new IValuesAccessor() {

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
    }
}
