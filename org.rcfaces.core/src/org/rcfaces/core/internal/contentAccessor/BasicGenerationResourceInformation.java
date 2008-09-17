/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicGenerationResourceInformation extends AbstractInformation implements
        IGenerationResourceInformation {

    private static final String COMPONENT_CLIENT_ID_PROPERTY = "org.rcfaces.org.COMPONENT_CLIENT_ID_PROPERTY";

    private transient UIComponent component;

    private IFilterProperties filterProperties;

    private boolean processAtRequest;

    public BasicGenerationResourceInformation() {
    }

    public BasicGenerationResourceInformation(
            IComponentRenderContext componentRenderContext) {
        this(componentRenderContext.getComponent(), componentRenderContext
                .getComponentClientId());
    }

    public BasicGenerationResourceInformation(UIComponent component, String clientId) {
        setComponent(component, clientId);
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(IComponentRenderContext componentRenderContext) {
        setComponent(componentRenderContext.getComponent(),
                componentRenderContext.getComponentClientId());
    }

    public void setComponent(UIComponent component) {
        setComponent(component, (FacesContext) null);
    }

    public void setComponent(UIComponent component, FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        String clientId = null;
        if (facesContext != null) {
            clientId = component.getClientId(facesContext);
        }

        setComponent(component, clientId);
    }

    public void setComponent(UIComponent component, String clientId) {
        this.component = component;
        setComponentClientId(clientId);
    }

    public String getComponentClientId() {
        return (String) getAttribute(COMPONENT_CLIENT_ID_PROPERTY);
    }

    public void setComponentClientId(String componentClientId) {
        setAttribute(COMPONENT_CLIENT_ID_PROPERTY, componentClientId);
    }

    public final IFilterProperties getFilterProperties() {
        return filterProperties;
    }

    public final void setFilterProperties(IFilterProperties filterProperties) {
        this.filterProperties = filterProperties;
    }

    public void restoreState(FacesContext context, Object state) {
        Object states[] = (Object[]) state;

        super.restoreState(context, states[0]);

        filterProperties = (IFilterProperties) UIComponentBase
                .restoreAttachedState(context, states[1]);

        // Pas la peine de traiter processAtRequest !!
    }

    public Object saveState(FacesContext context) {
        Object states[] = new Object[2];

        states[0] = super.saveState(context);

        states[1] = UIComponentBase
                .saveAttachedState(context, filterProperties);

        // Pas la peine de traiter processAtRequest !!

        return states;
    }

    public final boolean isProcessAtRequest() {
        return processAtRequest;
    }

    public final void setProcessAtRequest(boolean processAtRequest) {
        this.processAtRequest = processAtRequest;
    }

}
