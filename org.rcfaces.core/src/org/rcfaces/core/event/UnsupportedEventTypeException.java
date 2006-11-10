/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.FacesException;
import javax.faces.event.FacesEvent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UnsupportedEventTypeException extends FacesException {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 267866609058774106L;

    private final FacesEvent event;

    public UnsupportedEventTypeException(FacesEvent event) {
        super("Event '" + event + "' is not supported by listener !");

        this.event = event;
    }

    public final FacesEvent getEvent() {
        return event;
    }

}
