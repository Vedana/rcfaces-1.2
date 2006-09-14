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
 * Revision 1.5  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.4  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.3  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.model;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IIndexesModel {
	
	/**
	 * Number of selected items.
	 */
	int countIndexes();
	
	/**
	 * List all indexes sorted by ascendending.
	 */
	int [] listSortedIndexes(); 

	/**
	 * Returns the first index.
	 */
	int getFirstIndex();

	/**
	 * Clear all selection.
	 */
	void clearIndexes();

	/**
	 * Specify the indexes of selected items.
	 */
	void setIndexes(int indexes[]);
	
	/**
	 * Returns if the index of item is selected.
	 */
	boolean containsIndex(int index);

	void addIndex(int index);
	
	void removeIndex(int index);
	
	void commitChanges();
	
	Object [] listSelectedObjects(Object toArray[], Object value);

	Object getFirstSelectedObject(Object cachedValue);
}
