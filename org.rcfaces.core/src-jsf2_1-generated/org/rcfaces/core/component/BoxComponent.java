package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IOverStyleClassCapability;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.converter.AsyncDecodeModeConverter;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import org.rcfaces.core.component.capability.ITypedComponentCapability;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IMenuCapability;

/**
 * <p>The Box Component is a container.</p>
 * <p>It can have a graphical representation or not; But it is mainly used to apply a collective treatment to a set of component, for example show or hide a group of component.</p>
 * <p>The Box Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Background Image</li>
 * <li>Border</li>
 * <li>Events Handling</li>
 * <li>Contextual Menu</li>
 * <li>Async Render (AJAX)</li>
 * </ul>
 * </p>
 */
public class BoxComponent extends AbstractBasicComponent implements 
	IBackgroundImageCapability,
	IBorderCapability,
	IMouseEventCapability,
	IInitEventCapability,
	ILoadEventCapability,
	IMenuCapability,
	IAsyncRenderModeCapability,
	IAsyncDecodeModeCapability,
	IVariableScopeCapability,
	ITypedComponentCapability,
	IOverStyleClassCapability,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(BoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.box";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

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

	public java.lang.String getBackgroundImageHorizontalPosition() {
		return getBackgroundImageHorizontalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageHorizontalPosition() getBackgroundImageHorizontalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageHorizontalPosition(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalPositionSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION)!=null;
	}

	public void setBackgroundImageHorizontalPosition(java.lang.String backgroundImageHorizontalPosition) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public boolean isBackgroundImageHorizontalRepeat() {
		return isBackgroundImageHorizontalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageHorizontalRepeat() isBackgroundImageHorizontalRepeat()} for more details
	 */
	public boolean isBackgroundImageHorizontalRepeat(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalRepeatSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT)!=null;
	}

	public void setBackgroundImageHorizontalRepeat(boolean backgroundImageHorizontalRepeat) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public java.lang.String getBackgroundImageURL() {
		return getBackgroundImageURL(null);
	}

	/**
	 * See {@link #getBackgroundImageURL() getBackgroundImageURL()} for more details
	 */
	public java.lang.String getBackgroundImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageURLSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_URL)!=null;
	}

	public void setBackgroundImageURL(java.lang.String backgroundImageURL) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public java.lang.String getBackgroundImageVerticalPosition() {
		return getBackgroundImageVerticalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageVerticalPosition() getBackgroundImageVerticalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageVerticalPosition(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalPositionSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION)!=null;
	}

	public void setBackgroundImageVerticalPosition(java.lang.String backgroundImageVerticalPosition) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public boolean isBackgroundImageVerticalRepeat() {
		return isBackgroundImageVerticalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageVerticalRepeat() isBackgroundImageVerticalRepeat()} for more details
	 */
	public boolean isBackgroundImageVerticalRepeat(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalRepeatSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT)!=null;
	}

	public void setBackgroundImageVerticalRepeat(boolean backgroundImageVerticalRepeat) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
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

	public int getAsyncDecodeMode() {
		return getAsyncDecodeMode(null);
	}

	/**
	 * See {@link #getAsyncDecodeMode() getAsyncDecodeMode()} for more details
	 */
	public int getAsyncDecodeMode(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ASYNC_DECODE_MODE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "asyncDecodeMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncDecodeModeSetted() {
		return getStateHelper().get(Properties.ASYNC_DECODE_MODE)!=null;
	}

	public void setAsyncDecodeMode(int asyncDecodeMode) {
		getStateHelper().put(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);
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

	public java.lang.String getType() {
		return getType(null);
	}

	/**
	 * See {@link #getType() getType()} for more details
	 */
	public java.lang.String getType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTypeSetted() {
		return getStateHelper().get(Properties.TYPE)!=null;
	}

	public void setType(java.lang.String type) {
		getStateHelper().put(Properties.TYPE, type);
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
		return (Boolean)getStateHelper().eval(Properties.HORIZONTAL_SCROLL, false);
	}

	/**
	 * Sets a boolean value indicating wether the horizontal scroll is shown.
	 * @param horizontalScroll true if the horizontal scrollbar is to be shown
	 */
	public void setHorizontalScroll(boolean horizontalScroll) {
		 getStateHelper().put(Properties.HORIZONTAL_SCROLL, horizontalScroll);
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
		return getStateHelper().get(Properties.HORIZONTAL_SCROLL)!=null;
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
		return (Boolean)getStateHelper().eval(Properties.VERTICAL_SCROLL, false);
	}

	/**
	 * Sets a boolean value indicating wether the vertical scroll is shown.
	 * @param verticalScroll true if vertical scrollbar is to be shown
	 */
	public void setVerticalScroll(boolean verticalScroll) {
		 getStateHelper().put(Properties.VERTICAL_SCROLL, verticalScroll);
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
		return getStateHelper().get(Properties.VERTICAL_SCROLL)!=null;
	}

}
