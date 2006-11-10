/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServiceEventListener extends FacesListener {

    void processServiceEvent(ServiceEvent event)
            throws AbortProcessingException;

}
