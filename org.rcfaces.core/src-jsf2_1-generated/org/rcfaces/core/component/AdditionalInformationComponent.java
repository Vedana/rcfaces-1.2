package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IAdditionalInformationContainer;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.component.IMenuComponent;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IHeightCapability;
import org.rcfaces.core.internal.component.CameliaColumnComponent;
import org.rcfaces.core.component.capability.IErrorEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IMarginCapability;

public class AdditionalInformationComponent extends CameliaColumnComponent implements 
	IPropertyChangeEventCapability,
	IUserEventCapability,
	IErrorEventCapability,
	IWAIRoleCapability,
	IMarginCapability,
	IForegroundBackgroundColorCapability,
	IBackgroundImageCapability,
	ILookAndFeelCapability,
	IStyleClassCapability,
	IHeightCapability,
	IMouseEventCapability,
	IInitEventCapability,
	ILoadEventCapability,
	IMenuCapability,
	IVariableScopeCapability,
	IAdditionalInformationContainer,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(AdditionalInformationComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.additionalInformation";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaColumnComponent.BEHAVIOR_EVENT_NAMES;

	public AdditionalInformationComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public AdditionalInformationComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setMargins(String margins) {


				MarginTools.setMargins(this, margins);
			
	}

	public final void addPropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removePropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listPropertyChangeListeners() {
		return getFacesListeners(org.rcfaces.core.event.IPropertyChangeListener.class);
	}

	public final void addUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		addFacesListener(listener);
	}

	public final void removeUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listUserEventListeners() {
		return getFacesListeners(org.rcfaces.core.event.IUserEventListener.class);
	}

	public final void addErrorListener(org.rcfaces.core.event.IErrorListener listener) {
		addFacesListener(listener);
	}

	public final void removeErrorListener(org.rcfaces.core.event.IErrorListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listErrorListeners() {
		return getFacesListeners(org.rcfaces.core.event.IErrorListener.class);
	}

	public java.lang.String getAriaLabel() {
		return getAriaLabel(null);
	}

	/**
	 * See {@link #getAriaLabel() getAriaLabel()} for more details
	 */
	public java.lang.String getAriaLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ARIA_LABEL);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLabelSetted() {
		return getStateHelper().get(Properties.ARIA_LABEL)!=null;
	}

	public void setAriaLabel(java.lang.String ariaLabel) {
		getStateHelper().put(Properties.ARIA_LABEL, ariaLabel);
	}

	public int getAriaLevel() {
		return getAriaLevel(null);
	}

	/**
	 * See {@link #getAriaLevel() getAriaLevel()} for more details
	 */
	public int getAriaLevel(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ARIA_LEVEL, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLevelSetted() {
		return getStateHelper().get(Properties.ARIA_LEVEL)!=null;
	}

	public void setAriaLevel(int ariaLevel) {
		getStateHelper().put(Properties.ARIA_LEVEL, ariaLevel);
	}

	public java.lang.String getWaiRole() {
		return getWaiRole(null);
	}

	/**
	 * See {@link #getWaiRole() getWaiRole()} for more details
	 */
	public java.lang.String getWaiRole(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WAI_ROLE);
	}

	/**
	 * Returns <code>true</code> if the attribute "waiRole" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWaiRoleSetted() {
		return getStateHelper().get(Properties.WAI_ROLE)!=null;
	}

	public void setWaiRole(java.lang.String waiRole) {
		getStateHelper().put(Properties.WAI_ROLE, waiRole);
	}

	public java.lang.String getMarginBottom() {
		return getMarginBottom(null);
	}

	/**
	 * See {@link #getMarginBottom() getMarginBottom()} for more details
	 */
	public java.lang.String getMarginBottom(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_BOTTOM);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginBottom" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginBottomSetted() {
		return getStateHelper().get(Properties.MARGIN_BOTTOM)!=null;
	}

	public void setMarginBottom(java.lang.String marginBottom) {
		getStateHelper().put(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	/**
	 * See {@link #getMarginLeft() getMarginLeft()} for more details
	 */
	public java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_LEFT);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginLeft" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginLeftSetted() {
		return getStateHelper().get(Properties.MARGIN_LEFT)!=null;
	}

	public void setMarginLeft(java.lang.String marginLeft) {
		getStateHelper().put(Properties.MARGIN_LEFT, marginLeft);
	}

	public java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	/**
	 * See {@link #getMarginRight() getMarginRight()} for more details
	 */
	public java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_RIGHT);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginRight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginRightSetted() {
		return getStateHelper().get(Properties.MARGIN_RIGHT)!=null;
	}

	public void setMarginRight(java.lang.String marginRight) {
		getStateHelper().put(Properties.MARGIN_RIGHT, marginRight);
	}

	public java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	/**
	 * See {@link #getMarginTop() getMarginTop()} for more details
	 */
	public java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_TOP);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginTop" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginTopSetted() {
		return getStateHelper().get(Properties.MARGIN_TOP)!=null;
	}

	public void setMarginTop(java.lang.String marginTop) {
		getStateHelper().put(Properties.MARGIN_TOP, marginTop);
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return getStateHelper().get(Properties.BACKGROUND_COLOR)!=null;
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		getStateHelper().put(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOREGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return getStateHelper().get(Properties.FOREGROUND_COLOR)!=null;
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		getStateHelper().put(Properties.FOREGROUND_COLOR, foregroundColor);
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

	public java.lang.String getLookId() {
		return getLookId(null);
	}

	/**
	 * See {@link #getLookId() getLookId()} for more details
	 */
	public java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.LOOK_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "lookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLookIdSetted() {
		return getStateHelper().get(Properties.LOOK_ID)!=null;
	}

	public void setLookId(java.lang.String lookId) {
		getStateHelper().put(Properties.LOOK_ID, lookId);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HEIGHT);
	}

	/**
	 * Returns <code>true</code> if the attribute "height" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeightSetted() {
		return getStateHelper().get(Properties.HEIGHT)!=null;
	}

	public void setHeight(java.lang.String height) {
		getStateHelper().put(Properties.HEIGHT, height);
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

}
