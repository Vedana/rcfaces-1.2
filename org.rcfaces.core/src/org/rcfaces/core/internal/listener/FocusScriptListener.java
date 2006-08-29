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

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.FocusEvent;
import org.rcfaces.core.event.IFocusListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class FocusScriptListener extends AbstractScriptListener implements
        IFocusListener {
    private static final String REVISION = "$Revision$";

    public FocusScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public FocusScriptListener() {
    }

    public void processFocus(FocusEvent event) throws AbortProcessingException {
    }
}