/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.1  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IJavaScriptComponent {
    void initializeJavaScript(IJavaScriptWriter javaScriptWriter)
            throws WriterException;

    void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException;

    void initializeJavaScriptComponent(IJavaScriptWriter javaScriptWriter)
            throws WriterException;

    void releaseJavaScript(IJavaScriptWriter javaScriptWriter)
            throws WriterException;
}
