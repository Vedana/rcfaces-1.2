/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IScriptListener extends FacesListener {
	String getScriptType();
	
	String getCommand();
}
