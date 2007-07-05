/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.AdditionalInformationEvent;
import org.rcfaces.core.event.IAdditionalInformationListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdditionalInformationScriptListener extends AbstractScriptListener
        implements IAdditionalInformationListener {
    private static final String REVISION = "$Revision$";

    public AdditionalInformationScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public AdditionalInformationScriptListener() {
    }

    public void processAdditionalInformation(AdditionalInformationEvent event)
            throws AbortProcessingException {
    }
}
