/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ISortListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortEventCapability {

	String SORT_INTEGER = "integer";

	String SORT_NUMBER = "number";

	String SORT_ALPHA = "alpha";

	String SORT_ALPHA_IGNORE_CASE = "alphaIgnoreCase";

	String SORT_TIME = "time";

	String SORT_DATE = "date";

	String SORT_SERVER = "server";

	void addSortListener(ISortListener facesListener);

	void removeSortListener(ISortListener facesListener);

	FacesListener[] listSortListeners();
}
