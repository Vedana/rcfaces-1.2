/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IInitListener;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IInitEventCapability {

    void addInitListener(IInitListener facesListener);

    void removeInitListener(IInitListener facesListener);

    FacesListener[] listInitListeners();
}
