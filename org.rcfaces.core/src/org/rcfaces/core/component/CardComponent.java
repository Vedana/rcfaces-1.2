package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IVariableScopeCapability;

/**
 * An element belonging to a <a href="/comps/cardBoxComponent.html">cardBox Component</a> and holding the components.
 */
public class CardComponent extends AbstractOutputComponent implements 
	ITextAlignmentCapability,
	IVerticalAlignmentCapability,
	IVariableScopeCapability,
	ILoadEventCapability,
	IAsyncRenderComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.card";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"loadListener","scopeValue","scopeVar","verticalAlignment","textAlignment"}));
	}

	public CardComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final CardBoxComponent getCardBox() {


				return CardBoxTools.getCardBox(this);
			
	}

	public final java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	public final java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	public final void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final void setTextAlignment(ValueBinding textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	public final java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	public final void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public final void setVerticalAlignment(ValueBinding verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
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
