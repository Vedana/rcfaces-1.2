/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IErrorEventCapability;
import org.rcfaces.core.event.IErrorListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ErrorListenerTag extends AbstractListenerTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 5117953058731866439L;

    private static final Log LOG = LogFactory.getLog(ErrorListenerTag.class);

    protected void addListener(Object listener, UIComponent component) {
        addErrorListener(listener, component);
    }

    public static void addErrorListener(Object listener, UIComponent component) {

        if ((listener instanceof IErrorListener) == false) {
            throw new FacesException("Listener '" + listener
                    + "' must implement IErrorListener.");
        }

        IErrorListener errorListener = (IErrorListener) listener;

        if ((component instanceof IErrorEventCapability) == false) {
            LOG.error("Component '" + component.getId()
                    + "' does not implement IErrorEventCapability.");
            return;
        }

        ((IErrorEventCapability) component).addErrorListener(errorListener);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add an errorListener '" + errorListener
                    + "' to component '" + component.getId() + "'.");
        }
    }

    protected String getListenerName() {
        return "error";
    }

}
