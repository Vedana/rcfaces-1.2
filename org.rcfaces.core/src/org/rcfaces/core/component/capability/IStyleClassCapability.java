/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
 *
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IStyleClassCapability {

	/**
	 * Specify the style class of the component.
	 */
	void setStyleClass(String cssClass);

	/**
	 * Returns the style class of the component.
	 */
	String getStyleClass();

}