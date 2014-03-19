/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Iterator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFiltredCollection<T> {

	int NO_MAXIMUM_RESULT_NUMBER = -1;

	Iterator<T> iterator(IFilterProperties filterProperties,
			int maximumResultNumber);

	/**
	 * 
	 * @author Olivier Oeuillot (latest modification by $Author$)
	 * @version $Revision$ $Date$
	 */
	public interface IFiltredIterator<T> extends Iterator<T> {
		int getSize();

		void release();
	}
}
