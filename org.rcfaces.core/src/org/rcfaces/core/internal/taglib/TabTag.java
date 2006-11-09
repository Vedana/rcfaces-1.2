package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TabComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TabTag extends CardTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabTag.class);

	private String text;
	private String fontBold;
	private String fontItalic;
	private String fontName;
	private String fontSize;
	private String fontUnderline;
	private String disabled;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String imageURL;
	private String accessKey;
	public String getComponentType() {
		return TabComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
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

	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
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

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TabComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TabComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TabComponent'.");
		}

		TabComponent component = (TabComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (fontBold != null) {
			if (isValueReference(fontBold)) {
				ValueBinding vb = application.createValueBinding(fontBold);

				component.setFontBold(vb);
			} else {
				component.setFontBold(getBoolean(fontBold));
			}
		}

		if (fontItalic != null) {
			if (isValueReference(fontItalic)) {
				ValueBinding vb = application.createValueBinding(fontItalic);

				component.setFontItalic(vb);
			} else {
				component.setFontItalic(getBoolean(fontItalic));
			}
		}

		if (fontName != null) {
			if (isValueReference(fontName)) {
				ValueBinding vb = application.createValueBinding(fontName);

				component.setFontName(vb);
			} else {
				component.setFontName(fontName);
			}
		}

		if (fontSize != null) {
			if (isValueReference(fontSize)) {
				ValueBinding vb = application.createValueBinding(fontSize);

				component.setFontSize(vb);
			} else {
				component.setFontSize(fontSize);
			}
		}

		if (fontUnderline != null) {
			if (isValueReference(fontUnderline)) {
				ValueBinding vb = application.createValueBinding(fontUnderline);

				component.setFontUnderline(vb);
			} else {
				component.setFontUnderline(getBoolean(fontUnderline));
			}
		}

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);

				component.setDisabled(vb);
			} else {
				component.setDisabled(getBool(disabled));
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

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);

				component.setAccessKey(vb);
			} else {
				component.setAccessKey(accessKey);
			}
		}
	}

	public void release() {
		text = null;
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
