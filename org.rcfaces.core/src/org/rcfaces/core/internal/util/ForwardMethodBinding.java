/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.util;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ForwardMethodBinding extends MethodBinding implements StateHolder {
	private static final String REVISION="$Revision$";
	
	private String forward = null;

	public ForwardMethodBinding() {
	}

	public ForwardMethodBinding(String forward) {
		this.forward = forward;
	}

	public Object invoke(FacesContext context, Object params[]) {
		return forward;
	}

	public Class getType(FacesContext context) {
		return String.class;
	}

	// ----------------------------------------------------- StateHolder Methods

	public Object saveState(FacesContext context) {
		return forward;
	}

	public void restoreState(FacesContext context, Object state) {
		forward = (String) state;
	}

	private boolean transientFlag = false;

	public boolean isTransient() {
		return (this.transientFlag);
	}

	public void setTransient(boolean transientFlag) {
		this.transientFlag = transientFlag;
	}
}
