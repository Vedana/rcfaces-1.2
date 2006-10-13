/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ISelectionListener;

/**
 * L'élement qui implémente cette interface génère un événement dés que
 * l'utilisateur interagit avec lui.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectionEventCapability {

    void addSelectionListener(ISelectionListener facesListener);

    void removeSelectionListener(ISelectionListener facesListener);

    FacesListener[] listSelectionListeners();
}