package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ScopeComponent;
import org.rcfaces.core.internal.component.Properties;

public class ScopeTag extends CameliaTag implements Tag {

    private static final Log LOG = LogFactory.getLog(ScopeTag.class);

    private String scopeValue;

    private String scopeVar;

    public String getComponentType() {
        return ScopeComponent.COMPONENT_TYPE;
    }

    public final String getScopeValue() {
        return scopeValue;
    }

    public final void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public final String getScopeVar() {
        return scopeVar;
    }

    public final void setScopeVar(String scopeVar) {
        this.scopeVar = scopeVar;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (ScopeComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  scopeValue='" + scopeValue + "'");
            LOG.debug("  scopeVar='" + scopeVar + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof ScopeComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'ScopeComponent'.");
        }

        ScopeComponent component = (ScopeComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (scopeValue != null) {
            ValueBinding vb = application.createValueBinding(scopeValue);
            component.setValueBinding(Properties.SCOPE_VALUE, vb);
        }

        if (scopeVar != null) {
            if (isValueReference(scopeVar)) {
                ValueBinding vb = application.createValueBinding(scopeVar);
                component.setValueBinding(Properties.SCOPE_VAR, vb);

            } else {
                component.setScopeVar(scopeVar);
            }
        }
    }

    public void release() {
        scopeValue = null;
        scopeVar = null;

        super.release();
    }

}
