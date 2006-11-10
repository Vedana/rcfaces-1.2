/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISuggestionListener;
import org.rcfaces.core.event.SuggestionEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SuggestionScriptListener extends AbstractScriptListener implements
        ISuggestionListener {
    private static final String REVISION = "$Revision$";

    public SuggestionScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SuggestionScriptListener() {
    }

    public void processSuggestion(SuggestionEvent event) {
    }
}
