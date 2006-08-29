/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyUpListener;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IKeyUpEventCapability {

    void addKeyUpListener(IKeyUpListener facesListener);

    void removeKeyUpListener(IKeyUpListener facesListener);

    FacesListener[] listKeyUpListeners();
}
