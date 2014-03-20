package org.rcfaces.core.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;

public class ToolTipComponent extends AbstractBasicComponent implements 
	IBackgroundImageCapability,
	IBorderCapability,
	IInitEventCapability,
	ILoadEventCapability,
	IAsyncRenderModeCapability,
	IVariableScopeCapability,
	ILookAndFeelCapability,
	IStyleClassCapability,
	NamingContainer,
	IAsyncRenderComponent {

	private static final Log LOG = LogFactory.getLog(ToolTipComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolTip";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"position","lookId","backgroundImageVerticalRepeat","styleClass","backgroundImageVerticalPosition","backgroundImageHorizontalRepeat","toolTipId","backgroundImageHorizontalPosition","loadListener","asyncRenderMode","initListener","scopeSaveValue","scopeVar","scopeValue","backgroundImageURL","border"}));
	}

	public ToolTipComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolTipComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, this, asyncRenderMode)).intValue());
		
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

	public String getToolTipId() {
		return getToolTipId(null);
	}

	public String getToolTipId(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.TOOL_TIP_ID, facesContext);
		return s;
	}

	public void setToolTipId(String toolTipId) {
		engine.setProperty(Properties.TOOL_TIP_ID, toolTipId);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isToolTipIdSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_ID);
	}

	public String getPosition() {
		return getPosition(null);
	}

	public String getPosition(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.POSITION, facesContext);
		return s;
	}

	public void setPosition(String position) {
		engine.setProperty(Properties.POSITION, position);
	}

	/**
	 * Returns <code>true</code> if the attribute "position" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPositionSetted() {
		return engine.isPropertySetted(Properties.POSITION);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
