package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImagePagerButtonComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ImagePagerButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImagePagerButtonTag.class);

	private ValueExpression forValue;
	private ValueExpression type;
	private ValueExpression hideIfDisabled;
	public String getComponentType() {
		return ImagePagerButtonComponent.COMPONENT_TYPE;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public final void setType(ValueExpression type) {
		this.type = type;
	}

	public final void setHideIfDisabled(ValueExpression hideIfDisabled) {
		this.hideIfDisabled = hideIfDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImagePagerButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  type='"+type+"'");
			LOG.debug("  hideIfDisabled='"+hideIfDisabled+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ImagePagerButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImagePagerButtonComponent'.");
		}

		ImagePagerButtonComponent component = (ImagePagerButtonComponent) uiComponent;
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
