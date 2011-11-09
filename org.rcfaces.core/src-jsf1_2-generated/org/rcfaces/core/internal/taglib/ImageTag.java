package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ImageTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageTag.class);

	private ValueExpression imageURL;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression alternateText;
	private ValueExpression filterProperties;
	private ValueExpression toolTipId;
	public String getComponentType() {
		return ImageComponent.COMPONENT_TYPE;
	}

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public void setToolTipId(ValueExpression toolTipId) {
		this.toolTipId = toolTipId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  toolTipId='"+toolTipId+"'");
		}
		if ((uiComponent instanceof ImageComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageComponent'.");
		}

		super.setProperties(uiComponent);

		ImageComponent component = (ImageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (imageHeight != null) {
			if (imageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_HEIGHT, imageHeight);

			} else {
				component.setImageHeight(getInt(imageHeight.getExpressionString()));
			}
		}

		if (imageWidth != null) {
			if (imageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_WIDTH, imageWidth);

			} else {
				component.setImageWidth(getInt(imageWidth.getExpressionString()));
			}
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (filterProperties != null) {
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (toolTipId != null) {
			if (toolTipId.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_ID, toolTipId);

			} else {
				component.setToolTipId(toolTipId.getExpressionString());
			}
		}
	}

	public void release() {
		imageURL = null;
		imageHeight = null;
		imageWidth = null;
		alternateText = null;
		filterProperties = null;
		toolTipId = null;

		super.release();
	}

}
