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

import org.rcfaces.core.internal.manager.IClientDataManager;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientDataTag extends TagSupport implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -7633749361412060360L;

    private String name;

    private String value;

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
        if ((component instanceof IClientDataManager) == false) {
            throw new JspException(
                    "Component does not implement IClientDataManager");

        }

        IClientDataManager clientDataCapability = (IClientDataManager) component;

        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = application.createValueBinding(value);

            clientDataCapability.setClientData(name, vb);

        } else {
            clientDataCapability.setClientData(name, value);
        }

        return (SKIP_BODY);

    }

    /**
     * <p>
     * Release references to any acquired resources.
     */
    public void release() {
        name = null;
        value = null;

        super.release();
    }

}
