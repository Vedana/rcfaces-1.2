/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISortListener;
import org.rcfaces.core.event.SortEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SortScriptListener extends AbstractScriptListener implements
        ISortListener {
    private static final String REVISION = "$Revision$";

    public SortScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SortScriptListener() {
    }

    public void processSort(SortEvent event) {
    }

}
