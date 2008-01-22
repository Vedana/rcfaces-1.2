package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ImageButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageButtonTag.class);

	private ValueExpression imageURL;
	private ValueExpression disabledImageURL;
	private ValueExpression hoverImageURL;
	private ValueExpression selectedImageURL;
	private ValueExpression border;
	private ValueExpression borderType;
	private ValueExpression textPosition;
	private ValueExpression alternateText;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	public String getComponentType() {
		return ImageButtonComponent.COMPONENT_TYPE;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
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

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public final void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public final void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
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
			LOG.debug("  alternateText='"+alternateText+"'");
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

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
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

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

		if (textPosition != null) {
			if (textPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_POSITION, textPosition);

			} else {
				component.setTextPosition(textPosition.getExpressionString());
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
		alternateText = null;

		super.release();
	}

}
