package org.rcfaces.core.lang;

import javax.faces.model.DataModel;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;

/**
 * 
 * @author Olivier Oeuillot
 */
public abstract class AdaptedDataModel extends DataModel implements IAdaptable {

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
