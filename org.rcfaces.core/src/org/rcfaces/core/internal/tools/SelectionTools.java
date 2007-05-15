/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.lang.provider.ISelectionProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectionTools extends CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SelectionTools.class);

    private static final IValuesAccessor SELECTION_PROVIDER_VALUES_ACCESSOR = new IValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object checkProvider) {
            return ((ISelectionProvider) checkProvider)
                    .getSelectedValuesCount();
        }

        public Object getFirst(Object checkProvider, Object refValues) {
            return ((ISelectionProvider) checkProvider).getFirstSelectedValue();
        }

        public Object[] listValues(Object checkProvider, Object refValues) {
            return convertToObjectArray(((ISelectionProvider) checkProvider)
                    .getSelectedValues());
        }

        public Object adaptValue(Object value) {
            return value;
        }
    };

    public static int getCount(Object selectedValues) {
        IValuesAccessor valuesAccessor = getValuesAccessor(selectedValues,
                ISelectionProvider.class, SELECTION_PROVIDER_VALUES_ACCESSOR,
                true);
        if (valuesAccessor == null) {
            return 0;
        }
        return valuesAccessor.getCount(selectedValues);
    }

    public static Object getFirst(Object selectedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(selectedValues,
                ISelectionProvider.class, SELECTION_PROVIDER_VALUES_ACCESSOR,
                true);
        if (valuesAccessor == null) {
            return null;
        }
        return valuesAccessor.getFirst(selectedValues, refValue);
    }

    public static Object[] listValues(Object selectedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(selectedValues,
                ISelectionProvider.class, SELECTION_PROVIDER_VALUES_ACCESSOR,
                true);
        if (valuesAccessor == null) {
            return EMPTY_VALUES;
        }
        return valuesAccessor.listValues(selectedValues, refValue);
    }

    public static Object adaptValue(Object value) {
        IValuesAccessor valuesAccessor = getValuesAccessor(value,
                ISelectionProvider.class, SELECTION_PROVIDER_VALUES_ACCESSOR,
                false);
        if (valuesAccessor == null) {
            return null;
        }
        return valuesAccessor.adaptValue(value);
    }

}
