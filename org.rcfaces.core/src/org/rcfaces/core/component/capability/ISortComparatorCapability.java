/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Comparator;

/**
 * A string value specifying the "compare" function to use. it can reference a
 * javascript function or a keyword :
 * <ul>
 * <li> integer </li>
 * <li> number </li>
 * <li> alpha </li>
 * <li> alphaIgnoreCase </li>
 * <li> time </li>
 * <li> date </li>
 * <li> &lt;javascript function&gt; </li>
 * </ul>
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortComparatorCapability {

    /**
     * Returns the sort Comparator object associated with the component
     * 
     * @return the sort Comparator object
     */
    Comparator getSortComparator();

    /**
     * Associates a sort Comparator object to the component.
     * 
     * @param sortComparator
     *            the sort Comparator object
     */
    void setSortComparator(Comparator sortComparator);
}
