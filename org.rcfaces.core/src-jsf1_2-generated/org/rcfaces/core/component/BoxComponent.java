package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IOverStyleClassCapability;
import org.rcfaces.core.component.capability.ILayoutManagerCapability;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.converter.AsyncDecodeModeConverter;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IHeadingLevelCapability;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import org.rcfaces.core.component.capability.ITypedComponentCapability;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.IHeadingZoneCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IClickEventCapability;
import org.rcfaces.core.internal.converter.LayoutManagerTypeConverter;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IMenuCapability;

/**
 * <p>The Box Component is a container.</p>
 * <p>It can have a graphical representation or not; But it is mainly used to apply a collective treatment to a set of component, for example show or hide a group of component.</p>
 * <p>The Box Component has the following capabilities :
 * <ul>
 * <li>IBackgroundImageCapability</li>
 * <li>IBorderCapability</li>
 * <li>IMouseEventCapability</li>
 * <li>IInitEventCapability</li>
 * <li>ILoadEventCapability</li>
 * <li>IMenuCapability</li>
 * <li>IAsyncRenderModeCapability</li>
 * <li>IAsyncDecodeModeCapability</li>
 * <li>ITypedComponentCapability</li>
 * <li>IOverStyleClassCapability</li>
 * <li>IScrollableCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/BoxComponent.html">box</a> renderer is linked to the <a href="/jsdocs/index.html?f_box.html" target="_blank">f_box</a> javascript class. f_box extends f_component, fa_asyncRender, fa_subMenu</p>
 * 
 * <p>Table of component style classes : </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th  width="50%">Description</th>
 * </tr>
 * <tr style="text-align:left">
 * <td width="33%">f_box</td>
 * <td width="50%">Defines styles for the wrapper element of the component</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class BoxComponent extends AbstractBasicComponent implements 
	IHeadingZoneCapability,
	IHeadingLevelCapability,
	IBackgroundImageCapability,
	IBorderCapability,
	IMouseEventCapability,
	IClickEventCapability,
	IDoubleClickEventCapability,
	IInitEventCapability,
	ILoadEventCapability,
	ILayoutManagerCapability,
	IMenuCapability,
	IAsyncRenderModeCapability,
	IAsyncDecodeModeCapability,
	IVariableScopeCapability,
	ITypedComponentCapability,
	IOverStyleClassCapability,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(BoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.box";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"headingLevel","backgroundImageVerticalRepeat","horizontalScroll","backgroundImageVerticalPosition","backgroundImageHorizontalRepeat","doubleClickListener","overStyleClass","type","asyncDecodeMode","asyncRenderMode","loadListener","backgroundImageHorizontalPosition","initListener","scopeSaveValue","scopeVar","mouseOverListener","verticalScroll","clickListener","scopeValue","backgroundImageURL","border","headingZone","mouseOutListener","layoutType"}));
	}

	public BoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public BoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, this, asyncRenderMode)).intValue());
		
	}

	public void setAsyncDecodeMode(String asyncDecodeMode) {


			setAsyncDecodeMode(((Integer)AsyncDecodeModeConverter.SINGLETON.getAsObject(null, this, asyncDecodeMode)).intValue());
		
	}

	public void setLayoutType(String type) {


			setLayoutType(((Integer)LayoutManagerTypeConverter.SINGLETON.getAsObject(null, this, type)).intValue());
		
	}

	public boolean isHeadingZone() {
		return isHeadingZone(null);
	}

	/**
	 * See {@link #isHeadingZone() isHeadingZone()} for more details
	 */
	public boolean isHeadingZone(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADING_ZONE, false, facesContext);
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

	public java.lang.String getBackgroundImageHorizontalPosition() {
		return getBackgroundImageHorizontalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageHorizontalPosition() getBackgroundImageHorizontalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageHorizontalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalPositionSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION);
	}

	public void setBackgroundImageHorizontalPosition(java.lang.String backgroundImageHorizontalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public boolean isBackgroundImageHorizontalRepeat() {
		return isBackgroundImageHorizontalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageHorizontalRepeat() isBackgroundImageHorizontalRepeat()} for more details
	 */
	public boolean isBackgroundImageHorizontalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalRepeatSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT);
	}

	public void setBackgroundImageHorizontalRepeat(boolean backgroundImageHorizontalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public java.lang.String getBackgroundImageURL() {
		return getBackgroundImageURL(null);
	}

	/**
	 * See {@link #getBackgroundImageURL() getBackgroundImageURL()} for more details
	 */
	public java.lang.String getBackgroundImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageURLSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_URL);
	}

	public void setBackgroundImageURL(java.lang.String backgroundImageURL) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public java.lang.String getBackgroundImageVerticalPosition() {
		return getBackgroundImageVerticalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageVerticalPosition() getBackgroundImageVerticalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageVerticalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalPositionSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION);
	}

	public void setBackgroundImageVerticalPosition(java.lang.String backgroundImageVerticalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public boolean isBackgroundImageVerticalRepeat() {
		return isBackgroundImageVerticalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageVerticalRepeat() isBackgroundImageVerticalRepeat()} for more details
	 */
	public boolean isBackgroundImageVerticalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalRepeatSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT);
	}

	public void setBackgroundImageVerticalRepeat(boolean backgroundImageVerticalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
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

	public final void addMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOutListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOutListener.class);
	}

	public final void addMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOverListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOverListener.class);
	}

	public final void addClickListener(org.rcfaces.core.event.IClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeClickListener(org.rcfaces.core.event.IClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IClickListener.class);
	}

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
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

	public int getLayoutType() {
		return getLayoutType(null);
	}

	/**
	 * See {@link #getLayoutType() getLayoutType()} for more details
	 */
	public int getLayoutType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.LAYOUT_TYPE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "layoutType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLayoutTypeSetted() {
		return engine.isPropertySetted(Properties.LAYOUT_TYPE);
	}

	public void setLayoutType(int layoutType) {
		engine.setProperty(Properties.LAYOUT_TYPE, layoutType);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
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

	public int getAsyncDecodeMode() {
		return getAsyncDecodeMode(null);
	}

	/**
	 * See {@link #getAsyncDecodeMode() getAsyncDecodeMode()} for more details
	 */
	public int getAsyncDecodeMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ASYNC_DECODE_MODE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "asyncDecodeMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncDecodeModeSetted() {
		return engine.isPropertySetted(Properties.ASYNC_DECODE_MODE);
	}

	public void setAsyncDecodeMode(int asyncDecodeMode) {
		engine.setProperty(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);
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

	public java.lang.String getType() {
		return getType(null);
	}

	/**
	 * See {@link #getType() getType()} for more details
	 */
	public java.lang.String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	public void setType(java.lang.String type) {
		engine.setProperty(Properties.TYPE, type);
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

	/**
	 * Returns a boolean value indicating wether the horizontal scroll is shown.
	 * @return true if the horizontal scrollbar is shown
	 */
	public boolean isHorizontalScroll() {
		return isHorizontalScroll(null);
	}

	/**
	 * Returns a boolean value indicating wether the horizontal scroll is shown.
	 * @return true if the horizontal scrollbar is shown
	 */
	public boolean isHorizontalScroll(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HORIZONTAL_SCROLL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the horizontal scroll is shown.
	 * @param horizontalScroll true if the horizontal scrollbar is to be shown
	 */
	public void setHorizontalScroll(boolean horizontalScroll) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL, horizontalScroll);
	}

	/**
	 * Sets a boolean value indicating wether the horizontal scroll is shown.
	 * @param horizontalScroll true if the horizontal scrollbar is to be shown
	 */
	/**
	 * Returns <code>true</code> if the attribute "horizontalScroll" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHorizontalScrollSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL);
	}

	/**
	 * Returns a boolean value indicating wether the vertical scroll is shown.
	 * @return true if vertical scrollbar is shown
	 */
	public boolean isVerticalScroll() {
		return isVerticalScroll(null);
	}

	/**
	 * Returns a boolean value indicating wether the vertical scroll is shown.
	 * @return true if vertical scrollbar is shown
	 */
	public boolean isVerticalScroll(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VERTICAL_SCROLL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the vertical scroll is shown.
	 * @param verticalScroll true if vertical scrollbar is to be shown
	 */
	public void setVerticalScroll(boolean verticalScroll) {
		engine.setProperty(Properties.VERTICAL_SCROLL, verticalScroll);
	}

	/**
	 * Sets a boolean value indicating wether the vertical scroll is shown.
	 * @param verticalScroll true if vertical scrollbar is to be shown
	 */
	/**
	 * Returns <code>true</code> if the attribute "verticalScroll" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVerticalScrollSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_SCROLL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
