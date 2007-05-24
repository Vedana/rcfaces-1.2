/*
 * $Id$
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
    int[] listSortedIndexes();

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

    Object[] listSelectedObjects(Object toArray[], Object value);

    Object getFirstSelectedObject(Object cachedValue);
}
