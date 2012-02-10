/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.ClickEvent;
import org.rcfaces.core.event.IClickListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClickActionListener extends AbstractActionListener implements
        IClickListener {

    private static final Class actionParameters[] = { ClickEvent.class };

    public ClickActionListener() {
    }

    public ClickActionListener(String expression) {
        super(expression);
    }

    public ClickActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processClick(ClickEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
