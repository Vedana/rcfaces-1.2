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
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.4  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISortListener;
import org.rcfaces.core.event.SortEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class SortScriptListener extends AbstractScriptListener implements
        ISortListener {
    private static final String REVISION = "$Revision$";

    public SortScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SortScriptListener() {
    }

    public void processSort(SortEvent event) {
    }

}
