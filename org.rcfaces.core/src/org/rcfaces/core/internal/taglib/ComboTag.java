package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ComboComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboTag extends AbstractInputTag implements Tag {

    private static final Log LOG = LogFactory.getLog(ComboTag.class);

    private String selectionListeners;

    private String required;

    private String filterProperties;

    public String getComponentType() {
        return ComboComponent.COMPONENT_TYPE;
    }

    public final String getSelectionListener() {
        return selectionListeners;
    }

    public final void setSelectionListener(String selectionListeners) {
        this.selectionListeners = selectionListeners;
    }

    public final String getRequired() {
        return required;
    }

    public final void setRequired(String required) {
        this.required = required;
    }

    public final String getFilterProperties() {
        return filterProperties;
    }

    public final void setFilterProperties(String filterProperties) {
        this.filterProperties = filterProperties;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (ComboComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  required='" + required + "'");
            LOG.debug("  filterProperties='" + filterProperties + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof ComboComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'ComboComponent'.");
        }

        ComboComponent component = (ComboComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (selectionListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
        }

        if (required != null) {
            if (isValueReference(required)) {
                ValueBinding vb = application.createValueBinding(required);
                component.setValueBinding(Properties.REQUIRED, vb);

            } else {
                component.setRequired(getBool(required));
            }
        }

        if (filterProperties != null) {
            ValueBinding vb = application.createValueBinding(filterProperties);
            component.setValueBinding(Properties.FILTER_PROPERTIES, vb);
        }
    }

    public void release() {
        selectionListeners = null;
        required = null;
        filterProperties = null;

        super.release();
    }

}
