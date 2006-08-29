/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.core.event;

import javax.faces.FacesException;
import javax.faces.event.FacesEvent;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class UnsupportedEventTypeException extends FacesException {
	private static final String REVISION = "$Revision$";

	private final FacesEvent event;
	
	public UnsupportedEventTypeException(FacesEvent event) {
		super("Event '"+event+"' is not supported by listener !");
		
		this.event=event;
	}

	public final FacesEvent getEvent() {
		return event;
	}
	
}
