package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ScopeComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ScopeTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ScopeTag.class);

	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	public String getComponentType() {
		return ScopeComponent.COMPONENT_TYPE;
	}

	public void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ScopeComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
		}
		if ((uiComponent instanceof ScopeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ScopeComponent'.");
		}

		super.setProperties(uiComponent);

		ScopeComponent component = (ScopeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (scopeSaveValue != null) {
			if (scopeSaveValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue.getExpressionString()));
			}
		}

		if (scopeValue != null) {
			if (scopeValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);

			} else {
				component.setScopeValue(scopeValue.getExpressionString());
			}
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}
	}

	public void release() {
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;

		super.release();
	}

}
