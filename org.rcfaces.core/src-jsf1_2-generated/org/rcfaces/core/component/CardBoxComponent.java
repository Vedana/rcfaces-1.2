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

/**
 * <p>The cardBox Component is similar to the <a href="/comps/tabbedPaneComponent.html">tabbedPane Component</a> without title and borders. It is usefull for showing alternatively different contents on the same page.</p>
 * <p>The cardBox Component has the following capabilities :
 * <ul>
 * <li>ISelectionEventCapability</li>
 * <li>IAsyncRenderModeCapability</li>
 * <li>IPreferencesSettings</li>
 * <li>IVariableScopeCapability</li>
 * <li>IAsyncDecodeModeCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/CardBoxComponent.html">cardBox</a> renderer is linked to the <a href="/jsdoc/index.html?f_cardBox.html" target="_blank">f_cardBox</a> javascript class. f_cardBox extends f_component, fa_immediateu</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee"  width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_cardBox</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_cardBox_card</td>
 * <td width="50%">Defines styles for the wrapper DIV element for each <a href="/apidocs/index.html?org/rcfaces/core/component/CardComponent.html">card</a></td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class CardBoxComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IAsyncRenderModeCapability,
	IPreferencesSettings,
	IVariableScopeCapability,
	IAsyncDecodeModeCapability {

	private static final Log LOG = LogFactory.getLog(CardBoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.cardBox";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","scopeSaveValue","scopeVar","scopeValue","asyncDecodeMode","asyncRenderMode","preferences"}));
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

	public org.rcfaces.core.preference.IComponentPreferences getPreferences() {
		return getPreferences(null);
	}

	/**
	 * See {@link #getPreferences() getPreferences()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreferences getPreferences(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreferences)engine.getProperty(Properties.PREFERENCES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preferences" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferencesSetted() {
		return engine.isPropertySetted(Properties.PREFERENCES);
	}

	public void setPreferences(org.rcfaces.core.preference.IComponentPreferences preferences) {
		engine.setProperty(Properties.PREFERENCES, preferences);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
