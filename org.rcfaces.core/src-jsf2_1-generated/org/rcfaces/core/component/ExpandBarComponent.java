package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IOverStyleClassCapability;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IExpandEventCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.ICollapsableCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.ITextCapability;

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
	IExpandEventCapability,
	IAsyncRenderModeCapability,
	IFontCapability,
	IDisabledCapability,
	IReadOnlyCapability,
	ITextCapability,
	ITextDirectionCapability,
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
	IOverStyleClassCapability,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(ExpandBarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.expandBar";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractOutputComponent.BEHAVIOR_EVENT_NAMES;

	public ExpandBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ExpandBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, this, asyncRenderMode)).intValue());
		
	}

	public final void addExpandListener(org.rcfaces.core.event.IExpandListener listener) {
		addFacesListener(listener);
	}

	public final void removeExpandListener(org.rcfaces.core.event.IExpandListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listExpandListeners() {
		return getFacesListeners(org.rcfaces.core.event.IExpandListener.class);
	}

	public int getAsyncRenderMode() {
		return getAsyncRenderMode(null);
	}

	/**
	 * See {@link #getAsyncRenderMode() getAsyncRenderMode()} for more details
	 */
	public int getAsyncRenderMode(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ASYNC_RENDER_MODE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "asyncRenderMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncRenderModeSetted() {
		return getStateHelper().get(Properties.ASYNC_RENDER_MODE)!=null;
	}

	public void setAsyncRenderMode(int asyncRenderMode) {
		getStateHelper().put(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_BOLD);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return getStateHelper().get(Properties.FONT_BOLD)!=null;
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		getStateHelper().put(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_ITALIC);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return getStateHelper().get(Properties.FONT_ITALIC)!=null;
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		getStateHelper().put(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return getStateHelper().get(Properties.FONT_NAME)!=null;
	}

	public void setFontName(java.lang.String fontName) {
		getStateHelper().put(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_SIZE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return getStateHelper().get(Properties.FONT_SIZE)!=null;
	}

	public void setFontSize(java.lang.String fontSize) {
		getStateHelper().put(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_UNDERLINE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return getStateHelper().get(Properties.FONT_UNDERLINE)!=null;
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		getStateHelper().put(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.DISABLED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return getStateHelper().get(Properties.DISABLED)!=null;
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(Properties.DISABLED, disabled);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		setValue(text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return getStateHelper().get(Properties.TEXT_ALIGNMENT)!=null;
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		getStateHelper().put(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public boolean isCollapsed() {
		return isCollapsed(null);
	}

	/**
	 * See {@link #isCollapsed() isCollapsed()} for more details
	 */
	public boolean isCollapsed(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.COLLAPSED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "collapsed" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCollapsedSetted() {
		return getStateHelper().get(Properties.COLLAPSED)!=null;
	}

	public void setCollapsed(boolean collapsed) {
		getStateHelper().put(Properties.COLLAPSED, collapsed);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ACCESS_KEY);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return getStateHelper().get(Properties.ACCESS_KEY)!=null;
	}

	public void setAccessKey(java.lang.String accessKey) {
		getStateHelper().put(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TAB_INDEX);
	}

	/**
	 * Returns <code>true</code> if the attribute "tabIndex" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTabIndexSetted() {
		return getStateHelper().get(Properties.TAB_INDEX)!=null;
	}

	public void setTabIndex(java.lang.Integer tabIndex) {
		getStateHelper().put(Properties.TAB_INDEX, tabIndex);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.GROUP_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return getStateHelper().get(Properties.GROUP_NAME)!=null;
	}

	public void setGroupName(java.lang.String groupName) {
		getStateHelper().put(Properties.GROUP_NAME, groupName);
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

	public boolean isScopeSaveValue() {
		return isScopeSaveValue(null);
	}

	/**
	 * See {@link #isScopeSaveValue() isScopeSaveValue()} for more details
	 */
	public boolean isScopeSaveValue(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SCOPE_SAVE_VALUE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeSaveValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeSaveValueSetted() {
		return getStateHelper().get(Properties.SCOPE_SAVE_VALUE)!=null;
	}

	public void setScopeSaveValue(boolean scopeSaveValue) {
		getStateHelper().put(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);
	}

	public java.lang.Object getScopeValue() {
		return getScopeValue(null);
	}

	/**
	 * See {@link #getScopeValue() getScopeValue()} for more details
	 */
	public java.lang.Object getScopeValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.SCOPE_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeValueSetted() {
		return getStateHelper().get(Properties.SCOPE_VALUE)!=null;
	}

	public void setScopeValue(java.lang.Object scopeValue) {
		getStateHelper().put(Properties.SCOPE_VALUE, scopeValue);
	}

	public java.lang.String getScopeVar() {
		return getScopeVar(null);
	}

	/**
	 * See {@link #getScopeVar() getScopeVar()} for more details
	 */
	public java.lang.String getScopeVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SCOPE_VAR);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeVarSetted() {
		return getStateHelper().get(Properties.SCOPE_VAR)!=null;
	}

	public void setScopeVar(java.lang.String scopeVar) {
		getStateHelper().put(Properties.SCOPE_VAR, scopeVar);
	}

	public java.lang.String getOverStyleClass() {
		return getOverStyleClass(null);
	}

	/**
	 * See {@link #getOverStyleClass() getOverStyleClass()} for more details
	 */
	public java.lang.String getOverStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.OVER_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "overStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverStyleClassSetted() {
		return getStateHelper().get(Properties.OVER_STYLE_CLASS)!=null;
	}

	public void setOverStyleClass(java.lang.String overStyleClass) {
		getStateHelper().put(Properties.OVER_STYLE_CLASS, overStyleClass);
	}

	/**
	 * Returns a string value indicating the effect to use when transionning from one state to the other.
	 * @return the effect : slideUp|slideUpTrans
	 */
	public String getCollapseEffect() {
		return getCollapseEffect(null);
	}

	/**
	 * Returns a string value indicating the effect to use when transionning from one state to the other.
	 * @return the effect : slideUp|slideUpTrans
	 */
	public String getCollapseEffect(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.COLLAPSE_EFFECT);
	}

	/**
	 * Sets a string value indicating the effect to use when transionning from one state to the other.
	 * @param collapseEffect the effect : slideUp|slideUpTrans
	 */
	public void setCollapseEffect(String collapseEffect) {
		 getStateHelper().put(Properties.COLLAPSE_EFFECT, collapseEffect);
	}

	/**
	 * Sets a string value indicating the effect to use when transionning from one state to the other.
	 * @param collapseEffect the effect : slideUp|slideUpTrans
	 */
	/**
	 * Returns <code>true</code> if the attribute "collapseEffect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCollapseEffectSetted() {
		return getStateHelper().get(Properties.COLLAPSE_EFFECT)!=null;
	}

	/**
	 * Returns a string value specifying the text to show when the component is collapsed.
	 * @return text
	 */
	public String getCollapsedText() {
		return getCollapsedText(null);
	}

	/**
	 * Returns a string value specifying the text to show when the component is collapsed.
	 * @return text
	 */
	public String getCollapsedText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.COLLAPSED_TEXT);
	}

	/**
	 * Sets a string value specifying the text to show when the component is collapsed.
	 * @param collapsedText text
	 */
	public void setCollapsedText(String collapsedText) {
		 getStateHelper().put(Properties.COLLAPSED_TEXT, collapsedText);
	}

	/**
	 * Sets a string value specifying the text to show when the component is collapsed.
	 * @param collapsedText text
	 */
	/**
	 * Returns <code>true</code> if the attribute "collapsedText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCollapsedTextSetted() {
		return getStateHelper().get(Properties.COLLAPSED_TEXT)!=null;
	}

	/**
	 * Returns a boolean value indicating wether the user can expand the component.
	 * @return true if the user can expand the component
	 */
	public boolean isUserExpandable() {
		return isUserExpandable(null);
	}

	/**
	 * Returns a boolean value indicating wether the user can expand the component.
	 * @return true if the user can expand the component
	 */
	public boolean isUserExpandable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.USER_EXPANDABLE, true);
	}

	/**
	 * Sets a boolean value indicating wether the user can expand the component.
	 * @param userExpandable true if the user can expand the component
	 */
	public void setUserExpandable(boolean userExpandable) {
		 getStateHelper().put(Properties.USER_EXPANDABLE, userExpandable);
	}

	/**
	 * Sets a boolean value indicating wether the user can expand the component.
	 * @param userExpandable true if the user can expand the component
	 */
	/**
	 * Returns <code>true</code> if the attribute "userExpandable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isUserExpandableSetted() {
		return getStateHelper().get(Properties.USER_EXPANDABLE)!=null;
	}

}
