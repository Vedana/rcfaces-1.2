/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
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
 * Revision 1.4  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ISuggestionListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISuggestionEventCapability {

    void addSuggestionListener(ISuggestionListener siggestionListener);

    void removeSuggestionListener(ISuggestionListener siggestionListener);

    FacesListener[] listSuggestionListeners();
}