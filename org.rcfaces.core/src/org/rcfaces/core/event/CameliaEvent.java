/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.listener.IScriptListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class CameliaEvent extends FacesEvent implements ITypedEvent {
    private static final String REVISION = "$Revision$";

    protected final int type;

    public CameliaEvent(UIComponent component) {
        this(component, 0);
    }

    public CameliaEvent(UIComponent component, int type) {
        super(component);

        this.type = type;
    }

    public final int getType() {
        return type;
    }

    public boolean isAppropriateListener(FacesListener listener) {
        if (listener instanceof IScriptListener) {
            return false;
        }

        return true;
    }

}
