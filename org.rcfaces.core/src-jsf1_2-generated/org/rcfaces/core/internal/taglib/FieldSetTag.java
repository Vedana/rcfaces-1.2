package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.FieldSetComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class FieldSetTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FieldSetTag.class);

	private ValueExpression headingZone;
	private ValueExpression headingLevel;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression textAlignment;
	private ValueExpression audioDescription;
	private ValueExpression verticalAlignment;
	private ValueExpression borderType;
	private ValueExpression imageURL;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression overStyleClass;
	public String getComponentType() {
		return FieldSetComponent.COMPONENT_TYPE;
	}

	public void setHeadingZone(ValueExpression headingZone) {
		this.headingZone = headingZone;
	}

	public void setHeadingLevel(ValueExpression headingLevel) {
		this.headingLevel = headingLevel;
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

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public void setTextAlignment(ValueExpression textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setAudioDescription(ValueExpression audioDescription) {
		this.audioDescription = audioDescription;
	}

	public void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
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

	public void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public void setOverStyleClass(ValueExpression overStyleClass) {
		this.overStyleClass = overStyleClass;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (FieldSetComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  headingZone='"+headingZone+"'");
			LOG.debug("  headingLevel='"+headingLevel+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  audioDescription='"+audioDescription+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  overStyleClass='"+overStyleClass+"'");
		}
		if ((uiComponent instanceof FieldSetComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FieldSetComponent'.");
		}

		super.setProperties(uiComponent);

		FieldSetComponent component = (FieldSetComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (headingZone != null) {
			if (headingZone.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_ZONE, headingZone);

			} else {
				component.setHeadingZone(getBool(headingZone.getExpressionString()));
			}
		}

		if (headingLevel != null) {
			if (headingLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_LEVEL, headingLevel);

			} else {
				component.setHeadingLevel(headingLevel.getExpressionString());
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

		if (textAlignment != null) {
			if (textAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGNMENT, textAlignment);

			} else {
				component.setTextAlignment(textAlignment.getExpressionString());
			}
		}

		if (audioDescription != null) {
			if (audioDescription.isLiteralText()==false) {
				component.setValueExpression(Properties.AUDIO_DESCRIPTION, audioDescription);

			} else {
				component.setAudioDescription(audioDescription.getExpressionString());
			}
		}

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

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

		if (scopeSaveValue != null) {
			if (scopeSaveValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue.getExpressionString()));
			}
		}

		if (scopeValue != null) {
			if (scopeValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);

			} else {
				component.setScopeValue(scopeValue.getExpressionString());
			}
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}

		if (overStyleClass != null) {
			if (overStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.OVER_STYLE_CLASS, overStyleClass);

			} else {
				component.setOverStyleClass(overStyleClass.getExpressionString());
			}
		}
	}

	public void release() {
		headingZone = null;
		headingLevel = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		text = null;
		textDirection = null;
		textAlignment = null;
		audioDescription = null;
		verticalAlignment = null;
		borderType = null;
		imageURL = null;
		imageHeight = null;
		imageWidth = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		overStyleClass = null;

		super.release();
	}

}
