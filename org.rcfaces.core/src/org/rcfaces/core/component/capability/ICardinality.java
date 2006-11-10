/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICardinality {

	int OPTIONAL_CARDINALITY = 1;

	int ZEROMANY_CARDINALITY = 2;

	int ONE_CARDINALITY = 3;

    int ONEMANY_CARDINALITY = 4;

	int DEFAULT_CARDINALITY = ZEROMANY_CARDINALITY;
}
