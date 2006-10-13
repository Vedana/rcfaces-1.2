package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.ImagePagerButtonComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

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

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final String getHideIfDisabled() {
		return hideIfDisabled;
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
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImagePagerButtonComponent'.");
		}

		ImagePagerButtonComponent component = (ImagePagerButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);

				component.setFor(vb);
			} else {
				component.setFor(forValue);
			}
		}

		if (type != null) {
			if (isValueReference(type)) {
				ValueBinding vb = application.createValueBinding(type);
				component.setType(vb);
			} else {
				component.setType(type);
			}
		}

		if (hideIfDisabled != null) {
			if (isValueReference(hideIfDisabled)) {
				ValueBinding vb = application.createValueBinding(hideIfDisabled);
				component.setHideIfDisabled(vb);
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
