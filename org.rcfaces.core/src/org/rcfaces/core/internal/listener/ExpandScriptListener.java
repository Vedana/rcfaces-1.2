package org.rcfaces.core.internal.listener;


import org.rcfaces.core.event.ExpandEvent;
import org.rcfaces.core.event.IExpandListener;


/**
 * @author meslin.jb@vedana.com
 */
public class ExpandScriptListener extends AbstractScriptListener implements
        IExpandListener {
    private static final String REVISION = "$Revision$";

    public ExpandScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ExpandScriptListener() {
    }


	public void processExpand(ExpandEvent event){
	
	}
}
