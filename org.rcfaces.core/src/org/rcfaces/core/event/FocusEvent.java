/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/16 13:30:00  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/16 08:00:08  oeuillot
 * Gestion des listeners
 *
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class FocusEvent extends CameliaEvent {
	private static final String REVISION = "$Revision$";

	public FocusEvent(UIComponent component) {
		super(component);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof IFocusListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public void processListener(FacesListener listener) {
		((IFocusListener) listener).processFocus(this);
	}

}