package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.component.AbstractSeparatorComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractSeparatorTag extends CameliaTag {

private static final Log LOG=LogFactory.getLog(AbstractSeparatorTag.class);
	private String visible;
	private String hiddenMode;
	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractSeparatorComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractSeparatorComponent'.");
		}

		AbstractSeparatorComponent component = (AbstractSeparatorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(visible));
			}
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
			}
		}
	}

	public void release() {
		visible = null;
		hiddenMode = null;

		super.release();
	}

}
