/*
 * $Id$
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeEvent extends ValueChangeEvent {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -4109774105032276791L;

    private final String propertyName;

    public PropertyChangeEvent(UIComponent component, String propertyName,
            Object oldValue, Object newValue) {
        super(component, oldValue, newValue);

        this.propertyName = propertyName;
    }

    public final String getPropertyName() {
        return propertyName;
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IPropertyChangeListener);
    }

    public void processListener(FacesListener listener) {
        ((IPropertyChangeListener) listener).processPropertyChange(this);
    }

}