/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * An int value indicating the cardinality (number of check allowed) for this componenent.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckCardinalityCapability extends ICardinality {
	
	/**
	 * Returns an int value indicating the cardinality (number of check allowed) for this componenent.
	 * 
	 * @return 1:?,optional|2:*,zeroMany|3:1,one+|4:+,oneMany
	 */
	int getCheckCardinality();
	
	/**
	 * Sets an int value indicating the cardinality (number of check allowed) for this componenent.
	 * 
	 * @param checkCardinality 1:?,optional|2:*,zeroMany|3:1,one+|4:+,oneMany default=zeroMany
	 */
	void setCheckCardinality(int checkCardinality);
}
