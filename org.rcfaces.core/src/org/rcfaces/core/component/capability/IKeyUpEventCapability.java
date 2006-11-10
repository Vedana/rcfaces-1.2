/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyUpListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyUpEventCapability {

    void addKeyUpListener(IKeyUpListener facesListener);

    void removeKeyUpListener(IKeyUpListener facesListener);

    FacesListener[] listKeyUpListeners();
}
