/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.core.internal.component;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractComponentEngine implements IComponentEngine {
    private static final String REVISION = "$Revision$";

    static final Iterator EMPTY_ITERATOR = Collections.EMPTY_SET.iterator();

    protected final IFactory factory;

    AbstractComponentEngine(IFactory factory) {
        if (factory == null) {
            throw new NullPointerException("Factory is NULL !");
        }
        this.factory = factory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.UIComponent#getFamily()
     */

    protected static final boolean isListEmpty(Collection list) {
        return (list == null) || (list.size() < 1);
    }

    protected static final boolean isMapEmpty(Map map) {
        return (map == null) || (map.size() < 1);
    }

    protected static final Iterator iteratorList(Collection list) {
        if (isListEmpty(list)) {
            return EMPTY_ITERATOR;
        }

        return list.iterator();
    }
}
