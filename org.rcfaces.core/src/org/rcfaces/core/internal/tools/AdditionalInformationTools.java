/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AdditionalInformationComponent;
import org.rcfaces.core.component.capability.IAdditionalProvider;
import org.rcfaces.core.component.capability.IAdditionalValuesCapability;
import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.lang.provider.ICheckProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdditionalInformationTools extends CollectionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AdditionalInformationTools.class);

    private static final IValuesAccessor ADDITIONAL_PROVIDER_VALUES_ACCESSOR = new IValuesAccessor() {
        private static final String REVISION = "$Revision$";

        public int getCount(Object checkProvider) {
            return ((IAdditionalProvider) checkProvider)
                    .getAdditionalValuesCount();
        }

        public Object getFirst(Object checkProvider, Object refValues) {
            return ((IAdditionalProvider) checkProvider)
                    .getFirstAdditionalValue();
        }

        public Object[] listValues(Object checkProvider, Object refValues) {
            return convertToObjectArray(((IAdditionalProvider) checkProvider)
                    .getAdditionalValues());
        }

        public Object adaptValue(Object value) {
            return value;
        }

        public Object getComponentValues(UIComponent component) {
            return ((IAdditionalValuesCapability) component)
                    .getAdditionalValues();
        }

        public void setComponentValues(UIComponent component, Object values) {
            ((IAdditionalValuesCapability) component)
                    .setAdditionalValues(values);
        }

        public Class getComponentValuesType(FacesContext facesContext,
                UIComponent component) {
            return ((IAdditionalValuesCapability) component)
                    .getAdditionalValuesType(facesContext);
        }
    };

    private static final IAdditionalInformationIterator EMPTY_ADDITIONAL_INFORMATION_ITERATOR = new AdditionalInformationListIterator(
            Collections.EMPTY_LIST);

    public static int getCount(Object checkedValues) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return 0;
        }
        return valuesAccessor.getCount(checkedValues);
    }

    public static Object getFirst(Object checkedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return null;
        }

        return valuesAccessor.getFirst(checkedValues, refValue);
    }

    public static Object[] listValues(Object checkedValues, Object refValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(checkedValues,
                ICheckProvider.class, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, true);

        if (valuesAccessor == null) {
            return EMPTY_VALUES;
        }

        return valuesAccessor.listValues(checkedValues, refValue);
    }

    public static Object adaptValue(Object value, Object defaultValue) {
        IValuesAccessor valuesAccessor = getValuesAccessor(value,
                ICheckProvider.class, ADDITIONAL_PROVIDER_VALUES_ACCESSOR,
                false);

        if (valuesAccessor == null) {
            return defaultValue;
        }

        return valuesAccessor.adaptValue(value);
    }

    public static void show(FacesContext facesContext, UIComponent component,
            Object rowValue) {
        select(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void show(FacesContext facesContext, UIComponent component,
            int index) {
        select(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void show(FacesContext facesContext, UIComponent component,
            int indexes[]) {
        select(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, indexes);
    }

    public static void show(FacesContext facesContext, UIComponent component,
            int start, int end) {
        select(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void showAll(FacesContext facesContext, UIComponent component) {
        selectAll(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR);
    }

    public static void hide(FacesContext facesContext, UIComponent component,
            Object rowValue) {
        deselect(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, rowValue);
    }

    public static void hide(FacesContext facesContext, UIComponent component,
            int index) {
        deselect(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, index);
    }

    public static void hide(FacesContext facesContext, UIComponent component,
            int indexes[]) {
        deselect(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, indexes);
    }

    public static void hide(FacesContext facesContext, UIComponent component,
            int start, int end) {
        deselect(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR, start, end);
    }

    public static void hideAll(FacesContext facesContext, UIComponent component) {
        deselectAll(component, ADDITIONAL_PROVIDER_VALUES_ACCESSOR);
    }

    public static IAdditionalInformationIterator listAdditionalInformations(
            UIComponent component) {
        List list = ComponentIterators.list(component,
                AdditionalInformationComponent.class);
        if (list.isEmpty()) {
            return EMPTY_ADDITIONAL_INFORMATION_ITERATOR;
        }

        return new AdditionalInformationListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class AdditionalInformationListIterator extends
            ComponentIterators.ComponentListIterator implements
            IAdditionalInformationIterator {
        private static final String REVISION = "$Revision$";

        public AdditionalInformationListIterator(List list) {
            super(list);
        }

        public final AdditionalInformationComponent next() {
            return (AdditionalInformationComponent) nextComponent();
        }

        public AdditionalInformationComponent[] toArray() {
            return (AdditionalInformationComponent[]) toArray(new AdditionalInformationComponent[count()]);
        }

    }
}
