package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.DataPagerComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DataPagerTag extends AbstractBasicTag {

private static final Log LOG=LogFactory.getLog(DataPagerTag.class);
	private String forVal;
	public String getComponentType() {
		return DataPagerComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forVal;
	}

	public final void setFor(String forVal) {
		this.forVal = forVal;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DataPagerComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataPagerComponent'.");
		}

		DataPagerComponent component = (DataPagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (forVal != null) {
			if (isValueReference(forVal)) {
				ValueBinding vb = application.createValueBinding(forVal);
				component.setFor(vb);
			} else {
				component.setFor(forVal);
			}
		}
	}

	public void release() {
		forVal = null;

		super.release();
	}

}
