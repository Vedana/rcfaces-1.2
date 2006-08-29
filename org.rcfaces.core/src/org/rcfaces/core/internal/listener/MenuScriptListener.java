/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IMenuListener;
import org.rcfaces.core.event.MenuEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class MenuScriptListener extends AbstractScriptListener implements
        IMenuListener {
    private static final String REVISION = "$Revision$";

    public MenuScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public MenuScriptListener() {
    }

    public void menuShown(MenuEvent event) {
    }

}
