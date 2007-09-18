package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImagePagerButtonComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ImagePagerButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImagePagerButtonTag.class);

	private String forValue;
	private String type;
	private String hideIfDisabled;
	public String getComponentType() {
		return ImagePagerButtonComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setHideIfDisabled(String hideIfDisabled) {
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
		Application application = facesContext.getApplication();

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (type != null) {
			if (isValueReference(type)) {
				ValueBinding vb = application.createValueBinding(type);
				component.setValueBinding(Properties.TYPE, vb);

			} else {
				component.setType(type);
			}
		}

		if (hideIfDisabled != null) {
			if (isValueReference(hideIfDisabled)) {
				ValueBinding vb = application.createValueBinding(hideIfDisabled);
				component.setValueBinding(Properties.HIDE_IF_DISABLED, vb);

			} else {
				component.setHideIfDisabled(getBool(hideIfDisabled));
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
