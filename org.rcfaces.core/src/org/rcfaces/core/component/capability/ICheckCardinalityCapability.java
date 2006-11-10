/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckCardinalityCapability extends ICardinality {
	
	int getCheckCardinality();
	
	void setCheckCardinality(int checkCardinality);
}
