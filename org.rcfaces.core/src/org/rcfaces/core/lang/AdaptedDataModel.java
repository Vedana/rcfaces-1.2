package org.rcfaces.core.lang;

import javax.faces.model.DataModel;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;

public abstract class AdaptedDataModel extends DataModel implements IAdaptable {

	@Override
	public Object getAdapter(Class adapter, Object parameter) {
		if (adapter.isAssignableFrom(getClass())) {
			return this;
		}

		IAdapterManager adapterManager = RcfacesContext.getCurrentInstance()
				.getAdapterManager();

		return adapterManager.getAdapter(this, adapter, parameter);
	}

}
