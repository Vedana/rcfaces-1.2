/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IInitListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IInitEventCapability {

    void addInitListener(IInitListener facesListener);

    void removeInitListener(IInitListener facesListener);

    FacesListener[] listInitListeners();
}
