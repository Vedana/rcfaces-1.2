/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
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

/**
 * Enable/Disabled state.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IDisabledCapability {

	/**
	 * Returns <code>false</code> if the receiver is enabled and all ancestors up to and
	 * including the receiver's nearest ancestor shell are enabled.
	 * <br> 
	 * Otherwise, false is returned. A disabled control is typically not selectable from
	 * the user interface and draws with an inactive or "grayed" look.
	 * 
	 * @return the receiver's enabled state
	 */
	boolean isDisabled();

	/**
	 * Disabled the receiver if the argument is true, and enables it otherwise. A disabled control is typically not selectable from the user interface and draws with an inactive or "grayed" look.
	 * 
	 * @param disabled the new enabled state
	 */
	void setDisabled(boolean disabled);
}