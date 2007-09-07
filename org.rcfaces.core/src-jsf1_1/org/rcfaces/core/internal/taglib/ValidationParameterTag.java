/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.rcfaces.core.internal.manager.IValidationParameters;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidationParameterTag extends TagSupport implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -1595891873980056092L;

    private static final boolean CLIENT_SIDE_DEFAULT_VALUE = true;

    private String name;

    private String value;

    private boolean clientSide = CLIENT_SIDE_DEFAULT_VALUE;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getValue() {
        return value;
    }

    public final void setValue(String value) {
        this.value = value;
    }

    public boolean isClientSide() {
        return clientSide;
    }

    public void setClientSide(boolean clientSide) {
        this.clientSide = clientSide;
    }

    public int doStartTag() throws JspException {

        // Locate our parent UIComponentTag
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) { // PENDING - i18n
            throw new JspException("Not nested in a UIComponentTag");
        }

        // Nothing to do unless this tag created a component
        if (!tag.getCreated()) {
            return (SKIP_BODY);
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();

        UIComponent component = tag.getComponentInstance();
        if ((component instanceof IValidationParameters) == false) {
            throw new JspException(
                    "Component does not implement IValidationParameters");

        }

        IValidationParameters validatonParameterManager = (IValidationParameters) component;

        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = application.createValueBinding(value);

            validatonParameterManager.setValidationParameter(name, vb,
                    clientSide);

        } else {
            validatonParameterManager.setValidationParameter(name, value,
                    clientSide);
        }

        return (SKIP_BODY);
    }

    public void release() {
        name = null;
        value = null;
        clientSide = CLIENT_SIDE_DEFAULT_VALUE;

        super.release();
    }

}
