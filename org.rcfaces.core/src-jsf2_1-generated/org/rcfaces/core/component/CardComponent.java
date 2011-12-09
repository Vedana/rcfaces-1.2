package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.internal.converter.AsyncDecodeModeConverter;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;

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

	private static final Log LOG = LogFactory.getLog(CardComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.card";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractOutputComponent.BEHAVIOR_EVENT_NAMES;

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


				if (isPropertySetted(Properties.ASYNC_DECODE_MODE)) {			
					return getComponentEngine().getIntProperty(Properties.ASYNC_DECODE_MODE,0, facesContext);
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
		return (String)getStateHelper().eval(Properties.TEXT_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return getStateHelper().get(Properties.TEXT_ALIGNMENT)!=null;
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		getStateHelper().put(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VERTICAL_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return getStateHelper().get(Properties.VERTICAL_ALIGNMENT)!=null;
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		getStateHelper().put(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
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
	 * Returns <code>true</code> if the attribute "asyncDecodeMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAsyncDecodeModeSetted() {
		return getStateHelper().get(Properties.ASYNC_DECODE_MODE)!=null;
	}

	public void setAsyncDecodeMode(int asyncDecodeMode) {
		getStateHelper().put(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);
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

}
