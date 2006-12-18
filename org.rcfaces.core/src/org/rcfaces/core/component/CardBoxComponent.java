package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.CardComponent;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import java.util.HashSet;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.component.capability.IVariableScopeCapability;

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
	IPreferenceCapability,
	IVariableScopeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.cardBox";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","scopeValue","scopeVar","asyncRenderMode","preference"}));
	}

	public CardBoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardBoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final ICardIterator listCards() {


		return CardBoxTools.listCards(this);
		
	}

	public final CardComponent getSelectedCard() {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public final CardComponent getSelectedCard(FacesContext facesContext) {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public final void select(CardComponent card) {


			CardBoxTools.selectCard(this, card);
			
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, null, asyncRenderMode)).intValue());
		
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

	public final org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	/**
	 * See {@link #getPreference() getPreference()} for more details
	 */
	public final org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	public final void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	/**
	 * See {@link #setPreference(org.rcfaces.core.preference.IComponentPreference) setPreference(org.rcfaces.core.preference.IComponentPreference)} for more details
	 */
	public final void setPreference(ValueBinding preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
