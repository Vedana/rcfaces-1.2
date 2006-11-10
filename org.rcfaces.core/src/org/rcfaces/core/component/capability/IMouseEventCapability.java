/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IMouseOutListener;
import org.rcfaces.core.event.IMouseOverListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMouseEventCapability {
	void addMouseOverListener(IMouseOverListener facesListener);

	void removeMouseOverListener(IMouseOverListener facesListener);

	FacesListener [] listMouseOverListeners();

	void addMouseOutListener(IMouseOutListener facesListener);

	void removeMouseOutListener(IMouseOutListener facesListener);

	FacesListener [] listMouseOutListeners();

}
