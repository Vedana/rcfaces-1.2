/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IPropertyChangeListener;
import org.rcfaces.core.event.PropertyChangeEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeScriptListener extends AbstractScriptListener
        implements IPropertyChangeListener {
    private static final String REVISION = "$Revision$";

    public PropertyChangeScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public PropertyChangeScriptListener() {
    }

    public void processPropertyChange(PropertyChangeEvent event) {
    }
}
