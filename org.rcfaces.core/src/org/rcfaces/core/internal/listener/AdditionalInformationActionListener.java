/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.AdditionalInformationEvent;
import org.rcfaces.core.event.IAdditionalInformationListener;
import org.rcfaces.core.event.SelectionEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdditionalInformationActionListener extends AbstractActionListener
        implements IAdditionalInformationListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { SelectionEvent.class };

    public AdditionalInformationActionListener() {
    }

    public AdditionalInformationActionListener(String expression) {
        super(expression);
    }

    public AdditionalInformationActionListener(String expression,
            boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processAdditionalInformation(AdditionalInformationEvent event)
            throws AbortProcessingException {
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }

}
