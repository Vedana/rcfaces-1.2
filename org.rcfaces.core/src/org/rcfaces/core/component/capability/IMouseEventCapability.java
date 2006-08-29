/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IMouseOutListener;
import org.rcfaces.core.event.IMouseOverListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IMouseEventCapability {
	void addMouseOverListener(IMouseOverListener facesListener);

	void removeMouseOverListener(IMouseOverListener facesListener);

	FacesListener [] listMouseOverListeners();

	void addMouseOutListener(IMouseOutListener facesListener);

	void removeMouseOutListener(IMouseOutListener facesListener);

	FacesListener [] listMouseOutListeners();

}
