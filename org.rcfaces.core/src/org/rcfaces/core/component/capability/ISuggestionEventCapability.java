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

    void addSuggestionListener(ISuggestionListener siggestionListener);

    void removeSuggestionListener(ISuggestionListener siggestionListener);

    FacesListener[] listSuggestionListeners();
}
