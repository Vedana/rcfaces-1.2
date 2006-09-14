package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataPagerComponent;

public class DataPagerTag extends AbstractBasicTag implements Tag {


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
		if (LOG.isDebugEnabled()) {
			if (DataPagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forVal='"+forVal+"'");
		}
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
