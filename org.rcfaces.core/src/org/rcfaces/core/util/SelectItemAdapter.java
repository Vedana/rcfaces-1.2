/*
 * $Id$
 */
package org.rcfaces.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.lang.IAdaptable;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemAdapter {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SelectItemAdapter.class);

    private static final SelectItem[] SELECT_ITEM_EMPTY_ARRAY = new SelectItem[0];

    public static SelectItem[] adapt(Object value[]) {
        return adapt(value, null, null);
    }

    public static SelectItem[] adapt(Object value[], Object parameter,
            FacesContext facesContext) {
        if (value == null || value.length == 0) {
            return SELECT_ITEM_EMPTY_ARRAY;
        }

        return adapt(Arrays.asList(value), parameter, facesContext);
    }

    public static SelectItem[] adapt(Collection collection) {
        return adapt(collection, null, null);
    }

    public static SelectItem[] adapt(Collection collection, Object parameter,
            FacesContext facesContext) {
        if (collection == null || collection.isEmpty()) {
            return SELECT_ITEM_EMPTY_ARRAY;
        }

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        IAdapterManager adapterManager = RcfacesContext.getInstance(
                facesContext).getAdapterManager();

        List ret = null;

        int count = collection.size();
        for (Iterator it = collection.iterator(); it.hasNext(); count--) {
            Object value = it.next();

            if (value == null) {
                continue;
            }

            if (value instanceof SelectItem) {
                if (ret == null) {
                    ret = new ArrayList(count);
                }
                ret.add(value);
                continue;
            }

            if (value instanceof IAdaptable) {
                IAdaptable adaptable = (IAdaptable) value;

                SelectItem selectItem = (SelectItem) adaptable.getAdapter(
                        SelectItem.class, parameter);
                if (selectItem != null) {
                    if (ret == null) {
                        ret = new ArrayList(count);
                    }
                    ret.add(selectItem);
                    continue;
                }
            }

            SelectItem selectItem = (SelectItem) adapterManager.getAdapter(
                    value, SelectItem.class, parameter);
            if (selectItem != null) {
                if (ret == null) {
                    ret = new ArrayList(count);
                }
                ret.add(selectItem);
                continue;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Can not convert value '" + value
                        + "' to selectItem !");
            }
        }

        if (ret == null) {
            return SELECT_ITEM_EMPTY_ARRAY;
        }

        return (SelectItem[]) ret.toArray(new SelectItem[ret.size()]);
    }
}
