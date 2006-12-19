package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICollapsableCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;

/**
 * <p>The expandBar Component is a container that can be collapsed to show only a title bar. Expand Bars can be managed by group : only one element of the group is expanded.</p>
 * <p>The expandBar Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Background Image</li>
 * <li>Events Handling</li>
 * <li>Contextual Menu</li>
 * <li>Async Render (AJAX)</li>
 * </ul>
 * </p>
 */
public class ExpandBarComponent extends AbstractOutputComponent implements 
	IAsyncRenderModeCapability,
	IFontCapability,
	IDisabledCapability,
	IReadOnlyCapability,
	ITextCapability,
	ITextAlignmentCapability,
	ICollapsableCapability,
	IBorderCapability,
	IAccessKeyCapability,
	ITabIndexCapability,
	IRadioGroupCapability,
	IFocusBlurEventCapability,
	ISelectionEventCapability,
	ILoadEventCapability,
	IVariableScopeCapability,
	IAsyncRenderComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.expandBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","fontUnderline","scopeValue","focusListener","collapseEffect","asyncRenderMode","disabled","fontSize","accessKey","groupName","fontItalic","fontBold","loadListener","text","scopeVar","textAlignment","collapsed","collapsedText","border","readOnly","blurListener","tabIndex","fontName"}));
	}

	public ExpandBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ExpandBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, null, asyncRenderMode)).intValue());
		
	}

	public final int getAsyncRenderMode() {
		return getAsyncRenderMode(null);
	}

	/**
	 * See {@link #getAsyncRenderMode() getAsyncRenderMode()} for more details
	 */
	public final int getAsyncRenderMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ASYNC_RENDER_MODE,0, facesContext);
	}

	public final void setAsyncRenderMode(int asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	/**
	 * See {@link #setAsyncRenderMode(int) setAsyncRenderMode(int)} for more details
	 */
	public final void setAsyncRenderMode(ValueBinding asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public final java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public final java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_BOLD, facesContext);
	}

	public final void setFontBold(java.lang.Boolean fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	/**
	 * See {@link #setFontBold(Boolean) setFontBold(Boolean)} for more details
	 */
	public final void setFontBold(ValueBinding fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public final java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public final java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_ITALIC, facesContext);
	}

	public final void setFontItalic(java.lang.Boolean fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	/**
	 * See {@link #setFontItalic(Boolean) setFontItalic(Boolean)} for more details
	 */
	public final void setFontItalic(ValueBinding fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public final java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public final java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAME, facesContext);
	}

	public final void setFontName(java.lang.String fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	/**
	 * See {@link #setFontName(String) setFontName(String)} for more details
	 */
	public final void setFontName(ValueBinding fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public final java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public final java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	public final void setFontSize(java.lang.String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	/**
	 * See {@link #setFontSize(String) setFontSize(String)} for more details
	 */
	public final void setFontSize(ValueBinding fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public final java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public final java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_UNDERLINE, facesContext);
	}

	public final void setFontUnderline(java.lang.Boolean fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	/**
	 * See {@link #setFontUnderline(Boolean) setFontUnderline(Boolean)} for more details
	 */
	public final void setFontUnderline(ValueBinding fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public final boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public final boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	public final void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	/**
	 * See {@link #setDisabled(boolean) setDisabled(boolean)} for more details
	 */
	public final void setDisabled(ValueBinding disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	public final void setText(java.lang.String text) {
		setValue(text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public final void setText(ValueBinding text) {
		setValue(text);
	}

	public final java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public final java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	public final void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	/**
	 * See {@link #setTextAlignment(String) setTextAlignment(String)} for more details
	 */
	public final void setTextAlignment(ValueBinding textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final boolean isCollapsed() {
		return isCollapsed(null);
	}

	/**
	 * See {@link #isCollapsed() isCollapsed()} for more details
	 */
	public final boolean isCollapsed(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.COLLAPSED, false, facesContext);
	}

	public final void setCollapsed(boolean collapsed) {
		engine.setProperty(Properties.COLLAPSED, collapsed);
	}

	/**
	 * See {@link #setCollapsed(boolean) setCollapsed(boolean)} for more details
	 */
	public final void setCollapsed(ValueBinding collapsed) {
		engine.setProperty(Properties.COLLAPSED, collapsed);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public final java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	public final void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	/**
	 * See {@link #setAccessKey(String) setAccessKey(String)} for more details
	 */
	public final void setAccessKey(ValueBinding accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public final java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public final java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return engine.getIntegerProperty(Properties.TAB_INDEX, facesContext);
	}

	public final void setTabIndex(java.lang.Integer tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
	}

	/**
	 * See {@link #setTabIndex(Integer) setTabIndex(Integer)} for more details
	 */
	public final void setTabIndex(ValueBinding tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	/**
	 * See {@link #setGroupName(String) setGroupName(String)} for more details
	 */
	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final void addBlurListener(org.rcfaces.core.event.IBlurListener listener) {
		addFacesListener(listener);
	}

	public final void removeBlurListener(org.rcfaces.core.event.IBlurListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listBlurListeners() {
		return getFacesListeners(org.rcfaces.core.event.IBlurListener.class);
	}

	public final void addFocusListener(org.rcfaces.core.event.IFocusListener listener) {
		addFacesListener(listener);
	}

	public final void removeFocusListener(org.rcfaces.core.event.IFocusListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listFocusListeners() {
		return getFacesListeners(org.rcfaces.core.event.IFocusListener.class);
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final void addLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		addFacesListener(listener);
	}

	public final void removeLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listLoadListeners() {
		return getFacesListeners(org.rcfaces.core.event.ILoadListener.class);
	}

	public final javax.faces.el.ValueBinding getScopeValue() {
		return getScopeValue(null);
	}

	/**
	 * See {@link #getScopeValue() getScopeValue()} for more details
	 */
	public final javax.faces.el.ValueBinding getScopeValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValueBindingProperty(Properties.SCOPE_VALUE);
	}

	public final void setScopeValue(javax.faces.el.ValueBinding scopeValue) {
		engine.setProperty(Properties.SCOPE_VALUE, scopeValue);
	}

	public final java.lang.String getScopeVar() {
		return getScopeVar(null);
	}

	/**
	 * See {@link #getScopeVar() getScopeVar()} for more details
	 */
	public final java.lang.String getScopeVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SCOPE_VAR, facesContext);
	}

	public final void setScopeVar(java.lang.String scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	/**
	 * See {@link #setScopeVar(String) setScopeVar(String)} for more details
	 */
	public final void setScopeVar(ValueBinding scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	/**
	 * Returns a string value indicating the effect to use when transionning from one state to the other.
	 * @return the effect : slideUp|slideUpTrans
	 */
	public final String getCollapseEffect() {
		return getCollapseEffect(null);
	}

	/**
	 * Returns a string value indicating the effect to use when transionning from one state to the other.
	 * @return the effect : slideUp|slideUpTrans
	 */
	public final String getCollapseEffect(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLLAPSE_EFFECT, facesContext);
	}

	/**
	 * Sets a string value indicating the effect to use when transionning from one state to the other.
	 * @param collapseEffect the effect : slideUp|slideUpTrans
	 */
	public final void setCollapseEffect(String collapseEffect) {
		engine.setProperty(Properties.COLLAPSE_EFFECT, collapseEffect);
	}

	/**
	 * Sets a string value indicating the effect to use when transionning from one state to the other.
	 * @param collapseEffect the effect : slideUp|slideUpTrans
	 */
	public final void setCollapseEffect(ValueBinding collapseEffect) {
		engine.setProperty(Properties.COLLAPSE_EFFECT, collapseEffect);
	}

	/**
	 * Returns <code>true</code> if the attribute "collapseEffect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCollapseEffectSetted() {
		return engine.isPropertySetted(Properties.COLLAPSE_EFFECT);
	}

	/**
	 * Returns a string value specifying the text to show when the component is collapsed.
	 * @return text
	 */
	public final String getCollapsedText() {
		return getCollapsedText(null);
	}

	/**
	 * Returns a string value specifying the text to show when the component is collapsed.
	 * @return text
	 */
	public final String getCollapsedText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLLAPSED_TEXT, facesContext);
	}

	/**
	 * Sets a string value specifying the text to show when the component is collapsed.
	 * @param collapsedText text
	 */
	public final void setCollapsedText(String collapsedText) {
		engine.setProperty(Properties.COLLAPSED_TEXT, collapsedText);
	}

	/**
	 * Sets a string value specifying the text to show when the component is collapsed.
	 * @param collapsedText text
	 */
	public final void setCollapsedText(ValueBinding collapsedText) {
		engine.setProperty(Properties.COLLAPSED_TEXT, collapsedText);
	}

	/**
	 * Returns <code>true</code> if the attribute "collapsedText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCollapsedTextSetted() {
		return engine.isPropertySetted(Properties.COLLAPSED_TEXT);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
