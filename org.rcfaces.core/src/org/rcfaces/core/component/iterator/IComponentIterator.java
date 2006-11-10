/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import javax.faces.component.UIComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentIterator {

	/**
	 * Retourne le nombre de composants qu'il reste � it�rer !
	 */
	int count();

	boolean hasNext();

	UIComponent nextComponent();
    
    UIComponent [] toArray(UIComponent[] array);
}
