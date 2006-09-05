package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ExpandBarComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ExpandBarTag extends AbstractOutputTag {


	private static final Log LOG=LogFactory.getLog(ExpandBarTag.class);

	private String asyncRenderMode;
	private String fontSize;
	private String fontBold;
	private String fontUnderline;
	private String fontItalic;
	private String fontName;
	private String disabled;
	private String readOnly;
	private String text;
	private String textAlignment;
	private String collapsed;
	private String border;
	private String accessKey;
	private String tabIndex;
	private String groupName;
	private String blurListeners;
	private String focusListeners;
	private String selectionListeners;
	private String loadListeners;
	private String collapseEffect;
	public String getComponentType() {
		return ExpandBarComponent.COMPONENT_TYPE;
	}

	public final String getAsyncRenderMode() {
		return asyncRenderMode;
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public final String getFontSize() {
		return fontSize;
	}

	public final void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public final String getFontBold() {
		return fontBold;
	}

	public final void setFontBold(String fontBold) {
		this.fontBold = fontBold;
	}

	public final String getFontUnderline() {
		return fontUnderline;
	}

	public final void setFontUnderline(String fontUnderline) {
		this.fontUnderline = fontUnderline;
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

	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextAlignment() {
		return textAlignment;
	}

	public final void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final String getCollapsed() {
		return collapsed;
	}

	public final void setCollapsed(String collapsed) {
		this.collapsed = collapsed;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getTabIndex() {
		return tabIndex;
	}

	public final void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getBlurListener() {
		return blurListeners;
	}

	public final void setBlurListener(String blurListeners) {
		this.blurListeners = blurListeners;
	}

	public final String getFocusListener() {
		return focusListeners;
	}

	public final void setFocusListener(String focusListeners) {
		this.focusListeners = focusListeners;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final String getCollapseEffect() {
		return collapseEffect;
	}

	public final void setCollapseEffect(String collapseEffect) {
		this.collapseEffect = collapseEffect;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ExpandBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  collapsed='"+collapsed+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  collapseEffect='"+collapseEffect+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ExpandBarComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ExpandBarComponent'.");
		}

		ExpandBarComponent component = (ExpandBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (asyncRenderMode != null) {
			if (isValueReference(asyncRenderMode)) {
				ValueBinding vb = application.createValueBinding(asyncRenderMode);

				component.setAsyncRenderMode(vb);
			} else {
				component.setAsyncRenderMode(asyncRenderMode);
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

		if (fontBold != null) {
			if (isValueReference(fontBold)) {
				ValueBinding vb = application.createValueBinding(fontBold);

				component.setFontBold(vb);
			} else {
				component.setFontBold(getBoolean(fontBold));
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

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);

				component.setDisabled(vb);
			} else {
				component.setDisabled(getBool(disabled));
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (textAlignment != null) {
			if (isValueReference(textAlignment)) {
				ValueBinding vb = application.createValueBinding(textAlignment);

				component.setTextAlignment(vb);
			} else {
				component.setTextAlignment(textAlignment);
			}
		}

		if (collapsed != null) {
			if (isValueReference(collapsed)) {
				ValueBinding vb = application.createValueBinding(collapsed);

				component.setCollapsed(vb);
			} else {
				component.setCollapsed(getBool(collapsed));
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
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

		if (tabIndex != null) {
			if (isValueReference(tabIndex)) {
				ValueBinding vb = application.createValueBinding(tabIndex);

				component.setTabIndex(vb);
			} else {
				component.setTabIndex(getInteger(tabIndex));
			}
		}

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);

				component.setGroupName(vb);
			} else {
				component.setGroupName(groupName);
			}
		}

		if (blurListeners != null) {
			parseActionListener(application, component, BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			parseActionListener(application, component, FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (loadListeners != null) {
			parseActionListener(application, component, LOAD_LISTENER_TYPE, loadListeners);
		}

		if (collapseEffect != null) {
			if (isValueReference(collapseEffect)) {
				ValueBinding vb = application.createValueBinding(collapseEffect);
				component.setCollapseEffect(vb);
			} else {
				component.setCollapseEffect(collapseEffect);
			}
		}
	}

	public void release() {
		asyncRenderMode = null;
		fontSize = null;
		fontBold = null;
		fontUnderline = null;
		fontItalic = null;
		fontName = null;
		disabled = null;
		readOnly = null;
		text = null;
		textAlignment = null;
		collapsed = null;
		border = null;
		accessKey = null;
		tabIndex = null;
		groupName = null;
		blurListeners = null;
		focusListeners = null;
		selectionListeners = null;
		loadListeners = null;
		collapseEffect = null;

		super.release();
	}

}
