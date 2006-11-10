/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.ISortListener;
import org.rcfaces.core.event.SortEvent;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SortActionListener extends AbstractActionListener implements
        ISortListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { SortEvent.class };

    public SortActionListener(String expression) {
        super(expression);
    }

    public SortActionListener() {
    }

    public void processSort(SortEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }

}
