/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 */
package org.rcfaces.core.model;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractIndexesModel implements IIndexesModel {
	private static final String REVISION = "$Revision$";

	public Object[] listSelectedObjects(Object toArray[], Object value) {
		return IndexesModels.listSelectedObject(toArray, value, this);
	}
	
	public Object getFirstSelectedObject(Object value) {
		return IndexesModels.getFirstSelectedObject(value, this);
	}
}
