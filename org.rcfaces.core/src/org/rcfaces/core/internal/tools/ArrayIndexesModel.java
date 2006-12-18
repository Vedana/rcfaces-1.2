/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.model.AbstractIndexesModel;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ArrayIndexesModel extends AbstractIndexesModel implements
        Serializable {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7393822820762985697L;

    private static final Log LOG = LogFactory.getLog(ArrayIndexesModel.class);

    protected static final int[] EMPTY_SELECTION = new int[0];

    private static final boolean VERIFY_GARBAGE = true;

    private int selectionIndexes[] = EMPTY_SELECTION;

    private int lastPos = 0;

    private int count = 0;

    private boolean garbaged = true;

    public ArrayIndexesModel() {
    }

    public ArrayIndexesModel(int[] indexes) {
        setIndexes(indexes, false);
    }

    public int[] listSortedIndexes() {
        if (count == 0) {
            return EMPTY_SELECTION;
        }

        garbage();

        int a[] = new int[count];
        System.arraycopy(selectionIndexes, 0, a, 0, count);

        Arrays.sort(a);

        return a;
    }

    public int getFirstIndex() {
        if (count == 0) {
            return -1;
        }

        garbage();

        return selectionIndexes[0];
    }

    private void garbage() {
        if (garbaged) {
            return;
        }

        garbaged = true;
        if (count == lastPos) {
            return;
        }

        int lastEmpty = -1;
        int last = -1;
        for (int i = 0; i < lastPos; i++) {
            int n = selectionIndexes[i];

            if (n < 0) {
                if (lastEmpty < 0) {
                    lastEmpty = i;
                }

                continue;
            }

            if (lastEmpty >= 0) {
                selectionIndexes[lastEmpty] = n;
                last = lastEmpty;
                lastEmpty++;
                continue;
            }

            last = i;

            continue;
        }

        // Assure que count==last
        if (count != last + 1) {
            LOG.error("Y a un probleme ! (count!=last)");
        }

        if (VERIFY_GARBAGE) {
            Set set = new HashSet(count);
            for (int i = 0; i < count; i++) {
                if (set.add(new Integer(i)) == false) {
                    System.err.println("YA un GROS probleme !");
                }
            }
        }

        lastPos = count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.ISelectionModel#clearSelections()
     */
    public void clearIndexes() {
        count = 0;
        lastPos = 0;
        garbaged = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.ISelectionModel#isSelected(int)
     */
    public boolean containsIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Invalid index (" + index
                    + " < 0)");
        }

        if (count == 0) {
            return false;
        }

        garbage();

        for (int i = 0; i < count; i++) {
            int n = selectionIndexes[i];

            if (index == n) {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.ISelectionModel#addSelection(int)
     */
    public void addIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Invalid index (" + index
                    + " < 0)");
        }

        for (int i = 0; i < lastPos; i++) {
            int n = selectionIndexes[i];

            if (index == n) {
                return;
            }
        }

        if (lastPos < selectionIndexes.length) {
            selectionIndexes[lastPos++] = index;
            count++;
            garbaged = false;
            return;
        }

        garbage();

        if (lastPos < selectionIndexes.length) {
            selectionIndexes[lastPos++] = index;
            count++;
            garbaged = false;
            return;
        }

        // On agrandi
        int d = count / 2;
        if (d < 8) {
            d = 8;
        }
        int n[] = new int[count + d];
        if (count > 0) {
            System.arraycopy(selectionIndexes, 0, n, 0, count);
        }

        selectionIndexes = n;

        selectionIndexes[lastPos++] = index;
        count++;
        garbaged = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.ISelectionModel#removeSelection(int)
     */
    public void removeIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Invalid index (" + index
                    + " < 0)");
        }
        if (count == 0) {
            return;
        }

        for (int i = 0; i < lastPos; i++) {
            if (selectionIndexes[i] != index) {
                continue;
            }

            selectionIndexes[i] = -1;
            count--;
            garbaged = false;
            return;
        }
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
        setIndexes(indexes, true);
    }

    public void setIndexes(int[] indexes, boolean copy) {
        if (indexes == null || indexes.length < 1) {
            selectionIndexes = EMPTY_SELECTION;
            lastPos = 0;
            count = 0;
            return;
        }

        if (copy) {
            selectionIndexes = new int[indexes.length];
            System.arraycopy(indexes, 0, selectionIndexes, 0, indexes.length);

        } else {
            selectionIndexes = indexes;
        }

        garbaged = true;
        count = selectionIndexes.length;
        lastPos = count;

        for (int i = 0; i < selectionIndexes.length; i++) {
            if (selectionIndexes[i] >= 0) {
                continue;
            }

            garbaged = false;
            count--;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IIndexesModel#countIndexes()
     */
    public int countIndexes() {
        return count;
    }
}
