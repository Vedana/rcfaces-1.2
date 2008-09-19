/*
 * $Id$
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.PhaseId;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ItemSelectionEvent extends SelectionEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 2043226704038860305L;

    private final boolean immediate;

    public ItemSelectionEvent(UIComponent component, String value, Object valueObject, Object item, int detail, boolean immediate) {
        super(component, value, valueObject, item, detail);

        this.immediate = immediate;
    }

    public void setPhaseId(PhaseId phaseId) {
        if (immediate) {
            phaseId = PhaseId.APPLY_REQUEST_VALUES;
            // On force APPLY_REQUEST_VALUE
        }
        super.setPhaseId(phaseId);
    }

}
