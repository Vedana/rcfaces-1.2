package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.internal.component.Properties;

public class ImageButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageButtonTag.class);

	private String imageURL;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String border;
	private String borderType;
	private String textPosition;
	private String imageHeight;
	private String imageWidth;
	public String getComponentType() {
		return ImageButtonComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getDisabledImageURL() {
		return disabledImageURL;
	}

	public final void setDisabledImageURL(String disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final String getHoverImageURL() {
		return hoverImageURL;
	}

	public final void setHoverImageURL(String hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final String getSelectedImageURL() {
		return selectedImageURL;
	}

	public final void setSelectedImageURL(String selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
	}

	public final String getImageHeight() {
		return imageHeight;
	}

	public final void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final String getImageWidth() {
		return imageWidth;
	}

	public final void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  disabledImageURL='"+disabledImageURL+"'");
			LOG.debug("  hoverImageURL='"+hoverImageURL+"'");
			LOG.debug("  selectedImageURL='"+selectedImageURL+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ImageButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageButtonComponent'.");
		}

		ImageButtonComponent component = (ImageButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
			}
		}

		if (disabledImageURL != null) {
			if (isValueReference(disabledImageURL)) {
				ValueBinding vb = application.createValueBinding(disabledImageURL);
				component.setValueBinding(Properties.DISABLED_IMAGE_URL, vb);

			} else {
				component.setDisabledImageURL(disabledImageURL);
			}
		}

		if (hoverImageURL != null) {
			if (isValueReference(hoverImageURL)) {
				ValueBinding vb = application.createValueBinding(hoverImageURL);
				component.setValueBinding(Properties.HOVER_IMAGE_URL, vb);

			} else {
				component.setHoverImageURL(hoverImageURL);
			}
		}

		if (selectedImageURL != null) {
			if (isValueReference(selectedImageURL)) {
				ValueBinding vb = application.createValueBinding(selectedImageURL);
				component.setValueBinding(Properties.SELECTED_IMAGE_URL, vb);

			} else {
				component.setSelectedImageURL(selectedImageURL);
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);
				component.setValueBinding(Properties.BORDER, vb);

			} else {
				component.setBorder(getBool(border));
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);
				component.setValueBinding(Properties.BORDER_TYPE, vb);

			} else {
				component.setBorderType(borderType);
			}
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);
				component.setValueBinding(Properties.TEXT_POSITION, vb);

			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);
				component.setValueBinding(Properties.IMAGE_HEIGHT, vb);

			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);
				component.setValueBinding(Properties.IMAGE_WIDTH, vb);

			} else {
				component.setImageWidth(getInt(imageWidth));
			}
		}
	}

	public void release() {
		imageURL = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		border = null;
		borderType = null;
		textPosition = null;
		imageHeight = null;
		imageWidth = null;

		super.release();
	}

}
