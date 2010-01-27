/*
 * $Id$
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientValueChangeEvent extends ValueChangeEvent {

    private static final long serialVersionUID = 3196578500483059239L;

    public ClientValueChangeEvent(UIComponent component, Object oldValue,
            Object newValue) {

        super(component, oldValue, newValue);
    }

}
