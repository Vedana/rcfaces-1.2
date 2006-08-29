/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.1  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/16 13:30:00  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.listener.IScriptListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
abstract class CameliaEvent extends FacesEvent implements ITypedEvent {
	private static final String REVISION = "$Revision$";

	protected final int type;

	public CameliaEvent(UIComponent component) {
		this(component, 0);
	}

	public CameliaEvent(UIComponent component, int type) {
		super(component);

		this.type = type;
	}

	public final int getType() {
		return type;
	}

	public boolean isAppropriateListener(FacesListener listener) {
		if (listener instanceof IScriptListener) {
			return false;
		}

		return true;
	}

}