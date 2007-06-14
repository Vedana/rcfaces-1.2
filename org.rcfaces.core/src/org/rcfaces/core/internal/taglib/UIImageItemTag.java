package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.UIImageItemComponent;
import javax.faces.application.Application;

public class UIImageItemTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(UIImageItemTag.class);

	private String visible;
	private String toolTipText;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String imageURL;
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

	public final String getToolTipText() {
		return toolTipText;
	}

	public final void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
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

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getRendered() {
		return rendered;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (UIImageItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  disabledImageURL='"+disabledImageURL+"'");
			LOG.debug("  hoverImageURL='"+hoverImageURL+"'");
			LOG.debug("  selectedImageURL='"+selectedImageURL+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  rendered='"+rendered+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof UIImageItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'UIImageItemComponent'.");
		}

		UIImageItemComponent component = (UIImageItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);
				component.setValueBinding(Properties.VISIBLE, vb);

			} else {
				component.setVisible(getBool(visible));
			}
		}

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);
				component.setValueBinding(Properties.TOOL_TIP_TEXT, vb);

			} else {
				component.setToolTipText(toolTipText);
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

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
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
		visible = null;
		toolTipText = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		imageURL = null;
		rendered = null;

		super.release();
	}

}
