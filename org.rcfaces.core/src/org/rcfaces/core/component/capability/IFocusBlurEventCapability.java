/*
 * $Id$
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFocusBlurEventCapability {

	void addFocusListener(IFocusListener facesListener);

	void removeFocusListener(IFocusListener facesListener);

	FacesListener [] listFocusListeners();

	void addBlurListener(IBlurListener facesListener);

	void removeBlurListener(IBlurListener facesListener);

	FacesListener [] listBlurListeners();
}
