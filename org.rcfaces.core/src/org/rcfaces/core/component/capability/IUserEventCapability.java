/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IUserEventListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUserEventCapability {
    void addUserEventListener(IUserEventListener facesListener);

    void removeUserEventListener(IUserEventListener facesListener);

    FacesListener[] listUserEventListeners();

}
