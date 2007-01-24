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
public class ErrorEvent extends ActionEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -1530737844134123632L;

    public ErrorEvent(UIComponent component) {
        super(component);
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IErrorListener);
    }

    public void processListener(FacesListener listener) {
        ((IErrorListener) listener).processError(this);
    }

}
