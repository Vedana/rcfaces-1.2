/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 *
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICloseListener;


/**
 * 
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ICloseEventCapability {

	void addCloseListener(ICloseListener facesListener);

	void removeCloseListener(ICloseListener facesListener);

	FacesListener [] listCloseListeners();
}