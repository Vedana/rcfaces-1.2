package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.LineBreakComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class LineBreakTag extends CameliaTag {

private static final Log LOG=LogFactory.getLog(LineBreakTag.class);
	private String styleClass;
	private String visible;
	private String hiddenMode;
	private String rendered;
	public String getComponentType() {
		return LineBreakComponent.COMPONENT_TYPE;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

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

	public final String getRendered() {
		return rendered;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof LineBreakComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'LineBreakComponent'.");
		}

		LineBreakComponent component = (LineBreakComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);

				component.setStyleClass(vb);
			} else {
				component.setStyleClass(styleClass);
			}
		}

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

		if (rendered != null) {
			if (isValueReference(rendered)) {
				ValueBinding vb = application.createValueBinding(rendered);
				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(rendered));
			}
		}
	}

	public void release() {
		styleClass = null;
		visible = null;
		hiddenMode = null;
		rendered = null;

		super.release();
	}

}
