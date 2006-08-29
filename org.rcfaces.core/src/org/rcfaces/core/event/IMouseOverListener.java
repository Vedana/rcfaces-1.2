/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/16 13:30:00  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.event;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IMouseOverListener extends FacesListener {

	void processMouseOver(MouseOverEvent event) throws AbortProcessingException;
}
