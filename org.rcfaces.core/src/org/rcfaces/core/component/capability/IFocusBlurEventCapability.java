/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IBlurListener;
import org.rcfaces.core.event.IFocusListener;


/**
 * Un �lement heritant de cette interface poss�de la possibilit� d'accepter le
 * focus de l'utilisateur.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IFocusBlurEventCapability {

	void addFocusListener(IFocusListener facesListener);

	void removeFocusListener(IFocusListener facesListener);

	FacesListener [] listFocusListeners();

	void addBlurListener(IBlurListener facesListener);

	void removeBlurListener(IBlurListener facesListener);

	FacesListener [] listBlurListeners();
}