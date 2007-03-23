/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserEvent extends ActionEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 6509010645116536466L;

    private final String value;

    private final int detail;

    private final String item;

    public UserEvent(UIComponent component, String value, String item,
            int detail) {
        super(component);

        this.value = value;
        this.item = item;
        this.detail = detail;
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IUserEventListener);
    }

    public void processListener(FacesListener listener) {
        ((IUserEventListener) listener).processUserEvent(this);
    }

    public final String getValue() {
        return value;
    }

    public int getDetail() {
        return detail;
    }

    public final String getItem() {
        return item;
    }

}
