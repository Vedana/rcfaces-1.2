package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.CardBoxTools;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.capability.IPreferencesSettings;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.converter.AsyncDecodeModeConverter;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.CardComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import java.util.Collection;

/**
 * <p>The cardBox Component is similar to the <a href="/comps/tabbedPaneComponent.html">tabbedPane Component</a> without title and borders. It is usefull for showing alternatively different contents on the same page.</p>
 * <p>The cardBox Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class CardBoxComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IAsyncRenderModeCapability,
	IPreferencesSettings,
	IVariableScopeCapability,
	IAsyncDecodeModeCapability {

	private static final Log LOG = LogFactory.getLog(CardBoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.cardBox";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public CardBoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardBoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public ICardIterator listCards() {


		return CardBoxTools.listCards(this);
		
	}

	public CardComponent getSelectedCard() {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public CardComponent getSelectedCard(FacesContext facesContext) {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public void select(CardComponent card) {


			CardBoxTools.selectCard(this, card);
			
	}

	public void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, this, asyncRenderMode)).intValue());
		
	}

	public void setAsyncDecodeMode(String asyncDecodeMode) {


			setAsyncDecodeMode(((Integer)AsyncDecodeModeConverter.SINGLETON.getAsObject(null, this, asyncDecodeMode)).intValue());
		
	}

	protected boolean verifyAsyncDecode(FacesContext facesContext, PhaseId phaseId) {


				return true;
			
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

	public org.rcfaces.core.preference.IComponentPreferences getPreferences() {
		return getPreferences(null);
	}

	/**
	 * See {@link #getPreferences() getPreferences()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreferences getPreferences(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreferences)getStateHelper().eval(Properties.PREFERENCES);
	}

	/**
	 * Returns <code>true</code> if the attribute "preferences" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferencesSetted() {
		return getStateHelper().get(Properties.PREFERENCES)!=null;
	}

	public void setPreferences(org.rcfaces.core.preference.IComponentPreferences preferences) {
		getStateHelper().put(Properties.PREFERENCES, preferences);
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

}
