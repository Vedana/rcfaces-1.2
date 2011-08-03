/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.CriteriaEvent;
import org.rcfaces.core.event.ICriteriaListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CriteriaScriptListener extends AbstractScriptListener implements
		ICriteriaListener {

	public CriteriaScriptListener(String scriptType, String command) {
		super(scriptType, command);
	}

	public CriteriaScriptListener() {
	}

	public void processCriteriaChanged(CriteriaEvent event)
			throws AbortProcessingException {

	}
}
