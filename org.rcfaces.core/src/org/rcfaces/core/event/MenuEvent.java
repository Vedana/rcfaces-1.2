/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuEvent extends CameliaEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -5811470604287524737L;

    public MenuEvent(UIComponent component, int type) {
        super(component, type);
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IMenuListener);
    }

    public void processListener(FacesListener listener) {
        ((IMenuListener) listener).menuShown(this);
    }

}
