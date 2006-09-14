/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:50  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:12  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/04/27 13:49:46  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.2  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/22 11:48:06  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
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
