/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
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
 * L'element peut etre activ� par un raccourci clavier.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IAccessKeyCapability {
	/**
	 * Retourne la combinaison de touches pour activer cet element.
	 */
	String getAccessKey();

	/**
	 * Specifie la combinaison de touches pour activer cet element.
	 */
	void setAccessKey(String key);
}