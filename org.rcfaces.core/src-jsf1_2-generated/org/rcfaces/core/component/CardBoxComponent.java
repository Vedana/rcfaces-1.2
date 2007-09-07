package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.CardBoxTools;
import java.lang.String;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.CardComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.AbstractInputComponent;

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
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","scopeVar","preference","scopeValue","asyncRenderMode"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="value";

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

	public org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	/**
	 * See {@link #getPreference() getPreference()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preference" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferenceSetted() {
		return engine.isPropertySetted(Properties.PREFERENCE);
	}

	public void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public javax.faces.el.ValueBinding getScopeValue() {
		return getScopeValue(null);
	}

	/**
	 * See {@link #getScopeValue() getScopeValue()} for more details
	 */
	public javax.faces.el.ValueBinding getScopeValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValueExpressionProperty(Properties.SCOPE_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeValueSetted() {
		return engine.isPropertySetted(Properties.SCOPE_VALUE);
	}

	public void setScopeValue(javax.faces.el.ValueBinding scopeValue) {
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
