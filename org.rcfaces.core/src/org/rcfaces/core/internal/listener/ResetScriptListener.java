/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IResetListener;
import org.rcfaces.core.event.ResetEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResetScriptListener extends AbstractScriptListener implements
        IResetListener {
    private static final String REVISION = "$Revision$";

    public ResetScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ResetScriptListener() {
    }

    public void processReset(ResetEvent event) {
    }

}
