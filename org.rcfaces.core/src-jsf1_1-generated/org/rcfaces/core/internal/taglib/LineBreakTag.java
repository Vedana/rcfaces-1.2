package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.LineBreakComponent;
import javax.faces.context.FacesContext;

public class LineBreakTag extends CameliaTag implements Tag {


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

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (LineBreakComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  rendered='"+rendered+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof LineBreakComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'LineBreakComponent'.");
		}

		LineBreakComponent component = (LineBreakComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);
				component.setValueBinding(Properties.VISIBLE, vb);

			} else {
				component.setVisible(getBool(visible));
			}
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);
				component.setValueBinding(Properties.HIDDEN_MODE, vb);

			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (rendered != null) {
			if (isValueReference(rendered)) {
				ValueBinding vb = application.createValueBinding(rendered);
				component.setValueBinding(Properties.RENDERED, vb);

			} else {
				component.setRendered(getBool(rendered));
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
