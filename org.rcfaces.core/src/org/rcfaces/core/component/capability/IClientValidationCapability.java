/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;


/**
 * A key identifying a validation process to apply to the component. this validation process can handle parameters. cf. the clientValidator doc.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidationCapability {
    
	/**
	 * Returns a key identifying a validation process to apply to the component. this validation process can handle parameters. cf. the clientValidator doc.
	 * @return client validator key
	 */
	String getClientValidator();

	/**
	 * Sets a key identifying a validation process to apply to the component. this validation process can handle parameters. cf. the clientValidator doc.
	 * 
	 * @param validator client validator key
	 */
    void setClientValidator(String validator);
}
