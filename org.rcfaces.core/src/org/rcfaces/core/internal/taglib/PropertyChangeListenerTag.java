/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.event.IPropertyChangeListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeListenerTag extends AbstractListenerTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 5117953058731866439L;

    private static final Log LOG = LogFactory
            .getLog(PropertyChangeListenerTag.class);

    protected void addListener(Object listener, UIComponent component) {
        addPropertyChangeListener(listener, component);
    }

    public static void addPropertyChangeListener(Object listener,
            UIComponent component) {

        if ((listener instanceof IPropertyChangeListener) == false) {
            throw new FacesException("Listener '" + listener
                    + "' must implement IPropertyChangeListener.");
        }

        IPropertyChangeListener propertyChangeListener = (IPropertyChangeListener) listener;

        if ((component instanceof IPropertyChangeEventCapability) == false) {
            LOG.error("Component '" + component.getId()
                    + "' does not implement IPropertyChangeEventCapability.");
            return;
        }

        ((IPropertyChangeEventCapability) component)
                .addPropertyChangeListener(propertyChangeListener);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add propertyChangeListener '" + propertyChangeListener
                    + "' to component '" + component.getId() + "'.");
        }
    }

    protected String getListenerName() {
        return "propertyChange";
    }

}
