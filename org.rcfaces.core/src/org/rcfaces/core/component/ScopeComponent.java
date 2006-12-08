package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IVariableScopeCapability;

public class ScopeComponent extends CameliaBaseComponent implements 
	IVariableScopeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.scope";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"scopeValue","scopeVar"}));
	}

	public ScopeComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ScopeComponent(String componentId) {
		this();
		setId(componentId);
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

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
