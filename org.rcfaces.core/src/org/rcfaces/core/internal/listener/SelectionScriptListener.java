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
 * Revision 1.4  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISelectionListener;
import org.rcfaces.core.event.SelectionEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class SelectionScriptListener extends AbstractScriptListener implements
        ISelectionListener {
    private static final String REVISION = "$Revision$";

    public SelectionScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SelectionScriptListener() {
    }

    public void componentSelected(SelectionEvent event) {
    }

}
