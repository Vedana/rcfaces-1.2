/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ISuggestionListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISuggestionEventCapability {

	/**
	 * Adds a listener to the component for the suggestion event
	 * 
	 * @param suggestionListener the suggestion listener to add 
	 */
    void addSuggestionListener(ISuggestionListener suggestionListener);

	/**
	 * Removes a listener from the component for the suggestion event
	 * 
	 * @param suggestionListener the suggestion listener to remove
	 */
    void removeSuggestionListener(ISuggestionListener suggestionListener);

	/**
	 * Returns a list of suggestion listener for the component
	 * 
	 * @return suggestion listeners' list
	 */
    FacesListener[] listSuggestionListeners();
}
