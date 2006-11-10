/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ILoadListener;


/**
 * L'élement qui implémente cette interface génére un événement dés que
 * l'utilisateur interagit avec lui.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILoadEventCapability {

    void addLoadListener(ILoadListener loadListener);

    void removeLoadListener(ILoadListener loadListener);

    FacesListener[] listLoadListeners();
}
