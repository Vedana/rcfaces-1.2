/*
 * $Id$
 *
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/04/27 13:49:45  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.3  2004/08/06 12:03:49  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 16:53:26  oeuillot
 * Integration de l'INPUT
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.internal.component;

import java.util.Set;

import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertiesAccessor {

    boolean isPropertySetted(String propertyName);

    Object getProperty(String propertyName);

    Object setProperty(FacesContext context, String propertyName, Object value);

    Object removeProperty(FacesContext context, String name);

    Object saveState(FacesContext context);

    IDeltaPropertiesAccessor restoreState(FacesContext context, Object state);

    void release();

    IDeltaPropertiesAccessor createDeltaPropertiesAccessor();

    Set keySet();

    int size();

}