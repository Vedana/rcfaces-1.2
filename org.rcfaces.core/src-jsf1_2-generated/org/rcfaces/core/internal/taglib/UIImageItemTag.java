package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.UIImageItemComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class UIImageItemTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(UIImageItemTag.class);

	private ValueExpression visible;
	private ValueExpression toolTipText;
	private ValueExpression disabledImageURL;
	private ValueExpression hoverImageURL;
	private ValueExpression selectedImageURL;
	private ValueExpression imageURL;
	private ValueExpression rendered;
	private ValueExpression alternateText;
	public String getComponentType() {
		return UIImageItemComponent.COMPONENT_TYPE;
	}

	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setToolTipText(ValueExpression toolTipText) {
		this.toolTipText = toolTipText;
	}

	public final void setDisabledImageURL(ValueExpression disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final void setHoverImageURL(ValueExpression hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final void setSelectedImageURL(ValueExpression selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public final void setRendered(ValueExpression rendered) {
		this.rendered = rendered;
	}

	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
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
			LOG.debug("  alternateText='"+alternateText+"'");
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

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (toolTipText != null) {
			if (toolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_TEXT, toolTipText);

			} else {
				component.setToolTipText(toolTipText.getExpressionString());
			}
		}

		if (disabledImageURL != null) {
			if (disabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_IMAGE_URL, disabledImageURL);

			} else {
				component.setDisabledImageURL(disabledImageURL.getExpressionString());
			}
		}

		if (hoverImageURL != null) {
			if (hoverImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.HOVER_IMAGE_URL, hoverImageURL);

			} else {
				component.setHoverImageURL(hoverImageURL.getExpressionString());
			}
		}

		if (selectedImageURL != null) {
			if (selectedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_IMAGE_URL, selectedImageURL);

			} else {
				component.setSelectedImageURL(selectedImageURL.getExpressionString());
			}
		}

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (rendered != null) {
			if (rendered.isLiteralText()==false) {
				component.setValueExpression(Properties.RENDERED, rendered);

			} else {
				component.setRendered(getBool(rendered.getExpressionString()));
			}
		}
		
		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
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
		alternateText = null;

		super.release();
	}

}
