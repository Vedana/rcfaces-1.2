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
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISuggestionListener;
import org.rcfaces.core.event.SuggestionEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class SuggestionScriptListener extends AbstractScriptListener implements
        ISuggestionListener {
    private static final String REVISION = "$Revision$";

    public SuggestionScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SuggestionScriptListener() {
    }

    public void processSuggestion(SuggestionEvent event) {
    }
}