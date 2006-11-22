/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValueChangeEventCapability {

    void addValueChangeListener(ValueChangeListener facesListener);

    void removeValueChangeListener(ValueChangeListener facesListener);

    FacesListener[] listValueChangeListeners();
}
