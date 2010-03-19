/*
 * $Id$
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.PhaseId;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFacesEvent {
    UIComponent getComponent();

    PhaseId getPhaseId();

}
