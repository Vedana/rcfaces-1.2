/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.lang.provider.ICheckProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckTools {

    public static int getCount(Object checkedValues) {
        if (checkedValues == null) {
            return 0;
        }

        if (checkedValues.getClass().isArray()) {
            return Array.getLength(checkedValues);
        }

        if (checkedValues instanceof Collection) {
            return ((Collection) checkedValues).size();
        }

        if (checkedValues instanceof Map) {
            return ((Map) checkedValues).size();
        }

        ICheckProvider checkProvider = getCheckProvider(checkedValues);

        if (checkProvider != null) {
            return checkProvider.getCheckedValuesCount();
        }

        return 0;
    }

    public static Object getFirst(Object checkedValues) {
        if (checkedValues == null) {
            return null;
        }

        if (checkedValues.getClass().isArray()) {
            if (Array.getLength(checkedValues) < 1) {
                return null;
            }

            return Array.get(checkedValues, 0);
        }

        if (checkedValues instanceof List) {
            if (((List) checkedValues).isEmpty()) {
                return null;
            }

            return ((List) checkedValues).get(0);
        }

        if (checkedValues instanceof Collection) {
            if (((Collection) checkedValues).isEmpty()) {
                return null;
            }

            return ((Collection) checkedValues).iterator().next();
        }

        if (checkedValues instanceof Map) {
            if (((Map) checkedValues).isEmpty()) {
                return null;
            }

            return ((Map) checkedValues).values().iterator().next();
        }

        ICheckProvider checkProvider = getCheckProvider(checkedValues);

        if (checkProvider != null) {
            return checkProvider.getFirstCheckedValue();
        }

        return null;
    }

    static ICheckProvider getCheckProvider(Object checkedValues) {
        if (checkedValues instanceof ICheckProvider) {
            return (ICheckProvider) checkedValues;

        }

        if (checkedValues instanceof IAdaptable) {
            ICheckProvider checkProvider = (ICheckProvider) ((IAdaptable) checkedValues)
                    .getAdapter(ICheckProvider.class, null);

            if (checkProvider != null) {
                return checkProvider;
            }
        }

        ICheckProvider checkProvider = (ICheckProvider) RcfacesContext
                .getCurrentInstance().getAdapterManager().getAdapter(
                        checkedValues, ICheckProvider.class, null);

        return checkProvider;
    }

}
