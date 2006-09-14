package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.LineBreakComponent;

public class LineBreakTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(LineBreakTag.class);

	private String styleClass;
	private String hiddenMode;
	private String visible;
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

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getRendered() {
		return rendered;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (LineBreakComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  rendered='"+rendered+"'");
		}
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

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
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
		hiddenMode = null;
		visible = null;
		rendered = null;

		super.release();
	}

}
