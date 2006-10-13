/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IServiceEventListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServiceEventCapability {
    void addServiceEventListener(IServiceEventListener facesListener);

    void removeServiceEventListener(IServiceEventListener facesListener);

    FacesListener[] listServiceEventListeners();

}
