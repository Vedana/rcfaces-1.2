package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.component.TextComponent;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.ITextCapability;
import javax.faces.component.UIViewRoot;

public class TextTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextTag.class);

	private String text;
	private String textDirection;
	private String fontBold;
	private String fontItalic;
	private String fontName;
	private String fontSize;
	private String fontUnderline;
	private String textAlignment;
	private String accessKey;
	private String forValue;
	private String type;
	public String getComponentType() {
		return TextComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextDirection() {
		return textDirection;
	}

	public final void setTextDirection(String textDirection) {
		this.textDirection = textDirection;
	}

	public final String getFontBold() {
		return fontBold;
	}

	public final void setFontBold(String fontBold) {
		this.fontBold = fontBold;
	}

	public final String getFontItalic() {
		return fontItalic;
	}

	public final void setFontItalic(String fontItalic) {
		this.fontItalic = fontItalic;
	}

	public final String getFontName() {
		return fontName;
	}

	public final void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public final String getFontSize() {
		return fontSize;
	}

	public final void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public final String getFontUnderline() {
		return fontUnderline;
	}

	public final void setFontUnderline(String fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public final String getTextAlignment() {
		return textAlignment;
	}

	public final void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  type='"+type+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextComponent'.");
		}

		TextComponent component = (TextComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
			}
		}

		if (textDirection != null) {
			if (isValueReference(textDirection)) {
				ValueBinding vb = application.createValueBinding(textDirection);
				component.setValueBinding(Properties.TEXT_DIRECTION, vb);

			} else {
				component.setTextDirection(getInt(textDirection));
			}
		}

		if (fontBold != null) {
			if (isValueReference(fontBold)) {
				ValueBinding vb = application.createValueBinding(fontBold);
				component.setValueBinding(Properties.FONT_BOLD, vb);

			} else {
				component.setFontBold(getBoolean(fontBold));
			}
		}

		if (fontItalic != null) {
			if (isValueReference(fontItalic)) {
				ValueBinding vb = application.createValueBinding(fontItalic);
				component.setValueBinding(Properties.FONT_ITALIC, vb);

			} else {
				component.setFontItalic(getBoolean(fontItalic));
			}
		}

		if (fontName != null) {
			if (isValueReference(fontName)) {
				ValueBinding vb = application.createValueBinding(fontName);
				component.setValueBinding(Properties.FONT_NAME, vb);

			} else {
				component.setFontName(fontName);
			}
		}

		if (fontSize != null) {
			if (isValueReference(fontSize)) {
				ValueBinding vb = application.createValueBinding(fontSize);
				component.setValueBinding(Properties.FONT_SIZE, vb);

			} else {
				component.setFontSize(fontSize);
			}
		}

		if (fontUnderline != null) {
			if (isValueReference(fontUnderline)) {
				ValueBinding vb = application.createValueBinding(fontUnderline);
				component.setValueBinding(Properties.FONT_UNDERLINE, vb);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline));
			}
		}

		if (textAlignment != null) {
			if (isValueReference(textAlignment)) {
				ValueBinding vb = application.createValueBinding(textAlignment);
				component.setValueBinding(Properties.TEXT_ALIGNMENT, vb);

			} else {
				component.setTextAlignment(textAlignment);
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);
				component.setValueBinding(Properties.ACCESS_KEY, vb);

			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (type != null) {
			if (isValueReference(type)) {
				ValueBinding vb = application.createValueBinding(type);
				component.setValueBinding(Properties.TYPE, vb);

			} else {
				component.setType(type);
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
		textAlignment = null;
		accessKey = null;
		forValue = null;
		type = null;

		super.release();
	}

	protected int getDoStartValue() {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		if (text == null && getBodyContent() != null) {
			String content = getBodyContent().getString();
			if (content != null && content.length() > 0) {
				content = content.trim();
				if (content.length() > 0) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("  [body of tag] text='"+content+"'");
					}
					((ITextCapability)getComponentInstance()).setText(content);
				}
			}
		}
		return super.doEndTag();
	}
}
