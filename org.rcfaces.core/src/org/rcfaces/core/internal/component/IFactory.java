/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/05 16:53:26  oeuillot
 * Integration de l'INPUT
 *
 */
package org.rcfaces.core.internal.component;

import java.util.List;
import java.util.Map;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IFactory {
    
    String getName();

    List createList(int size);

    Map createMap(int size);

    IComponentEngine createComponentEngine();

    IPropertiesManager createPropertiesManager(IComponentEngine engine);

    /*
    IPropertiesAccessor createPropertiesAccessor(IComponentEngine engine);

    
    IPropertiesAccessor restorePropertiesAccessor(FacesContext facesContext,
            IComponentEngine engine, Object state);
            */
}