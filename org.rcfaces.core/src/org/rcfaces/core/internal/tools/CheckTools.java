/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
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

        public Object getComponentValues(UIComponent component) {
            return ((ICheckedValuesCapability) component).getCheckedValues();
        }

        public void setComponentValues(UIComponent component, Object values) {
            ((ICheckedValuesCapability) component).setCheckedValues(values);
        }

        public Class getComponentValuesType(FacesContext facesContext,
                UIComponent component) {
            return ((ICheckedValuesCapability) component)
                    .getCheckedValuesType(facesContext);
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

    public static Object adaptValue(Object value, Object defaultValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(value,
                ICheckProvider.class, CHECK_PROVIDER_VALUES_ACCESSOR, false);

        if (valuesAccessor == null) {
            return defaultValue;
        }

        return valuesAccessor.adaptValue(value);
    }

    public static void check(FacesContext facesContext, UIComponent component,
            Object rowValue) {
        select(component, CHECK_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void check(FacesContext facesContext, UIComponent component,
            int index) {
        select(component, CHECK_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void check(FacesContext facesContext, UIComponent component,
            int start, int end) {
        select(component, CHECK_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void checkAll(FacesContext facesContext, UIComponent component) {
        selectAll(component, CHECK_PROVIDER_VALUES_ACCESSOR);
    }

    public static void uncheck(FacesContext facesContext,
            UIComponent component, Object rowValue) {
        deselect(component, CHECK_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void uncheck(FacesContext facesContext,
            UIComponent component, int index) {
        deselect(component, CHECK_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void uncheck(FacesContext facesContext,
            UIComponent component, int start, int end) {
        deselect(component, CHECK_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void uncheckAll(FacesContext facesContext,
            UIComponent component) {
        deselectAll(component, CHECK_PROVIDER_VALUES_ACCESSOR);
    }
}
