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
 * Revision 1.2  2004/08/16 08:00:08  oeuillot
 * Gestion des listeners
 *
 * Revision 1.1  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 */
package org.rcfaces.core.event;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IBlurListener extends FacesListener {

	void processBlur(BlurEvent event) throws AbortProcessingException;
	
}
