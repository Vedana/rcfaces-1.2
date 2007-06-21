/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IValidationListener;
import org.rcfaces.core.event.ValidationEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidationScriptListener extends AbstractScriptListener implements
        IValidationListener {
    private static final String REVISION = "$Revision$";

    public ValidationScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ValidationScriptListener() {
    }

    public void processValidationEvent(ValidationEvent event) {
    }
}
