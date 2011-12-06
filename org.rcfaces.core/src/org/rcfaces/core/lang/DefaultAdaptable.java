/*
 * $Id$
 */
package org.rcfaces.core.lang;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DefaultAdaptable implements IAdaptable {

	public <T> T getAdapter(Class<T> adapter, Object parameter) {
		IAdapterManager adapterManager = RcfacesContext.getCurrentInstance()
				.getAdapterManager();

		return adapterManager.getAdapter(this, adapter, parameter);
	}
}
