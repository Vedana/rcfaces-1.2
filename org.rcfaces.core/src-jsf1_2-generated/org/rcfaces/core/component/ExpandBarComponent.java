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
import org.rcfaces.core.component.capability.IHeadingLevelCapability;
import java.util.Set;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IErrorTextCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.IHeadingZoneCapability;
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
 * <li>IExpandEventCapability</li>
 * <li>IAsyncRenderModeCapability</li>
 * <li>IFontCapability</li>
 * <li>IDisabledCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>ITextCapability</li>
 * <li>ITextDirectionCapability</li>
 * <li>ITextAlignmentCapability</li>
 * <li>ICollapsableCapability</li>
 * <li>IBorderCapability</li>
 * <li>IAccessKeyCapability</li>
 * <li>ITabIndexCapability</li>
 * <li>IRadioGroupCapability</li>
 * <li>IFocusBlurEventCapability</li>
 * <li>ISelectionEventCapability</li>
 * <li>ILoadEventCapability</li>
 * <li>IVariableScopeCapability</li>
 * <li>IOverStyleClassCapability</li>
 * <li>IAsyncRenderComponent</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ExpandBarComponent.html">expandBar</a> renderer is link to the <a href="/jsdocs/index.html?f_expandBar.html" target="_blank">f_expandBar</a> javascript class. f_expandBar extends f_component, fa_disabled, fa_readOnly, fa_collapsed, fa_groupName, fa_overStyleClass</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_expandBar</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper UL element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_expandBar_head</td>
 * <td id="ermvsh" width="50%">Defines styles for the LI element for the title.</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_expandBar_body</td>
 * <td id="ermvsh" width="50%">Defines styles for the element for the body.</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ExpandBarComponent extends AbstractOutputComponent implements 
	IExpandEventCapability,
	IHeadingZoneCapability,
	IHeadingLevelCapability,
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
	IErrorTextCapability,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(ExpandBarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.expandBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fontName","accessKey","groupName","blurListener","collapsedText","tabIndex","userExpandable","focusListener","overStyleClass","loadListener","errorText","selectionListener","collapseEffect","scopeValue","border","headingZone","text","headingLevel","expandListener","fontBold","fontSize","asyncRenderMode","textDirection","collapsed","fontItalic","scopeSaveValue","readOnly","fontUnderline","scopeVar","textAlignment","disabled"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="text";

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

	public boolean isHeadingZone() {
		return isHeadingZone(null);
	}

	/**
	 * See {@link #isHeadingZone() isHeadingZone()} for more details
	 */
	public boolean isHeadingZone(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADING_ZONE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingZoneSetted() {
		return engine.isPropertySetted(Properties.HEADING_ZONE);
	}

	public void setHeadingZone(boolean headingZone) {
		engine.setProperty(Properties.HEADING_ZONE, headingZone);
	}

	public java.lang.String getHeadingLevel() {
		return getHeadingLevel(null);
	}

	/**
	 * See {@link #getHeadingLevel() getHeadingLevel()} for more details
	 */
	public java.lang.String getHeadingLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEADING_LEVEL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingLevelSetted() {
		return engine.isPropertySetted(Properties.HEADING_LEVEL);
	}

	public void setHeadingLevel(java.lang.String headingLevel) {
		engine.setProperty(Properties.HEADING_LEVEL, headingLevel);
	}

	public int getAsyncRenderMode() {
		return getAsyncRenderMode(null);
	}

	/**
	 * See {@link #getAsyncRenderMode() getAsyncRenderMode()} for more details
	 */
	public int getAsyncRenderMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ASYNC_RENDER_MODE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "asyncRenderMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncRenderModeSetted() {
		return engine.isPropertySetted(Properties.ASYNC_RENDER_MODE);
	}

	public void setAsyncRenderMode(int asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_BOLD, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return engine.isPropertySetted(Properties.FONT_BOLD);
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_ITALIC, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return engine.isPropertySetted(Properties.FONT_ITALIC);
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return engine.isPropertySetted(Properties.FONT_NAME);
	}

	public void setFontName(java.lang.String fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZE);
	}

	public void setFontSize(java.lang.String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_UNDERLINE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return engine.isPropertySetted(Properties.FONT_UNDERLINE);
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return engine.isPropertySetted(Properties.DISABLED);
	}

	public void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return engine.isPropertySetted(Properties.READ_ONLY);
	}

	public void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
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
		return engine.isPropertySetted(Properties.TEXT);
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
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return engine.isPropertySetted(Properties.TEXT_ALIGNMENT);
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public boolean isCollapsed() {
		return isCollapsed(null);
	}

	/**
	 * See {@link #isCollapsed() isCollapsed()} for more details
	 */
	public boolean isCollapsed(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.COLLAPSED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "collapsed" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCollapsedSetted() {
		return engine.isPropertySetted(Properties.COLLAPSED);
	}

	public void setCollapsed(boolean collapsed) {
		engine.setProperty(Properties.COLLAPSED, collapsed);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return engine.isPropertySetted(Properties.BORDER);
	}

	public void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return engine.getIntegerProperty(Properties.TAB_INDEX, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "tabIndex" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTabIndexSetted() {
		return engine.isPropertySetted(Properties.TAB_INDEX);
	}

	public void setTabIndex(java.lang.Integer tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return engine.isPropertySetted(Properties.GROUP_NAME);
	}

	public void setGroupName(java.lang.String groupName) {
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

	public boolean isScopeSaveValue() {
		return isScopeSaveValue(null);
	}

	/**
	 * See {@link #isScopeSaveValue() isScopeSaveValue()} for more details
	 */
	public boolean isScopeSaveValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SCOPE_SAVE_VALUE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeSaveValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeSaveValueSetted() {
		return engine.isPropertySetted(Properties.SCOPE_SAVE_VALUE);
	}

	public void setScopeSaveValue(boolean scopeSaveValue) {
		engine.setProperty(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);
	}

	public java.lang.Object getScopeValue() {
		return getScopeValue(null);
	}

	/**
	 * See {@link #getScopeValue() getScopeValue()} for more details
	 */
	public java.lang.Object getScopeValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.SCOPE_VALUE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeValueSetted() {
		return engine.isPropertySetted(Properties.SCOPE_VALUE);
	}

	public void setScopeValue(java.lang.Object scopeValue) {
		engine.setProperty(Properties.SCOPE_VALUE, scopeValue);
	}

	public java.lang.String getScopeVar() {
		return getScopeVar(null);
	}

	/**
	 * See {@link #getScopeVar() getScopeVar()} for more details
	 */
	public java.lang.String getScopeVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SCOPE_VAR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeVarSetted() {
		return engine.isPropertySetted(Properties.SCOPE_VAR);
	}

	public void setScopeVar(java.lang.String scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	public java.lang.String getOverStyleClass() {
		return getOverStyleClass(null);
	}

	/**
	 * See {@link #getOverStyleClass() getOverStyleClass()} for more details
	 */
	public java.lang.String getOverStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.OVER_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "overStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverStyleClassSetted() {
		return engine.isPropertySetted(Properties.OVER_STYLE_CLASS);
	}

	public void setOverStyleClass(java.lang.String overStyleClass) {
		engine.setProperty(Properties.OVER_STYLE_CLASS, overStyleClass);
	}

	public java.lang.String getErrorText() {
		return getErrorText(null);
	}

	/**
	 * See {@link #getErrorText() getErrorText()} for more details
	 */
	public java.lang.String getErrorText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorTextSetted() {
		return engine.isPropertySetted(Properties.ERROR_TEXT);
	}

	public void setErrorText(java.lang.String errorText) {
		engine.setProperty(Properties.ERROR_TEXT, errorText);
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
		String s = engine.getStringProperty(Properties.COLLAPSE_EFFECT, facesContext);
		return s;
	}

	/**
	 * Sets a string value indicating the effect to use when transionning from one state to the other.
	 * @param collapseEffect the effect : slideUp|slideUpTrans
	 */
	public void setCollapseEffect(String collapseEffect) {
		engine.setProperty(Properties.COLLAPSE_EFFECT, collapseEffect);
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
		return engine.isPropertySetted(Properties.COLLAPSE_EFFECT);
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
		String s = engine.getStringProperty(Properties.COLLAPSED_TEXT, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the text to show when the component is collapsed.
	 * @param collapsedText text
	 */
	public void setCollapsedText(String collapsedText) {
		engine.setProperty(Properties.COLLAPSED_TEXT, collapsedText);
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
		return engine.isPropertySetted(Properties.COLLAPSED_TEXT);
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
		return engine.getBoolProperty(Properties.USER_EXPANDABLE, true, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the user can expand the component.
	 * @param userExpandable true if the user can expand the component
	 */
	public void setUserExpandable(boolean userExpandable) {
		engine.setProperty(Properties.USER_EXPANDABLE, userExpandable);
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
		return engine.isPropertySetted(Properties.USER_EXPANDABLE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
