/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IUserEventListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IUserEventCapability {
    void addUserEventListener(IUserEventListener facesListener);

    void removeUserEventListener(IUserEventListener facesListener);

    FacesListener[] listUserEventListeners();

}
