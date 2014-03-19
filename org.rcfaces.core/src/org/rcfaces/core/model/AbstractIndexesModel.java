/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractIndexesModel implements IIndexesModel {

	public Object[] listSelectedObjects(Object toArray[], Object value) {
		return IndexesModels.listSelectedObject(toArray, value, this);
	}

	public Object getFirstSelectedObject(Object value) {
		return IndexesModels.getFirstSelectedObject(value, this);
	}
}
