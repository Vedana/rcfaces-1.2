/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
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

import org.rcfaces.core.event.IMenuListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuEventCapability {
	void addMenuListener(IMenuListener facesListener);

	void removeMenuListener(IMenuListener facesListener);

	FacesListener [] listMenuListeners();
}
