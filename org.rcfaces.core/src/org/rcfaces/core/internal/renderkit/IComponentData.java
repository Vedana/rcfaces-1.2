/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.9  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.8  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.7  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/31 08:29:47  oeuillot
 * *** empty log message ***
 *
 * Revision 1.5  2004/08/30 15:14:16  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2004/08/30 14:34:39  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/30 12:19:13  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/30 08:29:15  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.internal.renderkit;

import org.rcfaces.core.internal.IReleasable;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IComponentData extends IProperties, IReleasable, IEventData {

    String getComponentParameter();

    String[] getComponentParameters();

    String getParameter(String parameterName);

    String[] getParameters(String parameterName);

    boolean isEventComponent();
}