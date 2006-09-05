package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.ImageComponent;
import javax.faces.application.Application;

public class ImageTag extends AbstractOutputTag {


	private static final Log LOG=LogFactory.getLog(ImageTag.class);

	private String imageURL;
	public String getComponentType() {
		return ImageComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ImageComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageComponent'.");
		}

		ImageComponent component = (ImageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
			}
		}
	}

	public void release() {
		imageURL = null;

		super.release();
	}

}
