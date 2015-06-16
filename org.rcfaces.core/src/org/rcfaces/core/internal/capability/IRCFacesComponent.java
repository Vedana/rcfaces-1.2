/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRCFacesComponent {
    // boolean isClientRendered();

  
    void clearListeners();

    void resetStates();
}
