package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.UIImageItemComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class UIImageItemTag extends AbstractItemTag {

private static final Log LOG=LogFactory.getLog(UIImageItemTag.class);
	private String visible;
	private String hiddenMode;
	private String toolTipText;
	private String imageURL;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String rendered;
	public String getComponentType() {
		return UIImageItemComponent.COMPONENT_TYPE;
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

	public final String getToolTipText() {
		return toolTipText;
	}

	public final void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
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

	public final String getRendered() {
		return rendered;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof UIImageItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'UIImageItemComponent'.");
		}

		UIImageItemComponent component = (UIImageItemComponent) uiComponent;
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

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);

				component.setToolTipText(vb);
			} else {
				component.setToolTipText(toolTipText);
			}
		}

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
			}
		}

		if (disabledImageURL != null) {
			if (isValueReference(disabledImageURL)) {
				ValueBinding vb = application.createValueBinding(disabledImageURL);

				component.setDisabledImageURL(vb);
			} else {
				component.setDisabledImageURL(disabledImageURL);
			}
		}

		if (hoverImageURL != null) {
			if (isValueReference(hoverImageURL)) {
				ValueBinding vb = application.createValueBinding(hoverImageURL);

				component.setHoverImageURL(vb);
			} else {
				component.setHoverImageURL(hoverImageURL);
			}
		}

		if (selectedImageURL != null) {
			if (isValueReference(selectedImageURL)) {
				ValueBinding vb = application.createValueBinding(selectedImageURL);

				component.setSelectedImageURL(vb);
			} else {
				component.setSelectedImageURL(selectedImageURL);
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
		visible = null;
		hiddenMode = null;
		toolTipText = null;
		imageURL = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		rendered = null;

		super.release();
	}

}
