/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.2  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.validator;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IDescriptor {
	String [] listRequiredClasses();
	
	IParameter [] listParameters();
}
