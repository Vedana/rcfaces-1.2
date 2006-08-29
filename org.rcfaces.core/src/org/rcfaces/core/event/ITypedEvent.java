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

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ITypedEvent {

	UIComponent getComponent();

	int getType();
}
