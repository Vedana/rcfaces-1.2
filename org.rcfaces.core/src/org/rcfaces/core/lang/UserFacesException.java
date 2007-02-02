/*
 * $Id$
 */
package org.rcfaces.core.lang;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserFacesException extends FacesException {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8523045593672133982L;

    private final int messageCode;

    private final String detail;

    private final String componentId;

    public UserFacesException(int messageCode, String message) {
        this((String) null, messageCode, message, null);
    }

    public UserFacesException(UIComponent component, int messageCode,
            String message, String detail) {
        this(component.getClientId(FacesContext.getCurrentInstance()),
                messageCode, message, detail);
    }

    public UserFacesException(String componentId, int messageCode) {
        this(componentId, messageCode, null, null);
    }

    public UserFacesException(String componentId, int messageCode,
            String message, String detail) {
        super(message);

        this.componentId = componentId;
        this.messageCode = messageCode;
        this.detail = detail;
    }

    public final String getComponentClientId() {
        return componentId;
    }

    public final String getDetail() {
        return detail;
    }

    public final int getMessageCode() {
        return messageCode;
    }

}
