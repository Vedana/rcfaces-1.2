/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Map;

/**
 * A tag used to associate data to a view.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDataCapability {
    
	/**
	 * Associates data to variable name.
	 * 
	 * @param name the variable to associate data to
	 * @param data the data to associate to the variable
	 * @return the data provided
	 */
	String setClientData(String name, String data);

    /**
     * Removes the variable name and the data associated to it
     * 
     * @param name the variable to associate data to
     * @return the data that was associated to the variable
     */
	String removeClientData(String name);

	/**
	 * Retrieves the data associated to a variable
	 * 
	 * @param name the variable to associate data to
	 * @return the data associated to the variable
	 */
    String getClientData(String name);

    /**
     * Returns the list of variable associated to the component.
     * 
     * @return a list of variables
     */
    String[] listClientDataKeys();

    /**
     * Returns the number of variable associated to the component.
     * 
     * @return number of variable
     */
    int getClientDataCount();

    /**
     * Returns a map containing the couples vraiable-data
     * 
     * @return a map
     */
    Map getClientDataMap();
}
