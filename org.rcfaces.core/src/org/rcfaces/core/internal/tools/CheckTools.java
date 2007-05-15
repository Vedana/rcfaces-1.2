/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.lang.provider.ICheckProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckTools extends CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CheckTools.class);

    private static final IValuesAccessor CHECK_PROVIDER_VALUES_ACCESSOR = new IValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object checkProvider) {
            return ((ICheckProvider) checkProvider).getCheckedValuesCount();
        }

        public Object getFirst(Object checkProvider, Object refValues) {
            return ((ICheckProvider) checkProvider).getFirstCheckedValue();
        }

        public Object[] listValues(Object checkProvider, Object refValues) {
            return convertToObjectArray(((ICheckProvider) checkProvider)
                    .getCheckedValues());
        }

        public Object adaptValue(Object value) {
            return value;
        }

    };

    public static int getCount(Object checkedValues) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, CHECK_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return 0;
        }
        return valuesAccessor.getCount(checkedValues);
    }

    public static Object getFirst(Object checkedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, CHECK_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return null;
        }

        return valuesAccessor.getFirst(checkedValues, refValue);
    }

    public static Object[] listValues(Object checkedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, CHECK_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return EMPTY_VALUES;
        }

        return valuesAccessor.listValues(checkedValues, refValue);
    }

    public static Object adaptValue(Object value) {
        IValuesAccessor valuesAccessor = getValuesAccessor(value,
                ICheckProvider.class, CHECK_PROVIDER_VALUES_ACCESSOR, false);

        if (valuesAccessor == null) {
            return EMPTY_VALUES;
        }

        return valuesAccessor.adaptValue(value);
    }
}
