/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class JavascriptRepositoryLifecycle extends Lifecycle {

    public void addPhaseListener(PhaseListener listener) {
        // System.out.println("Add phase listener !");
    }

    public void execute(FacesContext context) throws FacesException {
        // System.out.println("EXECUTE !");
    }

    public PhaseListener[] getPhaseListeners() {
        return null;
    }

    public void removePhaseListener(PhaseListener listener) {
        // System.out.println("Remove phase listener !");
    }

    public void render(FacesContext context) throws FacesException {
        // System.out.println("Render !");
    }

}
