/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:12  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.rcfaces.core.internal.manager.IServerDataManager;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ServerDataTag extends TagSupport {
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
        if ((component instanceof IServerDataManager) == false) {
            throw new JspException(
                    "Component does not implement IServerDataManager");

        }

        IServerDataManager serverDataCapability = (IServerDataManager) component;

        if (UIComponentTag.isValueReference(value)) {
            ValueBinding vb = application.createValueBinding(value);

            serverDataCapability.setServerData(name, vb);

        } else {
            serverDataCapability.setServerData(name, value);
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
