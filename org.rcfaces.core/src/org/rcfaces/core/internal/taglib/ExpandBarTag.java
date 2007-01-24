package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ExpandBarComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ExpandBarTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ExpandBarTag.class);

	private String asyncRenderMode;
	private String fontBold;
	private String fontItalic;
	private String fontName;
	private String fontSize;
	private String fontUnderline;
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
	private String scopeValue;
	private String scopeVar;
	private String collapseEffect;
	private String collapsedText;
	public String getComponentType() {
		return ExpandBarComponent.COMPONENT_TYPE;
	}

	public final String getAsyncRenderMode() {
		return asyncRenderMode;
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
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

	public final String getScopeValue() {
		return scopeValue;
	}

	public final void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final String getScopeVar() {
		return scopeVar;
	}

	public final void setScopeVar(String scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final String getCollapseEffect() {
		return collapseEffect;
	}

	public final void setCollapseEffect(String collapseEffect) {
		this.collapseEffect = collapseEffect;
	}

	public final String getCollapsedText() {
		return collapsedText;
	}

	public final void setCollapsedText(String collapsedText) {
		this.collapsedText = collapsedText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ExpandBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  collapsed='"+collapsed+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  collapseEffect='"+collapseEffect+"'");
			LOG.debug("  collapsedText='"+collapsedText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ExpandBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
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
			ListenersTools.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (scopeValue != null) {
				ValueBinding vb = application.createValueBinding(scopeValue);

				component.setScopeValue(vb);
		}

		if (scopeVar != null) {
			if (isValueReference(scopeVar)) {
				ValueBinding vb = application.createValueBinding(scopeVar);

				component.setScopeVar(vb);
			} else {
				component.setScopeVar(scopeVar);
			}
		}

		if (collapseEffect != null) {
			if (isValueReference(collapseEffect)) {
				ValueBinding vb = application.createValueBinding(collapseEffect);
				component.setCollapseEffect(vb);
			} else {
				component.setCollapseEffect(collapseEffect);
			}
		}

		if (collapsedText != null) {
			if (isValueReference(collapsedText)) {
				ValueBinding vb = application.createValueBinding(collapsedText);
				component.setCollapsedText(vb);
			} else {
				component.setCollapsedText(collapsedText);
			}
		}
	}

	public void release() {
		asyncRenderMode = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
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
		scopeValue = null;
		scopeVar = null;
		collapseEffect = null;
		collapsedText = null;

		super.release();
	}

}
