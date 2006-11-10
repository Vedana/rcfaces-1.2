/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyDownListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyDownEventCapability {

    void addKeyDownListener(IKeyDownListener facesListener);

    void removeKeyDownListener(IKeyDownListener facesListener);

    FacesListener[] listKeyDownListeners();
}
