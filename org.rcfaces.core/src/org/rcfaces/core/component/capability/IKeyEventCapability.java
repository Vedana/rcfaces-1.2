/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.2  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IKeyEventCapability extends IKeyPressEventCapability,
        IKeyUpEventCapability, IKeyDownEventCapability {

}
