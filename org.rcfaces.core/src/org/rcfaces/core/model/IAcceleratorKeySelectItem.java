/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.model;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IAcceleratorKeySelectItem {
    String getAcceleratorKey();
}
