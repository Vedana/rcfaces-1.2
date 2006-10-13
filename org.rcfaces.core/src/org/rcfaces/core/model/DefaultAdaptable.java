/*
 * $Id$
 */
package org.rcfaces.core.model;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DefaultAdaptable implements IAdaptable {
    private static final String REVISION = "$Revision$";

    public Object getAdapter(Class adapter) {
        IAdapterManager adapterManager = RcfacesContext.getCurrentInstance()
                .getAdapterManager();

        return adapterManager.getAdapter(this, adapter);
    }
}
