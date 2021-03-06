package org.rcfaces.core.lang;

import javax.faces.model.ArrayDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AdaptedArrayDataModel extends ArrayDataModel implements
        IAdaptable {

    private static final Log LOG = LogFactory
            .getLog(AdaptedArrayDataModel.class);

    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Class<T> adapter, Object parameter) {
        if (adapter.isAssignableFrom(getClass())) {
            return (T) this;
        }

        IAdapterManager adapterManager = RcfacesContext.getCurrentInstance()
                .getAdapterManager();

        return adapterManager.getAdapter(this, adapter, parameter);
    }

}
