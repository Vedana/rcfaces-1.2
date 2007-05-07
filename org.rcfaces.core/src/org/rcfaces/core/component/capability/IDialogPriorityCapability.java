/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;


/**
 * A int indicating the cardinality (number of check allowed) for this componenent.
 * Authorized values are : 	?|+|*|1|one|zeroMany|oneMany|optional|default
 * default=zeroMany
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDialogPriorityCapability {

	int MAX_PRIORITY = 1000;

	public int getPriority();
	
	public void setPriority(int priority);
	
}
