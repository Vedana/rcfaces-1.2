/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.event.IDoubleClickListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleClickListenerTag extends AbstractListenerTag {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -7064892566257135920L;

    private static final Log LOG = LogFactory
            .getLog(DoubleClickListenerTag.class);

    protected void addListener(Object listener, UIComponent component) {
        addDoubleClickListener(listener, component);
    }

    public static void addDoubleClickListener(Object listener,
            UIComponent component) {
        if ((listener instanceof IDoubleClickListener) == false) {
            throw new FacesException("Listener '" + listener
                    + "' must implement IDoubleClickListener.");
        }

        IDoubleClickListener doubleClickListener = (IDoubleClickListener) listener;

        if ((component instanceof IDoubleClickEventCapability) == false) {
            LOG.error("Component '" + component.getId()
                    + "' does not implement IDoubleClickEventCapability.");
            return;
        }

        ((IDoubleClickEventCapability) component)
                .addDoubleClickListener(doubleClickListener);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add doubleClickListener '" + doubleClickListener
                    + "' to component '" + component.getId() + "'.");
        }
    }

    protected String getListenerName() {
        return "doubleClick";
    }

}
