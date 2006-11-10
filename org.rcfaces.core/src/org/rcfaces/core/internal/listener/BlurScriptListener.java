/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.BlurEvent;
import org.rcfaces.core.event.IBlurListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BlurScriptListener extends AbstractScriptListener implements
        IBlurListener {
    private static final String REVISION = "$Revision$";

    public BlurScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public BlurScriptListener() {
    }

    public void processBlur(BlurEvent event) {
    }
}
