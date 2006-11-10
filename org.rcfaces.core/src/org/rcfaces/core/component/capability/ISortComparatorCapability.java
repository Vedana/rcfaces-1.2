/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Comparator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortComparatorCapability {
	Comparator getSortComparator();
	
	void setSortComparator(Comparator sortComparator);
}
