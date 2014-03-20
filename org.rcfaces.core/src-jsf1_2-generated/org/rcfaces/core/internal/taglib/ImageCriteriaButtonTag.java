package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.ImageCriteriaButtonComponent;
import javax.faces.context.FacesContext;

public class ImageCriteriaButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageCriteriaButtonTag.class);

	private ValueExpression forValue;
	private ValueExpression type;
	private ValueExpression hideIfDisabled;
	public String getComponentType() {
		return ImageCriteriaButtonComponent.COMPONENT_TYPE;
	}

	public void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public void setType(ValueExpression type) {
		this.type = type;
	}

	public void setHideIfDisabled(ValueExpression hideIfDisabled) {
		this.hideIfDisabled = hideIfDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageCriteriaButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  type='"+type+"'");
			LOG.debug("  hideIfDisabled='"+hideIfDisabled+"'");
		}
		if ((uiComponent instanceof ImageCriteriaButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageCriteriaButtonComponent'.");
		}

		super.setProperties(uiComponent);

		ImageCriteriaButtonComponent component = (ImageCriteriaButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (type != null) {
			if (type.isLiteralText()==false) {
				component.setValueExpression(Properties.TYPE, type);

			} else {
				component.setType(type.getExpressionString());
			}
		}

		if (hideIfDisabled != null) {
			if (hideIfDisabled.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDE_IF_DISABLED, hideIfDisabled);

			} else {
				component.setHideIfDisabled(getBool(hideIfDisabled.getExpressionString()));
			}
		}
	}

	public void release() {
		forValue = null;
		type = null;
		hideIfDisabled = null;

		super.release();
	}

}
