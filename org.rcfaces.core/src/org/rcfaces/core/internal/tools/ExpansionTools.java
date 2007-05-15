/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.lang.provider.IExpansionProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExpansionTools extends CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ExpansionTools.class);

    private static final IValuesAccessor EXPANSION_PROVIDER_VALUES_ACCESSOR = new IValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object checkProvider) {
            return ((IExpansionProvider) checkProvider)
                    .getExpandedValuesCount();
        }

        public Object getFirst(Object checkProvider, Object refValues) {
            return null;
        }

        public Object[] listValues(Object checkProvider, Object refValues) {
            return convertToObjectArray(((IExpansionProvider) checkProvider)
                    .getExpandedValues());
        }

        public Object adaptValue(Object value) {
            return value;
        }

    };

    public static int getCount(Object expandedValues) {
        IValuesAccessor valuesAccessor = getValuesAccessor(expandedValues,
                IExpansionProvider.class, EXPANSION_PROVIDER_VALUES_ACCESSOR,
                true);

        if (valuesAccessor == null) {
            return 0;
        }

        return valuesAccessor.getCount(expandedValues);
    }

    public static Object[] listValues(Object expandedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(expandedValues,
                IExpansionProvider.class, EXPANSION_PROVIDER_VALUES_ACCESSOR,
                true);

        if (valuesAccessor == null) {
            return EMPTY_VALUES;
        }

        return valuesAccessor.listValues(expandedValues, refValue);
    }

}
