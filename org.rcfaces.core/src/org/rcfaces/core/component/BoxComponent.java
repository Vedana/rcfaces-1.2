package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.IMenuCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;

public class BoxComponent extends AbstractBasicComponent implements 
	IBackgroundImageCapability,
	IBorderCapability,
	IMouseEventCapability,
	IInitEventCapability,
	ILoadEventCapability,
	IMenuCapability,
	IAsyncRenderModeCapability,
	IVariableScopeCapability,
	IAsyncRenderComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.box";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"scopeValue","asyncRenderMode","mouseOverListener","backgroundImageVerticalPosition","loadListener","scopeVar","backgroundImageHorizontalPosition","backgroundImageVerticalRepeat","backgroundImageHorizontalRepeat","initListener","verticalScroll","horizontalScroll","backgroundImageURL","border","mouseOutListener"}));
	}

	public BoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public BoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, null, asyncRenderMode)).intValue());
		
	}

	public final java.lang.String getBackgroundImageHorizontalPosition() {
		return getBackgroundImageHorizontalPosition(null);
	}

	public final java.lang.String getBackgroundImageHorizontalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, facesContext);
	}

	public final void setBackgroundImageHorizontalPosition(java.lang.String backgroundImageHorizontalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public final void setBackgroundImageHorizontalPosition(ValueBinding backgroundImageHorizontalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public final boolean isBackgroundImageHorizontalRepeat() {
		return isBackgroundImageHorizontalRepeat(null);
	}

	public final boolean isBackgroundImageHorizontalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false, facesContext);
	}

	public final void setBackgroundImageHorizontalRepeat(boolean backgroundImageHorizontalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public final void setBackgroundImageHorizontalRepeat(ValueBinding backgroundImageHorizontalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public final java.lang.String getBackgroundImageURL() {
		return getBackgroundImageURL(null);
	}

	public final java.lang.String getBackgroundImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_URL, facesContext);
	}

	public final void setBackgroundImageURL(java.lang.String backgroundImageURL) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public final void setBackgroundImageURL(ValueBinding backgroundImageURL) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public final java.lang.String getBackgroundImageVerticalPosition() {
		return getBackgroundImageVerticalPosition(null);
	}

	public final java.lang.String getBackgroundImageVerticalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, facesContext);
	}

	public final void setBackgroundImageVerticalPosition(java.lang.String backgroundImageVerticalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public final void setBackgroundImageVerticalPosition(ValueBinding backgroundImageVerticalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public final boolean isBackgroundImageVerticalRepeat() {
		return isBackgroundImageVerticalRepeat(null);
	}

	public final boolean isBackgroundImageVerticalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false, facesContext);
	}

	public final void setBackgroundImageVerticalRepeat(boolean backgroundImageVerticalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
	}

	public final void setBackgroundImageVerticalRepeat(ValueBinding backgroundImageVerticalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void setBorder(ValueBinding border) {
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

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public final int getAsyncRenderMode() {
		return getAsyncRenderMode(null);
	}

	public final int getAsyncRenderMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ASYNC_RENDER_MODE,0, facesContext);
	}

	public final void setAsyncRenderMode(int asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public final void setAsyncRenderMode(ValueBinding asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public final javax.faces.el.ValueBinding getScopeValue() {
		return getScopeValue(null);
	}

	public final javax.faces.el.ValueBinding getScopeValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValueBindingProperty(Properties.SCOPE_VALUE);
	}

	public final void setScopeValue(javax.faces.el.ValueBinding scopeValue) {
		engine.setProperty(Properties.SCOPE_VALUE, scopeValue);
	}

	public final java.lang.String getScopeVar() {
		return getScopeVar(null);
	}

	public final java.lang.String getScopeVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SCOPE_VAR, facesContext);
	}

	public final void setScopeVar(java.lang.String scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	public final void setScopeVar(ValueBinding scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	/**
	 * Returns a boolean value indicating wether the horizontal scroll is shown.
	 * @return true if the horizontal scrollbar is shown
	 */
	public final boolean isHorizontalScroll() {
		return isHorizontalScroll(null);
	}

	/**
	 * Returns a boolean value indicating wether the horizontal scroll is shown.
	 * @return true if the horizontal scrollbar is shown
	 */
	public final boolean isHorizontalScroll(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HORIZONTAL_SCROLL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the horizontal scroll is shown.
	 * @param horizontalScroll true if the horizontal scrollbar is to be shown
	 */
	public final void setHorizontalScroll(boolean horizontalScroll) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL, horizontalScroll);
	}

	/**
	 * Sets a boolean value indicating wether the horizontal scroll is shown.
	 * @param horizontalScroll true if the horizontal scrollbar is to be shown
	 */
	public final void setHorizontalScroll(ValueBinding horizontalScroll) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL, horizontalScroll);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScroll" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL);
	}

	/**
	 * Returns a boolean value indicating wether the vertical scroll is shown.
	 * @return true if vertical scrollbar is shown
	 */
	public final boolean isVerticalScroll() {
		return isVerticalScroll(null);
	}

	/**
	 * Returns a boolean value indicating wether the vertical scroll is shown.
	 * @return true if vertical scrollbar is shown
	 */
	public final boolean isVerticalScroll(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VERTICAL_SCROLL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the vertical scroll is shown.
	 * @param verticalScroll true if vertical scrollbar is to be shown
	 */
	public final void setVerticalScroll(boolean verticalScroll) {
		engine.setProperty(Properties.VERTICAL_SCROLL, verticalScroll);
	}

	/**
	 * Sets a boolean value indicating wether the vertical scroll is shown.
	 * @param verticalScroll true if vertical scrollbar is to be shown
	 */
	public final void setVerticalScroll(ValueBinding verticalScroll) {
		engine.setProperty(Properties.VERTICAL_SCROLL, verticalScroll);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScroll" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_SCROLL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
