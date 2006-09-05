/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:14  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IRequestContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IHtmlRequestContext extends IRequestContext {
    IHtmlProcessContext getExternalContext();
}
