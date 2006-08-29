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
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IPropertyChangeListener;
import org.rcfaces.core.event.PropertyChangeEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class PropertyChangeScriptListener extends AbstractScriptListener
        implements IPropertyChangeListener {
    private static final String REVISION = "$Revision$";

    public PropertyChangeScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public PropertyChangeScriptListener() {
    }

    public void processPropertyChange(PropertyChangeEvent event) {
    }
}