package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.converter.AsyncDecodeModeConverter;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;

/**
 * An element belonging to a <a href="/comps/cardBoxComponent.html">cardBox Component</a> and holding the components.
 */
public class CardComponent extends AbstractOutputComponent implements 
	ITextAlignmentCapability,
	IVerticalAlignmentCapability,
	IVariableScopeCapability,
	IAsyncDecodeModeCapability,
	ILoadEventCapability,
	IAsyncRenderComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.card";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"loadListener","scopeValue","scopeVar","verticalAlignment","textAlignment","scopeSaveValue","asyncDecodeMode"}));
	}

	public CardComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardComponent(String componentId) {
		this();
		setId(componentId);
	}

	public CardBoxComponent getCardBox() {


				return CardBoxTools.getCardBox(this);
			
	}

	public int getAsyncDecodeMode(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.ASYNC_DECODE_MODE)) {			
					return engine.getIntProperty(Properties.ASYNC_DECODE_MODE,0, facesContext);
				}
				
				CardBoxComponent cardBox=getCardBox();
				if (cardBox==null) {
					return IAsyncDecodeModeCapability.DEFAULT_ASYNC_DECODE_MODE;
				}
				
				return cardBox.getAsyncDecodeMode(facesContext);
			
	}

	public void setAsyncDecodeMode(String asyncDecodeMode) {


			setAsyncDecodeMode(((Integer)AsyncDecodeModeConverter.SINGLETON.getAsObject(null, this, asyncDecodeMode)).intValue());
		
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

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGNMENT);
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
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
	 * Returns <code>true</code> if the attribute "asyncDecodeMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncDecodeModeSetted() {
		return engine.isPropertySetted(Properties.ASYNC_DECODE_MODE);
	}

	public void setAsyncDecodeMode(int asyncDecodeMode) {
		engine.setProperty(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
