/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ICheckCardinalityCapability extends ICardinality {
	
	int getCheckCardinality();
	
	void setCheckCardinality(int checkCardinality);
}
