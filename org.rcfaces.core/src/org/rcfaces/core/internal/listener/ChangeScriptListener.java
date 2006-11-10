/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ChangeScriptListener extends AbstractScriptListener implements
        ValueChangeListener {
    private static final String REVISION = "$Revision$";

    public ChangeScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ChangeScriptListener() {
    }

    public void processValueChange(ValueChangeEvent event) {
    }
}
