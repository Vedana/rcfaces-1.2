/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
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

        public Object getComponentValues(UIComponent component) {
            return ((ISelectedValuesCapability) component).getSelectedValues();
        }

        public void setComponentValues(UIComponent component, Object values) {
            ((ISelectedValuesCapability) component).setSelectedValues(values);
        }

        public Class getComponentValuesType(FacesContext facesContext,
                UIComponent component) {
            return ((ISelectedValuesCapability) component)
                    .getSelectedValuesType(facesContext);
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

    public static Object adaptValue(Object value, Object defaultValues) {
        IValuesAccessor valuesAccessor = getValuesAccessor(value,
                ISelectionProvider.class, SELECTION_PROVIDER_VALUES_ACCESSOR,
                false);
        if (valuesAccessor == null) {
            return defaultValues;
        }
        return valuesAccessor.adaptValue(value);
    }

    public static void select(FacesContext facesContext, UIComponent component,
            Object rowValue) {
        select(component, SELECTION_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void select(FacesContext facesContext, UIComponent component,
            int index) {
        select(component, SELECTION_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void select(FacesContext facesContext, UIComponent component,
            int indexes[]) {
        select(component, SELECTION_PROVIDER_VALUES_ACCESSOR, indexes);
    }

    public static void select(FacesContext facesContext, UIComponent component,
            int start, int end) {
        select(component, SELECTION_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void selectAll(FacesContext facesContext,
            UIComponent component) {
        selectAll(component, SELECTION_PROVIDER_VALUES_ACCESSOR);
    }

    public static void deselect(FacesContext facesContext,
            UIComponent component, Object rowValue) {
        deselect(component, SELECTION_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void deselect(FacesContext facesContext,
            UIComponent component, int index) {
        deselect(component, SELECTION_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void deselect(FacesContext facesContext,
            UIComponent component, int indexes[]) {
        deselect(component, SELECTION_PROVIDER_VALUES_ACCESSOR, indexes);
    }

    public static void deselect(FacesContext facesContext,
            UIComponent component, int start, int end) {
        deselect(component, SELECTION_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void deselectAll(FacesContext facesContext,
            UIComponent component) {
        deselectAll(component, SELECTION_PROVIDER_VALUES_ACCESSOR);
    }

}
