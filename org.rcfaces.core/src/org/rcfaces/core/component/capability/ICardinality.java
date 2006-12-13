/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A string indicating the cardinality (number of check allowed) for this componenent.
 * Authorized values are : 	?|+|*|1|one|zeroMany|oneMany|optional|default
 * default=zeroMany
 * 
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
