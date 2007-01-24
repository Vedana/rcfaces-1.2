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
public interface IFiltredCollection {

    int NO_MAXIMUM_RESULT_NUMBER = -1;

    Iterator iterator(IFilterProperties filterProperties,
            int maximumResultNumber);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IFiltredIterator extends Iterator {
        int getSize();

        void release();
    }
}
