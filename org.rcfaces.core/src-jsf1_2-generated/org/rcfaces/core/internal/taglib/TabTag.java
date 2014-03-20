package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TabComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TabTag extends CardTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabTag.class);

	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression disabled;
	private ValueExpression disabledImageURL;
	private ValueExpression hoverImageURL;
	private ValueExpression selectedImageURL;
	private ValueExpression imageURL;
	private ValueExpression accessKey;
	public String getComponentType() {
		return TabComponent.COMPONENT_TYPE;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public void setFontBold(ValueExpression fontBold) {
		this.fontBold = fontBold;
	}

	public void setFontItalic(ValueExpression fontItalic) {
		this.fontItalic = fontItalic;
	}

	public void setFontName(ValueExpression fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontUnderline(ValueExpression fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public void setDisabledImageURL(ValueExpression disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public void setHoverImageURL(ValueExpression hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public void setSelectedImageURL(ValueExpression selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TabComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  disabledImageURL='"+disabledImageURL+"'");
			LOG.debug("  hoverImageURL='"+hoverImageURL+"'");
			LOG.debug("  selectedImageURL='"+selectedImageURL+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
		}
		if ((uiComponent instanceof TabComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TabComponent'.");
		}

		super.setProperties(uiComponent);

		TabComponent component = (TabComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textDirection != null) {
			if (textDirection.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DIRECTION, textDirection);

			} else {
				component.setTextDirection(getInt(textDirection.getExpressionString()));
			}
		}

		if (fontBold != null) {
			if (fontBold.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_BOLD, fontBold);

			} else {
				component.setFontBold(getBoolean(fontBold.getExpressionString()));
			}
		}

		if (fontItalic != null) {
			if (fontItalic.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_ITALIC, fontItalic);

			} else {
				component.setFontItalic(getBoolean(fontItalic.getExpressionString()));
			}
		}

		if (fontName != null) {
			if (fontName.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_NAME, fontName);

			} else {
				component.setFontName(fontName.getExpressionString());
			}
		}

		if (fontSize != null) {
			if (fontSize.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE, fontSize);

			} else {
				component.setFontSize(fontSize.getExpressionString());
			}
		}

		if (fontUnderline != null) {
			if (fontUnderline.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_UNDERLINE, fontUnderline);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline.getExpressionString()));
			}
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
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

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}
	}

	public void release() {
		text = null;
		textDirection = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		disabled = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		imageURL = null;
		accessKey = null;

		super.release();
	}

}
