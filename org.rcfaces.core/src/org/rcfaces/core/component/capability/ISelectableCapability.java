/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/08/24 15:39:33  oeuillot
 * Colonne retaillable + debug de composants
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
 * Revision 1.2  2003/11/12 15:15:02  oeuillot
 * Retire les methodes "public"
 *
 * Revision 1.1  2003/05/01 16:53:15  oeuillot
 * Deplacement des capabilities dans le package com.vedana.adonis.elements.capabilities
 * Deplacement des layouts dans le package com.vedana.adonis.elements.layout
 *
 * Revision 1.2  2003/01/21 18:12:19  oeuillot
 * Ajout des commentaires javadoc.
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISelectableCapability {

	/**
	 * 
	 */
	boolean isSelectable();

	/**
	 *
	 */
	void setSelectable(boolean selectable);
}