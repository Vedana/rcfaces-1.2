/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyPressListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyPressEventCapability {

    void addKeyPressListener(IKeyPressListener facesListener);

    void removeKeyPressListener(IKeyPressListener facesListener);

    FacesListener[] listKeyPressListeners();
}
