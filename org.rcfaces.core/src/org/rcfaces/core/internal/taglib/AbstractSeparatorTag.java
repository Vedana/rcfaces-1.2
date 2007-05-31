package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractSeparatorComponent;
import org.rcfaces.core.internal.component.Properties;

public abstract class AbstractSeparatorTag extends CameliaTag implements Tag {

    private static final Log LOG = LogFactory
            .getLog(AbstractSeparatorTag.class);

    private String visible;

    private String hiddenMode;

    public final String getVisible() {
        return visible;
    }

    public final void setVisible(String visible) {
        this.visible = visible;
    }

    public final String getHiddenMode() {
        return hiddenMode;
    }

    public final void setHiddenMode(String hiddenMode) {
        this.hiddenMode = hiddenMode;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("  visible='" + visible + "'");
            LOG.debug("  hiddenMode='" + hiddenMode + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof AbstractSeparatorComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'AbstractSeparatorComponent'.");
        }

        AbstractSeparatorComponent component = (AbstractSeparatorComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (visible != null) {
            if (isValueReference(visible)) {
                ValueBinding vb = application.createValueBinding(visible);
                component.setValueBinding(Properties.VISIBLE, vb);

            } else {
                component.setVisible(getBool(visible));
            }
        }

        if (hiddenMode != null) {
            if (isValueReference(hiddenMode)) {
                ValueBinding vb = application.createValueBinding(hiddenMode);
                component.setValueBinding(Properties.HIDDEN_MODE, vb);

            } else {
                component.setHiddenMode(hiddenMode);
            }
        }
    }

    public void release() {
        visible = null;
        hiddenMode = null;

        super.release();
    }

}
