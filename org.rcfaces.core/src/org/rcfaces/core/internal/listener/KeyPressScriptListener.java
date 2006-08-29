/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.4  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IKeyPressListener;
import org.rcfaces.core.event.KeyPressEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class KeyPressScriptListener extends AbstractScriptListener implements
        IKeyPressListener {
    private static final String REVISION = "$Revision$";

    public KeyPressScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public KeyPressScriptListener() {
    }

    public void processKeyPress(KeyPressEvent event) {
    }
}