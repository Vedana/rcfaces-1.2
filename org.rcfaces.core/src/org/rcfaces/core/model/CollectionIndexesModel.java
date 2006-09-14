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
 * Revision 1.2  2004/09/13 08:34:27  oeuillot
 */
package org.rcfaces.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CollectionIndexesModel extends AbstractIndexesModel implements
        Serializable {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -3821092264981658279L;

    protected static final int[] EMPTY_SELECTION = new int[0];

    protected static final int UNKNOWN_INDEX = -1;

    protected final Collection collection;

    public CollectionIndexesModel(Collection collection) {
        this.collection = collection;
    }

    public int getFirstIndex() {
        if (collection.isEmpty()) {
            return -1;
        }

        if (collection instanceof List) {
            return getIndex(((List) collection).get(0));
        }

        return getIndex(collection.iterator().next());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#listIndexes()
     */
    public final int[] listSortedIndexes() {
        if (collection.isEmpty()) {
            return EMPTY_SELECTION;
        }

        int n[] = new int[collection.size()];
        int pos = 0;
        int unknownIndex = getUnknownIndex();
        for (Iterator it = collection.iterator(); it.hasNext();) {
            int idx = getIndex(it.next());

            if (idx == unknownIndex) {
                continue;
            }

            n[pos++] = idx;
        }

        if (pos == n.length) {
            if (n.length > 1) {
                Arrays.sort(n);
            }
            return n;
        }

        int n2[] = new int[pos];

        System.arraycopy(n, 0, n2, 0, pos);

        if (n2.length > 1) {
            Arrays.sort(n2);
        }

        return n2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#clearIndexes()
     */
    public final void clearIndexes() {
        collection.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#containsIndex(int)
     */
    public final boolean containsIndex(int index) {
        return collection.contains(getKey(index));
    }

    protected int getIndex(Object object) {
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }

        return getUnknownIndex();
    }

    protected Object getKey(int index) {
        return new Integer(index);
    }

    protected int getUnknownIndex() {
        return UNKNOWN_INDEX;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#addIndex(int)
     */
    public void addIndex(int index) {
        collection.add(getKey(index));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#removeIndex(int)
     */
    public final void removeIndex(int index) {
        collection.remove(getKey(index));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#commitChanges()
     */
    public void commitChanges() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#setIndexes(int[])
     */
    public void setIndexes(int[] indexes) {
        clearIndexes();

        if (indexes == null || indexes.length < 1) {
            return;
        }

        for (int i = 0; i < indexes.length; i++) {
            int val = indexes[i];
            if (val < 0) {
                continue;
            }

            addIndex(val);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#countIndexes()
     */
    public int countIndexes() {
        if (collection.isEmpty()) {
            return 0;
        }

        int count = 0;
        int unknownIndex = getUnknownIndex();
        for (Iterator it = collection.iterator(); it.hasNext();) {
            int idx = getIndex(it.next());

            if (idx == unknownIndex) {
                continue;
            }

            count++;
        }

        return count;
    }
}
